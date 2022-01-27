package com.canopas.campose.countrypicker

import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import com.canopas.campose.countrypicker.model.Country
import kotlinx.coroutines.coroutineScope

@Composable
fun CountryTextField(
    label: String = "",
    expanded: Boolean = false,
    selectedCountry: Country? = null,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    onExpandedChange: () -> Unit,
) {

    Box(
        modifier = Modifier
            .wrapContentSize()
            .expandable(menuLabel = label, onExpandedChange = onExpandedChange)

    ) {
        OutlinedTextField(
            readOnly = true,
            label = { Text(label) },
            value = if (selectedCountry == null) "" else "${selectedCountry.dial_code} ${selectedCountry.name}",
            onValueChange = {},
            colors = colors,
            trailingIcon = {
                Icon(
                    Icons.Filled.ArrowDropDown,
                    null,
                    Modifier.rotate(
                        if (expanded)
                            180f
                        else
                            360f
                    )
                )
            }
        )
    }
}

@Composable
fun Modifier.expandable(
    onExpandedChange: () -> Unit,
    menuLabel: String
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
    contentDescription = menuLabel // this should be a localised string
    onClick {
        onExpandedChange()
        true
    }
}