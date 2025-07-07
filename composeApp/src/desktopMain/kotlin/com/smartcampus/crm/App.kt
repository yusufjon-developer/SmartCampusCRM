package com.smartcampus.crm

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.smartcampus.presentationCore.components.TopBar
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartcampuscrm.composeapp.generated.resources.Res
import smartcampuscrm.composeapp.generated.resources.icon
import smartcampuscrm.composeapp.generated.resources.anta_regular

@Preview
@Composable
fun App() {
    MaterialTheme {
        TopBar()
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .defaultMinSize(minWidth = 300.dp, minHeight = 200.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Image(
//                contentDescription = "Logo",
//                painter = painterResource( Res.drawable.icon),
//                contentScale = ContentScale.FillHeight,
//                modifier = Modifier.fillMaxHeight(.15f).defaultMinSize(minHeight = 96.dp)
//            )
//            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
//            Column {
//                Text(
//                    "Smart Campus",
//                    style = TextStyle(
//                        fontFamily = FontFamily(Font(Res.font.anta_regular))
//                    )
//                )
//            }
//        }
    }
}