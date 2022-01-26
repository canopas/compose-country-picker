package com.canopas.campose.countrypicker

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryFieldNew(
    label: String = "",
    modifier: Modifier = Modifier.fillMaxWidth(),
    textStyle: TextStyle = LocalTextStyle.current,
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
    val context = LocalContext.current
    val countries = remember { countryList(context) }

    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf(countries[0]) }

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )
    )
    BottomSheetScaffold(
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Hello from sheet")
            }
        }, sheetPeekHeight = 0.dp,
        scaffoldState = bottomSheetScaffoldState
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .expandable(menuLabel = label, onExpandedChange = {
                coroutineScope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        expanded = true
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    } else {
                        expanded = false
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            })
        ) {
            OutlinedTextField(
                readOnly = true,
                label = { Text(label) },
                value = "${selectedCountry.dial_code} ${selectedCountry.name}",
                onValueChange = {},
                textStyle = textStyle, shape = shape,
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