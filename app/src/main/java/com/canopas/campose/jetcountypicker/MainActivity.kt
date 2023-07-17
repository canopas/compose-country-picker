package com.canopas.campose.jetcountypicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canopas.campose.countrypicker.CountryPickerBottomSheet
import com.canopas.campose.countrypicker.CountryTextField
import com.canopas.campose.countrypicker.countryList
import com.canopas.campose.countrypicker.model.Country
import com.canopas.campose.jetcountypicker.ui.theme.JetCountyPickerTheme
import com.canopas.campose.jetcountypicker.ui.theme.poppins

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

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SampleCountryPicker() {
    Box {
        var selectedCountry by remember { mutableStateOf<Country?>(null) }
        val modalBottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden
        )

        val keyboardController = LocalSoftwareKeyboardController.current

        LaunchedEffect(key1 = modalBottomSheetState.isVisible, block = {
            if (!modalBottomSheetState.isVisible) {
                keyboardController?.hide()
            }
        })

        CountryPickerBottomSheet(sheetState = modalBottomSheetState,
            customFontFamily = poppins,
            bottomSheetTitle = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(R.string.select_country_text),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = poppins
                )
            },
            onItemSelected = {
                selectedCountry = it
            }) {
            CountryTextField(
                sheetState = modalBottomSheetState,
                label = stringResource(R.string.select_country_text),
                modifier = Modifier
                    .padding(top = 50.dp, start = 50.dp, end = 50.dp)
                    .align(Alignment.TopCenter),
                selectedCountry = selectedCountry,
                defaultCountry = countryList(LocalContext.current).firstOrNull { it.code == "IN" },
                customFontFamily = poppins
            )
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