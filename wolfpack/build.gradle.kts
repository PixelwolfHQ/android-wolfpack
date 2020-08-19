plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    setCompileSdkVersion(30)
    buildToolsVersion = "29.0.3"

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 100
        versionName = "0.1.0"

        vectorDrawables.useSupportLibrary = true
        consumerProguardFiles("consumer-rules.pro")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    lintOptions {
        disable("ObsoleteLintCustomCheck", "UnusedResources")
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation(embeddedKotlin("stdlib-jdk8"))

    // Android Core
    implementation("androidx.collection:collection-ktx:1.1.0")
    implementation("com.google.android.material:material:1.2.0")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-common-java8:2.2.0")

    // Testing
    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
