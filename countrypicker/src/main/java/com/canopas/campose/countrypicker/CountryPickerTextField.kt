package com.canopas.campose.countrypicker

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import com.canopas.campose.countrypicker.model.Country

/**
 * Composable for displaying a text field with a country picker.
 *
 * @param label The label for the text field.
 * @param isError Whether the text field should display an error state.
 * @param modifier The modifier for the text field.
 * @param shape The shape of the text field.
 * @param selectedCountry The currently selected country.
 * @param defaultCountry The default country to display if none is selected.
 * @param colors The colors for the text field.
 * @param textStyle The text style for the text field.
 * @param labelTextStyle The text style for the label.
 * @param isPickerVisible Whether the country picker bottom sheet is visible.
 * @param onShowCountryPicker Callback when the country picker is shown.
 */
@Composable
fun CountryTextField(
    label: String = "",
    isError: Boolean = false,
    modifier: Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    selectedCountry: Country? = null,
    defaultCountry: Country? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    textStyle: TextStyle = TextStyle(),
    labelTextStyle: TextStyle = TextStyle(),
    isPickerVisible: Boolean = false,
    onShowCountryPicker: () -> Unit
) {

    val context = LocalContext.current
    val defaultSelectedCountry = remember {
        defaultCountry ?: countryList(context).first()
    }

    val countryValue = "${defaultSelectedCountry.dial_code} ${defaultSelectedCountry.name}"

    OutlinedTextField(
        modifier = modifier
            .expandable(onExpandedChange = {
                onShowCountryPicker()
            }),
        readOnly = true,
        isError = isError,
        textStyle = textStyle,
        label = { Text(label, style = labelTextStyle) },
        value = if (selectedCountry == null) countryValue else "${selectedCountry.dial_code} ${selectedCountry.name}",
        onValueChange = {},
        colors = colors,
        shape = shape,
        trailingIcon = {
            Icon(
                Icons.Filled.ArrowDropDown,
                null,
                Modifier.graphicsLayer {
                    rotationZ = if (isPickerVisible) 180f else 0f
                }
            )
        }
    )
}

/**
 * Modifier for making a composable expandable when clicked.
 *
 * @param onExpandedChange Callback when the expandable state changes.
 */
internal fun Modifier.expandable(
    onExpandedChange: () -> Unit
) = this.pointerInput(Unit) {
    awaitEachGesture {
        var event: PointerEvent
        do {
            event = awaitPointerEvent(PointerEventPass.Initial)
        } while (
            !event.changes.all { it.changedToUp() }
        )
        onExpandedChange.invoke()
    }
}.semantics {
    onClick {
        onExpandedChange()
        true
    }
}