plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.clovercardpoc"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.clovercardpoc"
        minSdk = 24
        // noinspection ExpiredTargetSdkVersion
        targetSdk = 29
        versionCode = 3
        versionName = "3.0"
    }

    signingConfigs {
        create("release") {
            storeFile = file("C:/Users/berci/keystores/clovercardpoc.jks")
            storePassword = "Clover"
            keyAlias = "clovercardpoc"
            keyPassword = "Clover"

            enableV1Signing = true
            enableV2Signing = false
            enableV3Signing = false
            enableV4Signing = false
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.clover.sdk:clover-android-sdk:334")
}