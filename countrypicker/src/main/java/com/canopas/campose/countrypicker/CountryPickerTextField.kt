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
import androidx.compose.material3.TextFieldDefaults
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
import com.canopas.campose.countrypicker.model.Country
import kotlinx.coroutines.coroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryTextField(
    label: String = "",
    isError: Boolean = false,
    modifier: Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    selectedCountry: Country? = null,
    defaultCountry: Country? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
    ),
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
        label = { Text(label) },
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

fun Modifier.expandable(
    onExpandedChange: () -> Unit
) = pointerInput(Unit) {
    awaitEachGesture {
        coroutineScope {
            awaitPointerEventScope {
                var event: PointerEvent
                do {
                    event = awaitPointerEvent(PointerEventPass.Initial)
                } while (
                    !event.changes.all { it.changedToUp() }
                )
                onExpandedChange.invoke()
            }
        }
    }
}.semantics {
    onClick {
        onExpandedChange()
        true
    }
}