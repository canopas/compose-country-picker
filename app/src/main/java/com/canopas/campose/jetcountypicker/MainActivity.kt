package com.canopas.campose.jetcountypicker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.canopas.campose.countrypicker.CountryField
import com.canopas.campose.countrypicker.CountryFieldFour
import com.canopas.campose.countrypicker.CountryFieldNew
import com.canopas.campose.countrypicker.CountryFieldThree
import com.canopas.campose.jetcountypicker.ui.theme.JetCountyPickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCountyPickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SampleCountryPicker()
                }
            }
        }
    }
}

@Composable
fun SampleCountryPicker() {
    Column {
        Box {
            CountryFieldFour(label = "Select country")
        }

        Button(onClick = {

        }) {
            Text(text = "Click me")
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