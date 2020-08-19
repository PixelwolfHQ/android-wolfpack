plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    setCompileSdkVersion(30)
    buildToolsVersion = "29.0.3"

    defaultConfig {
        applicationId = "br.com.pixelwolf.example"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

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

    api(project(":wolfpack"))

    // Kotlin
    implementation(embeddedKotlin("stdlib-jdk8"))

    // Android Core
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.collection:collection-ktx:1.1.0")
    implementation("com.google.android.material:material:1.2.0")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-common-java8:2.2.0")

    // Testing
    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
