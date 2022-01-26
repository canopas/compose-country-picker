package com.canopas.campose.countrypicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryField(
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

    Box(modifier = modifier) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            modifier = Modifier.fillMaxWidth(),
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                readOnly = true,
                label = { Text(label) },
                value = " ${selectedCountry.dial_code} ${selectedCountry.name}", onValueChange = {},
                modifier = modifier, textStyle = textStyle, shape = shape,
                colors = colors,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                modifier = Modifier.fillMaxWidth(),
                onDismissRequest = {
                    expanded = false
                }
            ) {

                LazyColumn {
                    items(countries) { selectionOption ->
                        Row(modifier = Modifier.clickable {
                            selectedCountry = selectionOption
                            expanded = false
                        }) {
                            Text(
                                text = selectionOption.name,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                            Text(
                                text = selectionOption.dial_code,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                        }


                    }
                }
            }
        }
    }

}

