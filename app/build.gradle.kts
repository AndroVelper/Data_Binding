import com.android.build.api.dsl.ViewBinding

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.shubham.apiandroomdatabaseimplementor"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.shubham.apiandroomdatabaseimplementor"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    dataBinding{
        enable = true
    }
    viewBinding{
        enable = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.roomDatabase)
    implementation(libs.androidx.roomKtx)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.navigationUI)
    implementation(libs.glide)
    implementation(libs.retrofit.gsonFactory)
    implementation(libs.ssp)
    implementation(libs.sdp)
    implementation(libs.androidx.swipeToRefresh)
    implementation(libs.retrofit)
    implementation(libs.jetbrains.coroutines)
    implementation(libs.splash)
    implementation(libs.androidx.viewModel)
    implementation(libs.androidx.liveData)
    implementation(libs.androidx.viewModelSavedState)
    kapt(libs.kapt.viewModelAnnotationProcessor)
    annotationProcessor(libs.androidx.roomDatabaseAnnotationProcessor)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    kapt(libs.androidx.roomDatabaseAnnotationProcessor)

}