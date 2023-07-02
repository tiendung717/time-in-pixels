import deps.dependOn
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("com.google.firebase.crashlytics")
}

val versions = rootProject.file("version.properties")
val props = Properties()
props.load(FileInputStream(versions))
val major = props["majorVersion"].toString().toInt()
val minor = props["minorVersion"].toString().toInt()
val patch = props["patchVersion"].toString().toInt()

android {
    compileSdk = Build.compileSdk

    defaultConfig {
        applicationId = Build.applicationId
        minSdk = Build.minSdk
        targetSdk = Build.targetSdk
        versionCode = 10000 * major + 1000 * minor + 10 * patch
        versionName = "$major.$minor.$patch"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "DYNAMIC_LINK_DOMAIN", "\"tech.cleverone.honorguest\"")
    }

    signingConfigs {
        create("teleprompter") {
            keyAlias = "teleprompter"
            keyPassword = "tele123"
            storeFile = file("tele.jks")
            storePassword = "tele123"
        }
    }

    buildTypes {
        named("debug") {
            signingConfig = signingConfigs.getByName("teleprompter")
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            versionNameSuffix = "-debug"
        }

        named("release") {
            signingConfig = signingConfigs.getByName("teleprompter")
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile(Build.proguard_android),
                Build.proguard_common,
                Build.proguard_specific
            )
        }
    }

    flavorDimensions.add("app")

    productFlavors {
        create("prod") {
            dimension = "app"
            applicationIdSuffix = ".prod"
        }

        create("qa") {
            dimension = "app"
            applicationIdSuffix = ".qa"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = deps.Compose.Versions.composeCompiler
    }

    applicationVariants.all(ApplicationVariantAction())
}

repositories {
    mavenCentral()
    google()
}

dependencies {

    // Module dependencies
    implementation(project(":feature:ads"))
    implementation(project(":feature:purchase"))
    implementation(project(":base:theme"))
    implementation(project(":base:common"))
    implementation(project(":base:ui"))
    implementation(project(":base:persistence"))
    implementation(project(":base:navigation"))

    // Standard dependencies
    dependOn(
        deps.AndroidX,
        deps.Hilt,
        deps.Compose,
        deps.Log,
        deps.Glide,
        deps.Coroutine,
        deps.Room,
        deps.Firebase,
        deps.CameraX,
        deps.Test,
        deps.Ads,
        deps.Billing
    )
}

class ApplicationVariantAction : Action<com.android.build.gradle.api.ApplicationVariant> {
    override fun execute(variant: com.android.build.gradle.api.ApplicationVariant) {
        val fileName = createFileName(variant)
        variant.outputs.all(VariantOutputAction(fileName))
    }

    private fun createFileName(variant: com.android.build.gradle.api.ApplicationVariant): String {
        return "SolidApp.apk"
    }

    class VariantOutputAction(
        private val fileName: String
    ) : Action<com.android.build.gradle.api.BaseVariantOutput> {
        override fun execute(output: com.android.build.gradle.api.BaseVariantOutput) {
            if (output is com.android.build.gradle.internal.api.BaseVariantOutputImpl) {
                output.outputFileName = fileName
            }
        }
    }
}

