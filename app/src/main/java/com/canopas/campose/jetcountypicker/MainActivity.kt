package com.canopas.campose.jetcountypicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canopas.campose.countrypicker.CountryPickerBottomSheet
import com.canopas.campose.countrypicker.CountryTextField
import com.canopas.campose.countrypicker.model.Country
import com.canopas.campose.jetcountypicker.ui.theme.JetCountyPickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCountyPickerTheme {
                Surface(color = MaterialTheme.colors.background) {
                    SampleCountryPicker()
                }
            }
        }
    }
}

@Composable
fun SampleCountryPicker() {
    Box {
        var expanded by remember { mutableStateOf(false) }
        var selectedCountry by remember { mutableStateOf<Country?>(null) }

        CountryPickerBottomSheet(title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "Select Country", textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }, expanded, onDismissRequest = {
            expanded = false
        }, onItemSelected = {
            selectedCountry = it
            expanded = false
        }) {
            CountryTextField(
                label = "Select country",
                modifier = Modifier
                    .padding(top = 50.dp)
                    .align(Alignment.TopCenter),
                expanded = expanded,
                selectedCountry = selectedCountry
            ) {
                expanded = !expanded
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetCountyPickerTheme {
        SampleCountryPicker()
    }
}