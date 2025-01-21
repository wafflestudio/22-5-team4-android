plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.androidx.navigation.safe.args)

}

android {

    android {
        buildFeatures {
            viewBinding = true
            buildConfig = true
        }
    }
    namespace = "com.example.interpark"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.interpark"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
// 실제 키 입력
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.moshi)
    implementation(libs.moshiKotlin)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofitMoshiConverter)
    implementation(libs.okhttp)
    implementation(libs.okhttpLoggingInterceptor)
    implementation(libs.coil)
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    val fragment_version = "1.8.5"
    // Java language implementation
    implementation("androidx.fragment:fragment:$fragment_version")
    // Kotlin
    implementation("androidx.fragment:fragment-ktx:$fragment_version")

    implementation(libs.material)
    implementation(libs.viewpager2)
    implementation(libs.androidx.recyclerview)
    implementation("com.squareup.okhttp3:okhttp-urlconnection:4.9.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3") // Fragment 전환
    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")

//    implementation (libs.retrofit.gson)
//    implementation (libs.okhttp.logging)
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)
    implementation("com.kakao.sdk:v2-user:2.20.0") // 카카오 로그인 모듈

}