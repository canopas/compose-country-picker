package com.canopas.campose.countrypicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.canopas.campose.countrypicker.model.Country

@Composable
fun CountryPickerDialog(
    title: @Composable ()->Unit,
    onItemSelected: (country: Country) -> Unit,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    val countries = remember { countryList(context) }
    var selectedCountry by remember { mutableStateOf(countries[0]) }

    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {

            Column(modifier = Modifier.padding(10.dp)) {
                title()

                LazyColumn(contentPadding = PaddingValues(vertical = 10.dp)) {
                    items(countries) { country ->
                        Row(modifier = Modifier.clickable {
                            selectedCountry = country
                            onItemSelected(selectedCountry)
                        }.padding(10.dp)) {
                            Text(text = localeToEmoji(country.code))
                            Text(
                                text = country.name,
                                modifier = Modifier.padding(start = 6.dp).weight(2f)
                            )
                            Text(
                                text = country.dial_code,
                                modifier = Modifier
                                    .padding(start = 6.dp)
                            )
                        }
                        Divider(
                            color = Color.LightGray, thickness = 0.5.dp
                        )


                    }
                }
            }

        }
    }

}