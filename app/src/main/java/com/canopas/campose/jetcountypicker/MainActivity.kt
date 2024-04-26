package com.canopas.campose.jetcountypicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canopas.campose.countrypicker.CountryPickerView
import com.canopas.campose.countrypicker.CountryTextField
import com.canopas.campose.countrypicker.countryList
import com.canopas.campose.countrypicker.model.Country
import com.canopas.campose.countrypicker.model.PickerType
import com.canopas.campose.countypickerdemo.R
import com.canopas.campose.jetcountypicker.ui.theme.JetCountyPickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetCountyPickerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Column {
                        SampleCountryPickerDialog()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleCountryPickerDialog() {
    var showCountryPicker by rememberSaveable { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf<Country?>(null) }
    var pickerType by remember {
        mutableStateOf(PickerType.DIALOG)
    }

    Box {
        CountryTextField(
            label = stringResource(R.string.select_country_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 40.dp, end = 40.dp),
            textStyle = MaterialTheme.typography.bodyMedium,
            labelTextStyle = MaterialTheme.typography.labelMedium,
            selectedCountry = selectedCountry,
            defaultCountry = countryList(LocalContext.current).firstOrNull { it.code == "IN" },
            onShowCountryPicker = {
                showCountryPicker = true
            }, isPickerVisible = showCountryPicker
        )
    }

    Column(horizontalAlignment = Alignment.Start) {
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = stringResource(R.string.picker_type),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        PickerType.entries.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            pickerType = it
                        }
                    ),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = pickerType == it,
                    onClick = {
                        pickerType = it
                    }
                )
                Text(
                    text = stringResource(it.value),
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

    if (showCountryPicker) {
        CountryPickerView(
            pickerTitle = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(R.string.select_country_text),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            searchFieldTextStyle = MaterialTheme.typography.bodyMedium,
            placeholderTextStyle = MaterialTheme.typography.labelMedium,
            countriesTextStyle = MaterialTheme.typography.bodyMedium,
            onItemSelected = {
                selectedCountry = it
                showCountryPicker = false
            },
            pickerType = pickerType,
            onDismissRequest = {
                showCountryPicker = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetCountyPickerTheme {
        SampleCountryPickerDialog()
    }
}