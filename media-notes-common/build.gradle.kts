plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.kotlin)
}

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.ui)
                api(compose.material3)
                api(compose.materialIconsExtended)
                api(libs.kotlinx.collections.immutable)
                implementation(libs.mpp.windowSizeClass)
                implementation(compose.components.resources)
                implementation(libs.androidX.lifecycle.viewmodel)
                implementation(libs.androidX.compose.navigation)
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
        }
    }
}

android {
    namespace = "com.shashluchok.medianotes.common"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
