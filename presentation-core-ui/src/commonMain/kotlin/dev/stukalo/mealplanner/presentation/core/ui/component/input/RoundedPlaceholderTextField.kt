package dev.stukalo.mealplanner.presentation.core.ui.component.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
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
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = Theme.typography.regular14,
    activeColor: Color = Theme.color.text.primary,
    inactiveColor: Color = Theme.color.text.secondary,
    backgroundColor: Color = Color.Transparent,
    cornerRadiusDp: Dp = 16.dp,
    contentPaddingDp: Dp = 20.dp,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    cursorColor: Color = Theme.color.text.primary,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
    error: String? = null,
    onClick: () -> Unit = { },
    onAction: () -> Unit = { }
) {
    var isFocused by remember { mutableStateOf(false) }
    val borderColor by animateColorAsState(
        targetValue =
        when {
            error != null -> Theme.color.state.error
            isFocused -> activeColor
            else -> inactiveColor
        }
    )
    val textColor by animateColorAsState(
        targetValue = if (isFocused) activeColor else inactiveColor
    )

    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(cornerRadiusDp))
                .background(backgroundColor)
                .border(
                    width = Theme.thickness.thickness1,
                    color = borderColor,
                    shape = RoundedCornerShape(cornerRadiusDp)
                ).padding(contentPaddingDp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(Theme.spacing.space8))
                }

                Box(modifier = Modifier.weight(1f)) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        singleLine = singleLine,
                        textStyle = textStyle.copy(color = textColor),
                        cursorBrush = SolidColor(cursorColor),
                        keyboardOptions = keyboardOptions,
                        readOnly = readOnly,
                        enabled = enabled,
                        keyboardActions = KeyboardActions(
                            onAny = {
                                onAction()
                            }
                        ),
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                onClick()
                            }.onFocusChanged { focusState ->
                                isFocused = focusState.isFocused
                            }
                    )

                    if (value.text.isEmpty()) {
                        val placeholderColor =
                            if (isFocused) {
                                inactiveColor.copy(alpha = 0.6f)
                            } else {
                                inactiveColor.copy(alpha = 0.8f)
                            }
                        Text(
                            text = placeholder,
                            style = textStyle.copy(color = placeholderColor),
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                }

                if (trailingIcon != null) {
                    Spacer(modifier = Modifier.width(Theme.spacing.space8))
                    trailingIcon()
                }
            }
        }

        if (error != null) {
            Text(
                text = error,
                color = Theme.color.state.error,
                style = Theme.typography.regular12,
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
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = Theme.typography.regular14,
    activeColor: Color = Theme.color.text.primary,
    inactiveColor: Color = Theme.color.text.secondary,
    backgroundColor: Color = Color.Transparent,
    cornerRadiusDp: Dp = 16.dp,
    contentPaddingDp: Dp = 20.dp,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    cursorColor: Color = Theme.color.text.primary,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
    error: String? = null,
    onClick: () -> Unit = { },
    onAction: () -> Unit = { }
) {
    var textFieldValueState by remember {
        mutableStateOf(TextFieldValue(text = value, selection = TextRange(value.length)))
    }

    LaunchedEffect(value) {
        if (value != textFieldValueState.text) {
            textFieldValueState =
                textFieldValueState.copy(
                    text = value,
                    selection = TextRange(value.length)
                )
        }
    }

    RoundedPlaceholderTextField(
        value = textFieldValueState,
        onValueChange = { newValue ->
            textFieldValueState = newValue
            onValueChange(newValue.text)
        },
        modifier = modifier,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        textStyle = textStyle,
        activeColor = activeColor,
        inactiveColor = inactiveColor,
        backgroundColor = backgroundColor,
        cornerRadiusDp = cornerRadiusDp,
        contentPaddingDp = contentPaddingDp,
        singleLine = singleLine,
        imeAction = imeAction,
        cursorColor = cursorColor,
        readOnly = readOnly,
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        error = error,
        onClick = onClick,
        onAction = onAction
    )
}
