package com.smartcampus.presentation.core.components.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerticalProfileTabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp,
    indicatorHeight: Dp = 3.dp
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.surface
    } else {
        Color.Transparent
    }
    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val indicatorColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(if (isSelected) TopRoundedCornerShape(cornerRadius) else RoundedCornerShape(0.dp)) // Скругляем только активную
            .background(backgroundColor)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = title,
                color = contentColor,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                fontSize = 15.sp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(indicatorHeight)
                .background(indicatorColor)
        )
    }
}

class TopRoundedCornerShape(private val cornerRadius: Dp) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val radius = with(density) { cornerRadius.toPx() }
        val path = Path().apply {
            // Начинаем с нижнего левого угла и идем против часовой стрелки
            moveTo(0f, size.height) // Нижний левый угол
            lineTo(0f, radius)      // Двигаемся вверх по левой стороне до начала скругления

            // Верхний левый угол (дуга)
            arcTo(
                rect = Rect(left = 0f, top = 0f, right = 2 * radius, bottom = 2 * radius),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            // Прямая линия по верху до начала правого скругления
            lineTo(size.width - radius, 0f)

            // Верхний правый угол (дуга)
            arcTo(
                // ИСПРАВЛЕНИЕ ЗДЕСЬ: Правильный Rect для верхнего правого угла
                rect = Rect(
                    left = size.width - 2 * radius,
                    top = 0f,
                    right = size.width,
                    bottom = 2 * radius
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            // Двигаемся вниз по правой стороне
            lineTo(size.width, size.height) // Нижний правый угол

            // Закрываем путь (соединяем с начальной точкой moveTo)
            close()
        }
        return Outline.Generic(path)
    }
}