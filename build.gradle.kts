buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.create("clean", type = Delete::class) {
    delete(rootProject.buildDir)
}
