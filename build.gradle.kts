import com.appmattus.markdown.rules.LineLengthRule

buildscript {
    dependencies {
        classpath(libs.plugin.kotlin)
        classpath(libs.ktlint.composeRules)
    }
}

plugins {
    alias(libs.plugins.ktlint)
    alias(libs.plugins.markdownLint)
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false
}

allprojects {
    version = project.stringProperty("PUBLISH_VERSION").toString()
    group = "com.shashluchok.medianotes.common"
    configureKtLint()
}

markdownlint {
    rules {
        +LineLengthRule { active = false }
    }
    excludes = listOf(".*/misc/.*")
}

fun Project.configureKtLint() {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    ktlint {
        filter {
            exclude("**/generated/**")
        }
    }
    dependencies {
        ktlintRuleset(rootProject.libs.ktlint.composeRules)
    }
}

fun Project.stringProperty(propertyName: String): String? {
    return findProperty(propertyName)?.toString()
}
