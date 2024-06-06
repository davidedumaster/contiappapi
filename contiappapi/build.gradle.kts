plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    namespace = "com.py.contiappapi"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.py.contiappapi"
            artifactId = "contilibrary"
            version = "0.0.3"
        }
    }

    repositories {
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/davidedumaster/contiappapi")
            credentials {
                username = "davidedumaster"
                password = "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"
            }
        }
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
