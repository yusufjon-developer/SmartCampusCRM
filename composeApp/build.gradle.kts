import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinKsp)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(projects.presentation)
            implementation(projects.domain)
            implementation(projects.data)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.materialIconsExtended)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.navigation.compose)
            implementation(libs.material3.windowsSizeClass)

            implementation(libs.coil.network.ktor)

            implementation(libs.bundles.ktor.common)
            implementation(libs.paging.common)

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
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}


compose.desktop {
    application {
        mainClass = "com.smartcampus.crm.MainKt"

        nativeDistributions {
            targetFormats(
                TargetFormat.Msi,
                TargetFormat.Exe
            )
            packageName = "Smart Campus CRM"
            packageVersion = "1.0.0"

            windows {
                iconFile.set(project.file("icon.ico"))
                menuGroup = ""
                shortcut = true
            }
        }
    }
}
