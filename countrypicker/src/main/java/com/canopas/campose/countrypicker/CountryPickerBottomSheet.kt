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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canopas.campose.countrypicker.model.Country
import kotlinx.coroutines.launch

/**
 * Composable for displaying a bottom sheet country picker.
 *
 * @param sheetState The state of the bottom sheet.
 * @param shape The shape of the bottom sheet.
 * @param containerColor The color of the bottom sheet container.
 * @param contentColor The color of the bottom sheet content.
 * @param tonalElevation The elevation of the bottom sheet.
 * @param scrimColor The color of the bottom sheet scrim.
 * @param bottomSheetTitle The title composable for the bottom sheet.
 * @param onItemSelected Callback when a country is selected.
 * @param searchFieldTextStyle The text style for the search field.
 * @param placeholderTextStyle The text style for the search field placeholder.
 * @param countriesTextStyle The text style for the countries list.
 * @param onDismissRequest Callback when the bottom sheet is dismissed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryPickerBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
    shape: Shape = MaterialTheme.shapes.medium,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = BottomSheetDefaults.Elevation,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    bottomSheetTitle: @Composable () -> Unit,
    onItemSelected: (country: Country) -> Unit,
    searchFieldTextStyle: TextStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
    placeholderTextStyle: TextStyle = MaterialTheme.typography.labelMedium.copy(
        color = Color.Gray,
        fontSize = 16.sp,
    ),
    countriesTextStyle: TextStyle = TextStyle(),
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

        CountrySearchView(searchValue, searchFieldTextStyle, placeholderTextStyle) {
            searchValue = it
        }

        Countries(searchValue, countriesTextStyle) {
            scope.launch {
                sheetState.hide()
                onItemSelected(it)
            }
        }
    }
}

/**
 * Composable for displaying a list of countries.
 *
 * @param searchValue The search value for filtering countries.
 * @param textStyle The text style for the country list.
 * @param onItemSelected Callback when a country is selected.
 */
@Composable
internal fun Countries(
    searchValue: String,
    textStyle: TextStyle = TextStyle(),
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
                Text(
                    text = localeToEmoji(country.code),
                    style = textStyle
                )
                Text(
                    text = country.name,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(2f),
                    style = textStyle
                )
                Text(
                    text = country.dial_code,
                    modifier = Modifier
                        .padding(start = 8.dp),
                    style = textStyle
                )
            }
            Divider(color = Color.LightGray, thickness = 0.5.dp)
        }
    }
}
