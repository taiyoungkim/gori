import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "designsystem"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.peekaboo.ui)
                implementation(libs.peekaboo.image.picker)
                implementation(libs.kotlinx.datetime)
                dependsOn(commonMain.get())
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                dependsOn(commonMain.get())
            }
        }

        iosMain {
            dependencies {
                dependsOn(commonMain.get())
            }
        }
    }

}
android {
    namespace = "com.team_gori.gori.designsystem"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}
dependencies {
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.ui.graphics.android)
    implementation(libs.foundation.android)
}
