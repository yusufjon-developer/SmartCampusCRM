package com.smartcampus.presentationCore.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val lightTheme = lightColorScheme(
    primary = Color(0xFF233255), // Тёмно-синий (индиго)
    onPrimary = Color(0xFFFDFDF5), // Почти белый (слоновая кость)
    primaryContainer = Color(0xFFD1E0FF), // Светло-голубой (небесный)
    onPrimaryContainer = Color(0xFF001D40), // Очень тёмный синий
    inversePrimary = Color(0xFFABC7FF), // Пастельно-голубой

    secondary = Color(0xFFF6AD2B), // Ярко-оранжевый (медово-жёлтый)
    onSecondary = Color(0xFFE9A328), // Тёплый янтарный
    secondaryContainer = Color(0xFFFFE7B2), // Светлый песочный (кремовый)
    onSecondaryContainer = Color(0xFF422B00), // Коричнево-оливковый

    tertiary = Color(0xFFB0B8C1), // Светло-серый с холодным подтоном
    onTertiary = Color(0xFF1E2A38), // Глубокий тёмно-серый
    tertiaryContainer = Color(0xFFE4E9EF), // Серо-голубой контейнер
    onTertiaryContainer = Color(0xFF2C3C50), // Сине-серый текст

    background = Color(0xFFFFFFFF), // Белый
    onBackground = Color(0xFF5A6580),
    surface = Color(0xFFFFFFFF), // Белый
    onSurface = Color(0xFF2E3A59), // Глубокий синий
    surfaceVariant = Color(0xFFE0E4EC), // Светло-серый с голубым оттенком
    onSurfaceVariant = Color(0xFF444A59), // Сине-серый (мокрый асфальт)
    surfaceTint = Color(0xFF233255), // Тёмно-синий (индиго)

    inverseSurface = Color(0xFF2F2F39), // Очень тёмный графит
    inverseOnSurface = Color(0xFFF1F1F1), // Светло-серый

    error = Color(0xFFB3261E), // Алый (тревожный красный)
    onError = Color(0xFFFFFFFF), // Белый
    errorContainer = Color(0xFFF9DEDC), // Светло-розовый (нежный коралл)
    onErrorContainer = Color(0xFF410E0B), // Тёмный бордовый (кирпичный)

    outline = Color(0xFF8A94A6), // Холодный серый с синим подтоном
    outlineVariant = Color(0xFFC4CAD7), // Голубовато-серый
    scrim = Color(0x99000000), // Прозрачный чёрный (60%)

    surfaceBright = Color(0xFFFAFAFA), // Очень светлый серый
    surfaceContainer = Color(0xFFF3F5F9), // Голубовато-светлый серый
    surfaceContainerHigh = Color(0xFFE8EBF0), // Светло-серый
    surfaceContainerHighest = Color(0xFFE1E4EB), // Светло-серый
    surfaceContainerLow = Color(0xFFF6F8FA), // Почти белый с холодным оттенком
    surfaceContainerLowest = Color(0xFFFFFFFF), // Белый
    surfaceDim = Color(0xFFE3E6EB) // Серо-голубой
)