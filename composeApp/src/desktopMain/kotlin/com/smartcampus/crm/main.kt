package com.smartcampus.crm

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.smartcampus.crm.di.AppModule
import com.smartcampus.crm.domain.models.Theme
import com.smartcampus.crm.domain.useCases.GetThemeUseCase
import com.smartcampus.crm.domain.utils.AppConfig
import com.smartcampus.presentation.core.components.TopBar
import com.smartcampus.presentation.core.theme.SmartCampusTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.get
import smartcampuscrm.presentation.generated.resources.Res
import smartcampuscrm.presentation.generated.resources.logo
import java.awt.Insets
import java.awt.Toolkit

fun main() = application {
    stopKoin()
    startKoin { modules(AppModule) }

    val getThemeUseCase: GetThemeUseCase = get(GetThemeUseCase::class.java)

    val windowState = rememberWindowState()
    var isWindowSetupComplete by remember { mutableStateOf(false) }
    var isDarkTheme: Boolean? by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        getThemeUseCase().collectLatest {
            when (it) {
                Theme.SYSTEM -> isDarkTheme = null
                Theme.DARK -> isDarkTheme = true
                Theme.LIGHT -> isDarkTheme = false
            }
        }
    }
    SmartCampusTheme(isDarkTheme ?: isSystemInDarkTheme()) {
        Window(
            onCloseRequest = ::exitApplication,
            title = AppConfig.APP_NAME,
            icon = painterResource(Res.drawable.logo),
            undecorated = true,
            state = windowState,
            transparent = true
        ) {
            val contentAlpha by animateFloatAsState(
                targetValue = if (isWindowSetupComplete) 1f else 0f,
                animationSpec = tween(durationMillis = 150) // Короткая анимация для плавности
            )

            /**
             * Этот LaunchedEffect отвечает за корректную установку размера и позиции undecorated окна
             * так, чтобы оно занимало всё доступное пространство экрана, как стандартное
             * максимизированное приложение (не перекрывая панель задач и другие системные элементы).
             *
             * ПРОБЛЕМЫ И ПОЧЕМУ ТАКОЕ РЕШЕНИЕ, А НЕ ПРОСТО `WindowPlacement.Maximized`:
             *
             * 1. НЕДОСТАТОЧНОСТЬ `WindowPlacement.Maximized` ДЛЯ UNDECORATED ОКОН:
             *    Для окон с `undecorated = true`, простая установка `windowState.placement = WindowPlacement.Maximized`
             *    часто не приводит к желаемому результату. Оконная система может некорректно
             *    рассчитать или применить геометрию для такого окна, что может привести к:
             *      - Перекрытию панели задач.
             *      - Неправильному размеру или позиции окна.
             *      - Некорректной работе на многомониторных конфигурациях.
             *    Поэтому требуется ручной расчет и установка размеров/позиции.
             *
             * 2. РАСЧЕТ ГЕОМЕТРИИ С УЧЕТОМ `screenInsets`:
             *    - `Toolkit.getDefaultToolkit().screenSize`: Получаем общие размеры основного экрана.
             *    - `window.graphicsConfiguration`: Получаем конфигурацию текущего графического устройства (монитора),
             *      на котором отображается окно. Это важно для многомониторных систем.
             *    - `toolkit.getScreenInsets(graphicsConfig)`: Получаем отступы (панель задач, док-панели и т.д.)
             *      для текущего экрана.
             *    - `newWidth`, `newHeight`: Рассчитываем фактическую доступную ширину и высоту.
             *    - `currentScreenDeviceBounds`: Границы текущего экрана (важно для определения `newX`, `newY`
             *      на не основных мониторах).
             *    - `newX`, `newY`: Рассчитываем корректную позицию верхнего левого угла окна.
             *
             * 3. ПРОБЛЕМА СИНХРОНИЗАЦИИ И "ДЕРГАНИЕ" ОКНА (TIMING ISSUE):
             *    - Применение рассчитанных размеров и позиции должно произойти после того, как окно
             *      и его `graphicsConfiguration` полностью инициализированы.
             *    - `delay(100)`: Эта задержка введена для решения проблемы "дергания" окна при запуске.
             *      Без небольшой задержки окно может сначала появиться с начальными/дефолтными размерами,
             *      а затем "прыгнуть" в рассчитанные размеры. Задержка дает системе время "успокоиться"
             *      и применить изменения более гладко. Оптимальное значение задержки может зависеть
             *      от системы и требует подбора; слишком большое значение увеличит видимое время запуска.
             *      Это компромисс для достижения корректного финального состояния.
             *
             * 4. УСТАНОВКА `window.isResizable = false`:
             *    - После того как окно приняло нужные размеры и позицию, мы делаем его не изменяемым
             *      по размеру пользователем, чтобы оно всегда оставалось "максимизированным"
             *      в нашем понимании.
             *
             * 5. ФЛАГ `isWindowSetupComplete`:
             *    - Этот флаг используется для сигнализации другим частям UI (например, для управления
             *      анимацией появления контента), что окно настроено и его геометрия корректна.
             *      Это помогает скрыть любые артефакты изменения размера/позиции от пользователя
             *      до того, как окно будет полностью готово.
             *
             * В ИТОГЕ: Этот блок кода представляет собой обходной путь для эмуляции поведения
             * "максимизированного" окна для `undecorated` окна в Compose Desktop, обеспечивая
             * корректное использование пространства экрана и учитывая особенности оконной системы,
             * которые не обрабатываются автоматически через `WindowPlacement.Maximized`.
             */
            LaunchedEffect(window.graphicsConfiguration) {

                val toolkit = Toolkit.getDefaultToolkit()
                val mainScreenBounds = toolkit.screenSize

                val graphicsConfig = window.graphicsConfiguration
                if (graphicsConfig != null) {
                    val screenInsets: Insets = toolkit.getScreenInsets(graphicsConfig)

                    val newWidth = mainScreenBounds.width - screenInsets.left - screenInsets.right
                    val newHeight = mainScreenBounds.height - screenInsets.top - screenInsets.bottom

                    val currentScreenDeviceBounds = graphicsConfig.bounds
                    val newX = currentScreenDeviceBounds.x + screenInsets.left
                    val newY = currentScreenDeviceBounds.y + screenInsets.top

                    delay(100) // Ключевой момент для решения проблемы "дергания" и корректного применения
                    windowState.size = DpSize(newWidth.dp, newHeight.dp)
                    windowState.position = WindowPosition(newX.dp, newY.dp)

                    window.isResizable = false // Запрещаем изменение размера после установки

                    if (!isWindowSetupComplete) { // Сигнализируем о завершении настройки
                        isWindowSetupComplete = true
                    }
                }
            }



            Surface(
                modifier = Modifier.fillMaxSize().alpha(contentAlpha),
                color = MaterialTheme.colorScheme.background
            ) {

                Column(modifier = Modifier.fillMaxSize()) {
                    TopBar(
                        onCloseRequest = ::exitApplication,
                        onMinimiseRequest = { windowState.isMinimized = true }
                    )

                    App()
//                    ThemeAndComponentPreview()

                }
            }

        }
    }
}