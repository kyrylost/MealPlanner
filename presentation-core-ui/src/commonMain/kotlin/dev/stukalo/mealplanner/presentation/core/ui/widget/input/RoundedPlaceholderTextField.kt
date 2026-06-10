package dev.stukalo.mealplanner.presentation.core.ui.widget.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme

@Composable
fun RoundedPlaceholderTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    textStyle: TextStyle = Theme.typography.bodyLarge,
    activeColor: Color = Color.Black,
    inactiveColor: Color = Color.Gray,
    backgroundColor: Color = Color.Transparent,
    cornerRadiusDp: Dp = 16.dp,
    contentPaddingDp: Dp = 20.dp,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    error: String? = null,
    onClick: () -> Unit = { },
) {
    var isFocused by remember { mutableStateOf(false) }
    val borderColor by animateColorAsState(
        targetValue = when {
            error != null -> Theme.color.error
            isFocused -> activeColor
            else -> inactiveColor
        }
    )
    val textColor by animateColorAsState(targetValue = if (isFocused) activeColor else inactiveColor)

    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(cornerRadiusDp))
                .background(backgroundColor)
                .border(
                    width = Theme.thickness.thickness1,
                    color = borderColor,
                    shape = RoundedCornerShape(cornerRadiusDp)
                )
                .padding(contentPaddingDp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = singleLine,
                textStyle = textStyle.copy(color = textColor),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
                readOnly = readOnly,
                enabled = enabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onClick()
                    }
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    }
            )

            // Placeholder: shown only when text is empty
            if (value.text.isEmpty()) {
                // show placeholder; color depends on active/inactive too (here we use a lighter gray)
                val placeholderColor =
                    if (isFocused) inactiveColor.copy(alpha = 0.6f)
                    else inactiveColor.copy(alpha = 0.8f)
                Text(
                    text = placeholder,
                    style = textStyle.copy(color = placeholderColor),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
        }

        if (error != null) {
            Text(
                text = error,
                color = Theme.color.error,
                style = Theme.typography.bodyNormal,
                modifier = Modifier.padding(top = 4.dp, start = 16.dp)
            )
        }
    }
}

@Composable
fun RoundedPlaceholderTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    textStyle: TextStyle = Theme.typography.bodyLarge,
    activeColor: Color = Color.Black,
    inactiveColor: Color = Color.Gray,
    backgroundColor: Color = Color.Transparent,
    cornerRadiusDp: Dp = 16.dp,
    contentPaddingDp: Dp = 20.dp,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    error: String? = null,
    onClick: () -> Unit = { },
) {
    var isFocused by remember { mutableStateOf(false) }
    val borderColor by animateColorAsState(
        targetValue = when {
            error != null -> Theme.color.error
            isFocused -> activeColor
            else -> inactiveColor
        }
    )
    val textColor by animateColorAsState(targetValue = if (isFocused) activeColor else inactiveColor)

    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(cornerRadiusDp))
                .background(backgroundColor)
                .border(
                    width = Theme.thickness.thickness1,
                    color = borderColor,
                    shape = RoundedCornerShape(cornerRadiusDp)
                )
                .padding(contentPaddingDp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = singleLine,
                textStyle = textStyle.copy(color = textColor),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
                readOnly = readOnly,
                enabled = enabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onClick()
                    }
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    }
            )

            if (value.isEmpty()) {
                val placeholderColor =
                    if (isFocused) inactiveColor.copy(alpha = 0.6f)
                    else inactiveColor.copy(alpha = 0.8f)
                Text(
                    text = placeholder,
                    style = textStyle.copy(color = placeholderColor),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
        }

        if (error != null) {
            Text(
                text = error,
                color = Theme.color.error,
                style = Theme.typography.bodyNormal,
                modifier = Modifier.padding(top = 4.dp, start = 16.dp)
            )
        }
    }
}
