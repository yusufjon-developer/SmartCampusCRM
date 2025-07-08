package com.smartcampus.crm

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.smartcampus.presentationCore.components.TopBar
import com.smartcampus.presentationCore.theme.SmartCampusTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun App() {
    SmartCampusTheme {
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