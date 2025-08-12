plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinKsp)
}

kotlin {

    jvm("desktop")

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.ktor.client.serialization)
                implementation(libs.ktor.client.json)
                implementation(libs.paging.common)

                implementation(libs.kotlinx.coroutinesCore)
                implementation(libs.koin.annotations)
                implementation(libs.koin.ksp.compiler)
                implementation(libs.koin.core)
                implementation("com.github.oshi:oshi-core:6.4.0")
            }
        }
    }

}
