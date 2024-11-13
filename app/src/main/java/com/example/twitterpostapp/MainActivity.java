package com.example.twitterpostapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TwitterHelper twitterHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twitterHelper = new TwitterHelper();

        EditText tweetMessage = findViewById(R.id.tweetMessage);
        Button postTweetButton = findViewById(R.id.postTweetButton);

        postTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = tweetMessage.getText().toString();

                // Llama a postTweetAsync para enviar el tweet en segundo plano
                twitterHelper.postTweetAsync(message, MainActivity.this);
            }
        });
    }
}
