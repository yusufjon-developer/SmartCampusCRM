package com.smartcampus.presentationCore.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.smartcampus.presentationCore.components.buttons.WindowControlButton
import org.jetbrains.compose.resources.painterResource
import smartcampuscrm.presentationcore.generated.resources.Res
import smartcampuscrm.presentationcore.generated.resources.ic_close
import smartcampuscrm.presentationcore.generated.resources.ic_maximize
import smartcampuscrm.presentationcore.generated.resources.ic_minimise
import smartcampuscrm.presentationcore.generated.resources.ic_minimize
import smartcampuscrm.presentationcore.generated.resources.icon

@Composable
fun TopBar(icon: Painter = painterResource(Res.drawable.icon)) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color(0xFF233255))
            .padding(vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = icon,
                contentDescription = "Logo",
                colorFilter = ColorFilter.tint(Color(0xFFFDFDF5)),
                modifier = Modifier.heightIn(max = 32.dp).fillMaxHeight()
            )
            Text(text = "Smart Campus CRM", style = MaterialTheme.typography.titleMedium, color = Color(0xFFFDFDF5))
        }

        Row {
            WindowControlButton(
                {},
                content = {
                    Image(
                        painter = painterResource(Res.drawable.ic_minimise),
                        contentDescription = "Minimise",
                        colorFilter = ColorFilter.tint(Color(0xFFFDFDF5))
                    )
                }
            )
            WindowControlButton(
                {},
                content = {
                    Image(
                        painter = painterResource(Res.drawable.ic_minimize),
                        contentDescription = "Minimise",
                        colorFilter = ColorFilter.tint(Color(0xFFFDFDF5))
                    )
                }
            )
            WindowControlButton(
                {},
                content = {
                    Image(
                        painter = painterResource(Res.drawable.ic_close),
                        contentDescription = "Minimise",
                        colorFilter = ColorFilter.tint(Color(0xFFFDFDF5))
                    )
                }
            )
        }
    }
}