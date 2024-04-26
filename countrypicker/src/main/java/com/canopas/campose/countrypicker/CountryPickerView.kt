package com.canopas.campose.countrypicker

import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.canopas.campose.countrypicker.model.Country
import com.canopas.campose.countrypicker.model.PickerType

/**
 * Composable for displaying a country picker.
 *
 * @param sheetState The state of the bottom sheet.
 * @param shape The shape of the bottom sheet or dialog.
 * @param containerColor The color of the bottom sheet or dialog container.
 * @param contentColor The color of the bottom sheet items.
 *  **[For bottom sheet only].**
 * @param tonalElevation The elevation of the bottom sheet
 * **[For bottom sheet only].**
 * @param scrimColor Color of the scrim that obscures content when the bottom sheet is open.
 * **[For bottom sheet only].**
 * @param pickerTitle The title composable for the bottom sheet or dialog.
 * @param onItemSelected Callback when a country is selected.
 * @param searchFieldTextStyle The text style for the search field.
 * @param placeholderTextStyle The text style for the search field placeholder.
 * @param countriesTextStyle The text style for the countries list.
 * @param pickerType The type of picker to display (bottom sheet, full-screen dialog, or dialog).
 * Also see [PickerType].
 * @param onDismissRequest Callback when the bottom sheet or dialog is dismissed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryPickerView(
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
    shape: Shape =  MaterialTheme.shapes.medium,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = BottomSheetDefaults.Elevation,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    pickerTitle: @Composable () -> Unit,
    onItemSelected: (country: Country) -> Unit,
    searchFieldTextStyle: TextStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
    placeholderTextStyle: TextStyle = MaterialTheme.typography.labelMedium.copy(
        color = Color.Gray,
        fontSize = 16.sp,
    ),
    countriesTextStyle: TextStyle = TextStyle(),
    pickerType: PickerType = PickerType.BOTTOM_SHEET,
    onDismissRequest: () -> Unit
) {
    when(pickerType) {
        PickerType.BOTTOM_SHEET -> {
            CountryPickerBottomSheet(
                sheetState = sheetState,
                shape = shape,
                containerColor = containerColor,
                contentColor = contentColor,
                tonalElevation = tonalElevation,
                scrimColor = scrimColor,
                bottomSheetTitle = pickerTitle,
                onItemSelected = onItemSelected,
                searchFieldTextStyle = searchFieldTextStyle,
                placeholderTextStyle = placeholderTextStyle,
                countriesTextStyle = countriesTextStyle,
                onDismissRequest = onDismissRequest
            )
        }
        PickerType.FULL_SCREEN_DIALOG -> {
            CountryPickerDialog(
                shape = shape,
                backgroundColor = containerColor,
                onItemSelected = onItemSelected,
                dialogTitle = pickerTitle,
                searchFieldTextStyle = searchFieldTextStyle,
                placeholderTextStyle = placeholderTextStyle,
                countriesTextStyle = countriesTextStyle,
                showFullScreenDialog = true,
                onDismissRequest = onDismissRequest
            )
        }
        PickerType.DIALOG -> {
            CountryPickerDialog(
                shape = shape,
                backgroundColor = containerColor,
                onItemSelected = onItemSelected,
                dialogTitle = pickerTitle,
                searchFieldTextStyle = searchFieldTextStyle,
                placeholderTextStyle = placeholderTextStyle,
                countriesTextStyle = countriesTextStyle,
                onDismissRequest = onDismissRequest
            )
        }
    }
}