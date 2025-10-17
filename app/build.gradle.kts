import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

// Load API key from environment variable or fallback to local file
val zuploApiKey: String = System.getenv("ZU_PLO_API_KEY") ?: run {
    val props = Properties()
    val file = rootProject.file("apikey.properties")
    if (file.exists()) {
        file.inputStream().use { props.load(it) }
        props["ACCUWEATHER_API_KEY"] as? String ?: ""
    } else {
        ""
    }
}

android {
    namespace = "com.example.basicweather"
    compileSdk = 36

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.basicweather"
        minSdk = 35
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "ACCUWEATHER_API_KEY", "\"$zuploApiKey\"")

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.google.code.gson:gson:2.8.6")
}
