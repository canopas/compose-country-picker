package com.canopas.campose.countrypicker

import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryTextField(
    sheetState: ModalBottomSheetState,
    label: String = "",
    isError: Boolean = false,
    modifier: Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    selectedCountry: Country? = null,
    defaultCountry: Country? = null,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
) {

    val context = LocalContext.current
    val defaultSelectedCountry = remember {
        defaultCountry ?: countryList(context).first()
    }

    val scope = rememberCoroutineScope()
    val countryValue = "${defaultSelectedCountry.dial_code} ${defaultSelectedCountry.name}"

    OutlinedTextField(
        modifier = modifier
            .expandable(onExpandedChange = {
                scope.launch { sheetState.show() }
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
                    rotationZ = if (sheetState.isVisible) 180f else 0f
                }
            )
        }
    )
}

fun Modifier.expandable(
    onExpandedChange: () -> Unit
) = pointerInput(Unit) {
    forEachGesture {
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