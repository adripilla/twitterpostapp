plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.twitterpostapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.twitterpostapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/DEPENDENCIES"
            excludes += "/META-INF/LICENSE"
            excludes += "/META-INF/LICENSE.txt"
            excludes += "/META-INF/NOTICE"
            excludes += "/META-INF/NOTICE.txt"
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // OkHttp para solicitudes HTTP
    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    // ScribeJava para autenticación OAuth
    implementation("com.github.scribejava:scribejava-apis:8.3.1")
    implementation("com.github.scribejava:scribejava-core:8.3.1") // core es necesario para el manejo OAuth
}
