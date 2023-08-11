package com.canopas.campose.countrypicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.canopas.campose.countrypicker.model.Country
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryPickerBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = BottomSheetDefaults.Elevation,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    bottomSheetTitle: @Composable () -> Unit,
    onItemSelected: (country: Country) -> Unit,
    onDismissRequest: () -> Unit
) {
    var searchValue by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        scrimColor = scrimColor
    ) {
        bottomSheetTitle()

        CountrySearchView(searchValue) {
            searchValue = it
        }

        Countries(searchValue) {
            scope.launch {
                sheetState.hide()
                onItemSelected(it)
            }
        }
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

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(countries) { country ->
            Row(modifier = Modifier
                .clickable { onItemSelected(country) }
                .padding(12.dp)
            ) {
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
            Divider(color = Color.LightGray, thickness = 0.5.dp)
        }
    }

}
