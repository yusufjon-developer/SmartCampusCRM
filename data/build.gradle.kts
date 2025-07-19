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
                implementation(projects.domain)

                implementation(libs.bundles.ktor.common)

                implementation(libs.kotlinx.coroutinesCore)
                implementation(libs.koin.annotations)
                implementation(libs.koin.ksp.compiler)
                implementation(libs.koin.core)

                implementation(libs.multiplatform.setting)
                implementation(libs.multiplatform.setting.coroutines)
            }
        }
    }

}

