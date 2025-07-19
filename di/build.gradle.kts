plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinKsp)
}

kotlin {

    jvm("desktop")

    sourceSets {
        val desktopMain by getting
        commonMain.dependencies {
            implementation(projects.presentation)
            implementation(projects.data)
            implementation(projects.domain)

            implementation(libs.bundles.ktor.common)

            implementation(libs.kotlinx.coroutinesCore)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.annotations)
            implementation(libs.koin.ksp.compiler)
            implementation(libs.koin.core)

            implementation(libs.multiplatform.setting)
            implementation(libs.multiplatform.setting.coroutines)

            implementation(libs.slf4j.simple)
        }
    }

}

