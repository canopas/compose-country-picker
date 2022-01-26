package com.canopas.campose.countrypicker

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryFieldFour(
    label: String = "",
    textStyle: TextStyle = LocalTextStyle.current,
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
    val context = LocalContext.current
    val countries = remember { countryList(context) }

    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf(countries[0]) }

    val coroutineScope = rememberCoroutineScope()

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    Box(modifier = Modifier
        .wrapContentSize()
        .expandable(menuLabel = label, onExpandedChange = {
            coroutineScope.launch {
                if (!modalBottomSheetState.isVisible) {
                    expanded = true
                    modalBottomSheetState.show()
                } else {
                    expanded = false
                    modalBottomSheetState.hide()
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

        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetContent = {
                Box(
                    Modifier
                        .wrapContentSize()
                ) {
                    Text(text = "Hello from sheet")
                }

            }
        ) {

        }

    }


}