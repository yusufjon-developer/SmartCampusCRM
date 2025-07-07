package com.smartcampus.crm

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.painterResource
import smartcampuscrm.composeapp.generated.resources.Res
import smartcampuscrm.composeapp.generated.resources.icon
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SmartCampus CRM",
        icon = painterResource(Res.drawable.icon),
        undecorated = true
    ) {
        window.minimumSize = Dimension(800, 600)
        App()
    }
}