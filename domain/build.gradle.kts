plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.jetbrain.kotlin.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies{

    //kotlinx_serialization
    implementation(libs.jetbrain.kotlinx.serialization.json)

    //paging3 - common (안드로이드 의존성 없음)
    implementation(libs.androidx.paging.common)
}