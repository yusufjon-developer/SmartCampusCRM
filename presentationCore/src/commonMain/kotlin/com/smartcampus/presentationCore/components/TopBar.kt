package com.smartcampus.presentationCore.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.smartcampus.presentationCore.components.buttons.WindowControlButton
import org.jetbrains.compose.resources.painterResource
import smartcampuscrm.presentationcore.generated.resources.Res
import smartcampuscrm.presentationcore.generated.resources.icon

@Composable
fun TopBar(icon: Painter = painterResource(Res.drawable.icon)) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Image(
                painter = icon,
                contentDescription = "Logo"
            )
            Text(text = "Smart Campus CRM")
        }

        Row {
            WindowControlButton(
                {},
                content = {
                    Text("Minimize")
                }
            )
            WindowControlButton(
                {},
                content = {
                    Text("Maximize")
                }
            )
            WindowControlButton(
                {},
                content = {
                    Text("Close")
                }
            )
        }
    }
}