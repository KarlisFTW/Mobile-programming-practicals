plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.mobileprogrammingpracticals"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mobileprogrammingpracticals"
        minSdk = 26
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.exp4j)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //newly added

    //implementation ("androidx.constraintlayout:constraintlayout:2.1.4")  // or the latest version
    implementation (libs.firebase.analytics.ktx)
    implementation (libs.androidx.viewpager2)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    //Practical 2 added
    implementation (libs.play.services.maps)
    implementation (libs.places)
    implementation (libs.android.volley)


    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.preferences.core)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.tools.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}