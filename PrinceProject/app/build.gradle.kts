plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.princeproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.princeproject"
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
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.5.0"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.zxing:core:3.4.1")
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")

    implementation ("com.google.protobuf:protobuf-javalite:3.21.12")
    implementation(libs.play.services.location)

    testImplementation("junit:junit:4.13.2")
    testImplementation(libs.ext.junit)
    implementation("com.google.android.material:material:1.10.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.4.0")
    //androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.4.0")

    val fragment_version = "1.8.5"

    debugImplementation("androidx.fragment:fragment-testing-manifest:$fragment_version")

    androidTestImplementation("androidx.fragment:fragment-testing:$fragment_version")

    //debugImplementation ("androidx.fragment:fragment-testing:1.3.6")

    testImplementation ("org.mockito:mockito-core:4.5.1")
    testImplementation ("org.mockito:mockito-android:5.6.0")

    // Mockito for Android (Android Instrumentation tests)
    androidTestImplementation ("org.mockito:mockito-android:4.5.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //now
    testImplementation ("androidx.test.ext:junit:1.1.5")
    testImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation ("org.mockito:mockito-core:4.5.1")
    testImplementation ("com.google.firebase:firebase-messaging:23.1.0") // Only if you are using Firebase
    //
}
