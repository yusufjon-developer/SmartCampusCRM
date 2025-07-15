package com.smartcampus.crm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.smartcampus.presentationCore.PreviewThemeAndComponents
import com.smartcampus.presentationCore.components.TopBar
import com.smartcampus.presentationCore.theme.SmartCampusTheme
import org.jetbrains.compose.resources.painterResource
import smartcampuscrm.composeapp.generated.resources.Res
import smartcampuscrm.composeapp.generated.resources.icon
import java.awt.Insets
import java.awt.Toolkit

fun main() = application {
    val windowState = rememberWindowState()
    var isManuallyMaximized by remember { mutableStateOf(false) }

    var floatingSizeBeforeManualMaximize by remember { mutableStateOf(windowState.size) }
    var floatingPositionBeforeManualMaximize by remember { mutableStateOf(windowState.position) }

    SmartCampusTheme {
        Window(
            onCloseRequest = ::exitApplication,
            title = "SmartCampus CRM",
            icon = painterResource(Res.drawable.icon),
            undecorated = true,
            state = windowState
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                WindowDraggableArea {
                    TopBar(
                        onCloseRequest = ::exitApplication,
                        onMinimiseRequest = { windowState.isMinimized = true },
                        onMinMaxRequest = {
                            if (isManuallyMaximized) {
                                windowState.size = floatingSizeBeforeManualMaximize
                                windowState.position = floatingPositionBeforeManualMaximize

                                if (windowState.placement == WindowPlacement.Maximized) {
                                    windowState.placement = WindowPlacement.Floating
                                }

                                isManuallyMaximized = false
                            } else {
                                floatingSizeBeforeManualMaximize = windowState.size
                                floatingPositionBeforeManualMaximize = windowState.position

                                val toolkit = Toolkit.getDefaultToolkit()
                                val screenBounds = toolkit.screenSize
                                val screenInsets: Insets =
                                    toolkit.getScreenInsets(window.graphicsConfiguration)

                                val newWidth =
                                    screenBounds.width - screenInsets.left - screenInsets.right
                                val newHeight =
                                    screenBounds.height - screenInsets.top - screenInsets.bottom

                                windowState.size = DpSize(newWidth.dp, newHeight.dp)
                                windowState.position =
                                    WindowPosition(screenInsets.left.dp, screenInsets.top.dp)
                                isManuallyMaximized = true
                            }
                        },
                        isWindowMaximized = isManuallyMaximized || windowState.placement == WindowPlacement.Maximized,
                    )
                }

                App()
//                PreviewThemeAndComponents()
            }
        }
    }
}