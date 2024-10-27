plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.kotlin)
}

android {
    namespace = "com.shashluchok.medianotes.sample"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.shashluchok.medianotes.sample"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    sourceSets {
        val kotlinAdditionalSourceSets = project.file("src/main/kotlin")
        findByName("main")?.java?.srcDirs(kotlinAdditionalSourceSets)
    }
}

dependencies {
    implementation(projects.mediaNotesCommon)
    implementation(libs.androidX.activity.compose)
    implementation(libs.androidX.appCompat)
}
