plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
    dependencies {
        implementation(libs.kotlinx.coroutinesCore)
        implementation(libs.koin.annotations)
        implementation(libs.koin.ksp.compiler)
        implementation(libs.koin.core)
    }
}
