package com.smartcampus.presentationCore.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFFFF6358)               // primary
val OnPrimary = Color(0xFFFFFFFF)
val PrimaryContainer = Color(0xFFFFECEB)      // primary-subtle
val OnPrimaryContainer = Color(0xFF5C201C)

val Secondary = Color(0xFF666666)
val OnSecondary = Color(0xFFFFFFFF)
val SecondaryContainer = Color(0xFFFAFAFA)
val OnSecondaryContainer = Color(0xFF141414)

val Tertiary = Color(0xFF03A9F4)
val OnTertiary = Color(0xFFFFFFFF)
val TertiaryContainer = Color(0xFFD8F1FD)
val OnTertiaryContainer = Color(0xFF023F5C)

val Success = Color(0xFF37B400)
val OnSuccess = Color(0xFFFFFFFF)

val Warning = Color(0xFFFFC000)
val OnWarning = Color(0xFF3D3D3D)

val Error = Color(0xFFF31700)
val OnError = Color(0xFFFFFFFF)

val Background = Color(0xFFF5F5F5)            // base
val OnBackground = Color(0xFF3D3D3D)

val Surface = Color(0xFFFFFFFF)               // surface
val OnSurface = Color(0xFF3D3D3D)

val Outline = Color(0xFFADADAD)               // gray 8

// При необходимости: info, subtle и др.


val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    error = Error,
    onError = OnError,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    outline = Outline
)

val DarkColors = darkColorScheme(
    primary = Color(0xFFFF6358),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF5C201C),
    onPrimaryContainer = Color(0xFFFFECEB),

    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = Color(0xFF666666),
    onSecondaryContainer = Color(0xFFFFFFFF),

    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = Color(0xFF03A9F4),
    onTertiaryContainer = Color(0xFFFFFFFF),

    error = Color(0xFFF31700),
    onError = Color(0xFFFFFFFF),
    background = Color(0xFF292929),
    onBackground = Color(0xFFF5F5F5),
    surface = Color(0xFF292929),
    onSurface = Color(0xFFF5F5F5),

    outline = Color(0xFF525252)
)

