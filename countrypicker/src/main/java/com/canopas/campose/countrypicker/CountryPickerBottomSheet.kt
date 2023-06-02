package com.canopas.campose.countrypicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.canopas.campose.countrypicker.model.Country
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryPickerBottomSheet(
    sheetState: ModalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
    bottomSheetTitle: @Composable () -> Unit,
    onItemSelected: (country: Country) -> Unit,
    content: @Composable () -> Unit
) {
    var searchValue by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            bottomSheetTitle()

            CountrySearchView(searchValue) {
                searchValue = it
            }

            Countries(searchValue) {
                scope.launch { sheetState.hide() }
                onItemSelected(it)
            }
        }
    ) {
        content()
    }
}

@Composable
fun Countries(
    searchValue: String,
    onItemSelected: (country: Country) -> Unit
) {
    val context = LocalContext.current
    val defaultCountries = remember { countryList(context) }

    val countries = remember(searchValue) {
        if (searchValue.isEmpty()) {
            defaultCountries
        } else {
            defaultCountries.searchCountryList(searchValue)
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        items(countries) { country ->
            Row(modifier = Modifier
                .clickable { onItemSelected(country) }
                .padding(12.dp))
            {
                Text(text = localeToEmoji(country.code))
                Text(
                    text = country.name,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(2f)
                )
                Text(
                    text = country.dial_code,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
            Divider(
                color = Color.LightGray, thickness = 0.5.dp
            )
        }
    }

}
