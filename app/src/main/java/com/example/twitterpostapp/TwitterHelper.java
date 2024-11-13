package com.example.twitterpostapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TwitterHelper {

    private static final String TAG = "TwitterHelper";

    // Nuevas claves de autenticación
    private static final String CONSUMER_KEY = "TeSSvbcVOWldSR5SvBtMocfAP";
    private static final String CONSUMER_SECRET = "gX07GPfNGNAqCOokENAezlkRWfZWn3tPjAXBCaay2SXgcfxVGE";
    private static final String ACCESS_TOKEN = "1850723153460314112-Zy8zyIY8cKQ0YqM1EgAzMLfO7qIOgI";
    private static final String ACCESS_TOKEN_SECRET = "5GuWWU5C5iAR5iOLdgQqFnot2uWHkhepvtr9Rx3Lzbpuy";

    private final OAuth10aService service;
    private final OAuth1AccessToken accessToken;
    private final ExecutorService executorService;

    public TwitterHelper() {
        // Configuración del servicio OAuth con scribe-java
        service = new ServiceBuilder(CONSUMER_KEY)
                .apiSecret(CONSUMER_SECRET)
                .build(TwitterApi.instance());
        accessToken = new OAuth1AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
        executorService = Executors.newSingleThreadExecutor(); // Ejecuta las solicitudes en un solo hilo de fondo
    }

    public void postTweetAsync(final String message, final Context context) {
        executorService.execute(() -> {
            String url = "https://api.twitter.com/2/tweets";
            Log.d(TAG, "Iniciando postTweet con mensaje: " + message);

            // Crear la solicitud OAuth
            OAuthRequest request = new OAuthRequest(Verb.POST, url);
            request.addHeader("Content-Type", "application/json");
            request.setPayload("{\"text\":\"" + message + "\"}");
            Log.d(TAG, "Payload de la solicitud: " + "{\"text\":\"" + message + "\"}");

            // Firmar la solicitud
            service.signRequest(accessToken, request);
            Log.d(TAG, "Solicitud firmada correctamente");

            try {
                // Ejecutar la solicitud
                Response response = service.execute(request);
                if (response.isSuccessful()) {
                    Log.d(TAG, "Tweet publicado correctamente.");
                    showToastOnMainThread(context, "Tweet publicado correctamente.");
                } else {
                    Log.e(TAG, "Error publicando el tweet: Código de error " + response.getCode());
                    showToastOnMainThread(context, "Error publicando el tweet: " + response.getCode());
                }
            } catch (IOException | InterruptedException | ExecutionException e) {
                Log.e(TAG, "Excepción al intentar enviar el tweet: " + e.getMessage(), e);
                showToastOnMainThread(context, "Error al enviar el tweet: " + e.getMessage());
            }
        });
    }

    private void showToastOnMainThread(Context context, String message) {
        // Ejecuta el Toast en el hilo principal de la UI
        if (context instanceof android.app.Activity) {
            ((android.app.Activity) context).runOnUiThread(() ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            );
        } else {
            Log.e(TAG, "Contexto no es una instancia de Activity, no se puede mostrar Toast.");
        }
    }
}
