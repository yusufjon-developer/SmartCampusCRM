package com.smartcampus.presentation.core.components.window

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import smartcampuscrm.presentation.generated.resources.Res
import smartcampuscrm.presentation.generated.resources.ic_close
import smartcampuscrm.presentation.generated.resources.ic_minimise
import smartcampuscrm.presentation.generated.resources.logo

@Composable
fun TopBar(
    onMinimiseRequest: () -> Unit,
    onCloseRequest: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    val topBarColor = MaterialTheme.colorScheme.surfaceVariant
    val contentColor = MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(topBarColor)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Image(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = "Logo",
                    colorFilter = ColorFilter.tint(contentColor),
                    modifier = Modifier
                        .heightIn(max = 32.dp)
                        .fillMaxHeight()
                        .clickable { showMenu = !showMenu }
                )

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    offset = DpOffset(x = 10.dp, y = 0.dp),
                    modifier = Modifier
                        .background(topBarColor)
                        .heightIn(max = 100.dp)
                        .widthIn(max = 124.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text("Restore", fontSize = 14.sp, color = contentColor) },
                        onClick = {
                            onMinimiseRequest()
                            showMenu = false
                        },
                        leadingIcon = {
                            Image(
                                painter = painterResource(Res.drawable.ic_minimise),
                                contentDescription = "Restore",
                                modifier = Modifier.size(14.dp),
                                colorFilter = ColorFilter.tint(contentColor),
                            )
                        },
                        modifier = Modifier.size(width = 124.dp, height = 20.dp)
                    )

                    DropdownMenuItem(
                        text = { Text("Close", fontSize = 14.sp, color = contentColor) },
                        onClick = {
                            onCloseRequest()
                            showMenu = false
                        },
                        leadingIcon = {
                            Image(
                                painter = painterResource(Res.drawable.ic_close),
                                contentDescription = "Close",
                                modifier = Modifier.size(14.dp),
                                colorFilter = ColorFilter.tint(contentColor)
                            )
                        },
                        modifier = Modifier.size(width = 124.dp, height = 20.dp)
                    )
                }
            }

            Text(
                text = "Smart Campus CRM",
                style = MaterialTheme.typography.titleMedium,
                color = contentColor
            )
        }

        Row {
            WindowControlButton(
                onClick = onMinimiseRequest,
                content = {
                    Image(
                        painter = painterResource(Res.drawable.ic_minimise),
                        contentDescription = "Minimise",
                        colorFilter = ColorFilter.tint(contentColor)
                    )
                }
            )

            WindowControlButton(
                onClick = onCloseRequest,
                content = {
                    Image(
                        painter = painterResource(Res.drawable.ic_close),
                        contentDescription = "Close",
                        colorFilter = ColorFilter.tint(contentColor)
                    )
                }
            )
        }
    }
}