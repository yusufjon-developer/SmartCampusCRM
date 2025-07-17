package com.smartcampus.presentationCore.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import smartcampuscrm.presentationcore.generated.resources.Res
import smartcampuscrm.presentationcore.generated.resources.anta_regular
import smartcampuscrm.presentationcore.generated.resources.geo_regular
import smartcampuscrm.presentationcore.generated.resources.gilroy_black
import smartcampuscrm.presentationcore.generated.resources.gilroy_bold
import smartcampuscrm.presentationcore.generated.resources.gilroy_extrabold
import smartcampuscrm.presentationcore.generated.resources.gilroy_light
import smartcampuscrm.presentationcore.generated.resources.gilroy_medium
import smartcampuscrm.presentationcore.generated.resources.gilroy_regular
import smartcampuscrm.presentationcore.generated.resources.gilroy_semibold
import smartcampuscrm.presentationcore.generated.resources.gilroy_thin
import smartcampuscrm.presentationcore.generated.resources.gilroy_ultralight

@Composable
internal fun SmartCampusTypography(): Typography {
    val gilroyFontFamily = FontFamily(
        Font(Res.font.gilroy_black, FontWeight.Black),
        Font(Res.font.gilroy_extrabold, FontWeight.ExtraBold),
        Font(Res.font.gilroy_bold, FontWeight.Bold),
        Font(Res.font.gilroy_semibold, FontWeight.SemiBold),
        Font(Res.font.gilroy_medium, FontWeight.Medium),
        Font(Res.font.gilroy_regular, FontWeight.Normal),
        Font(Res.font.gilroy_light, FontWeight.Light),
        Font(Res.font.gilroy_ultralight, FontWeight.ExtraLight),
        Font(Res.font.gilroy_thin, FontWeight.Thin)
    )

    val geoFontFamily = FontFamily(
        Font(Res.font.geo_regular, FontWeight.Normal)
    )

    val antaFontFamily = FontFamily(
        Font(Res.font.anta_regular, FontWeight.Normal)
    )

    return Typography(
        displayLarge = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 57.sp,
            lineHeight = 64.sp,
            letterSpacing = (-0.25).sp
        ),
        displayMedium = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp,
            lineHeight = 52.sp,
            letterSpacing = 0.sp
        ),
        displaySmall = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            lineHeight = 44.sp,
            letterSpacing = 0.sp
        ),

        headlineLarge = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp
        ),

        titleLarge = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        titleMedium = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        ),
        titleSmall = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),

        bodyLarge = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp
        ),
        bodySmall = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp
        ),

        labelLarge = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        labelMedium = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
            fontFamily = gilroyFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
    )
}