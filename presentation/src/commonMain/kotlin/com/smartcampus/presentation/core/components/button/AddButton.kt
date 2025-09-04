package com.smartcampus.presentation.core.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * A customizable "Add" button with a tertiary appearance, an icon, and text.
 *
 * @param onClick Callback to be invoked when the button is clicked.
 * @param modifier Modifier to be applied to the button.
 * @param text The text to display on the button. Defaults to "Добавить".
 * @param icon The icon to display on the button. Defaults to a plus icon.
 * @param contentColor The color for the text and icon. Defaults to MaterialTheme.colorScheme.tertiary.
 *                     If you provide custom button colors via [colors], this might be overridden by them.
 * @param shape The shape of the button's container. Defaults to [ButtonDefaults.textShape].
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be clickable.
 * @param interactionSource The [MutableInteractionSource] representing the stream of
 *                          Interactions for this button. You can create and pass in your own
 *                          remembered instance to observe Interactions and customize the
 *                          appearance / behavior of this button in different states.
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different states.
 *                  This controls the size of the shadow below the button. Pass `null` for no elevation.
 * @param border Border to draw around the button.
 * @param contentPadding The spacing values to apply internally between the button and the content.
 */
@Composable
fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Добавить",
    icon: ImageVector = Icons.Filled.Add,
    contentColor: Color = MaterialTheme.colorScheme.onTertiaryContainer.copy(.65f),
    shape: Shape = ButtonDefaults.textShape,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = null,
    border: BorderStroke = BorderStroke(2.dp, if(enabled) contentColor else contentColor.copy(alpha = .38f)),
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    ) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.textButtonColors(
            contentColor = contentColor,
            disabledContentColor = contentColor.copy(alpha = 0.38f)
        ),
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}