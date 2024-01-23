plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    signingConfigs {
        create("release") {
            storePassword = "AndroidDev11"
            keyPassword = "AndroidDev11"
            storeFile = file("D:\\keystore\\PasswordStoreKey.jks")
            keyAlias = "key0"
        }
    }
    namespace = "com.onedive.passwordstore"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.onedive.passwordstore"
        minSdk = 22
        targetSdk = 34
        versionCode = 1
        versionName = "0.2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    externalNativeBuild {
        ndkVersion = "26.1.10909125"
        cmake {
            path("CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {

    //material3,colorPicker
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.github.skydoves:colorpickerview:2.3.0")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //AndroidX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("androidx.security:security-crypto:1.0.0")
    implementation("androidx.biometric:biometric:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //MVVM
    val viewModelLiveDataVersion = "2.7.0"
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$viewModelLiveDataVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelLiveDataVersion")

    //coroutine
    val coroutinesVersion = "1.7.3"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    //Room db
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    //Utils
    implementation ("de.raphaelebner:roomdatabasebackup:1.0.0-beta13")
    implementation("cat.ereza:customactivityoncrash:2.4.0")
    implementation ("com.airbnb.android:lottie:6.1.0")
    implementation ("net.zetetic:android-database-sqlcipher:4.5.4")

    //Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    debugImplementation ("com.squareup.leakcanary:leakcanary-android:2.12")

}