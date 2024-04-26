package com.canopas.campose.countrypicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.canopas.campose.countrypicker.model.Country
import kotlinx.coroutines.launch


/**
 * Composable for displaying a country picker as a dialog.
 *
 * @param shape The shape of the dialog.
 * @param backgroundColor The color of the dialog container.
 * @param dialogTitle The title composable for the dialog.
 * @param onItemSelected Callback when a country is selected.
 * @param searchFieldTextStyle The text style for the search field.
 * @param placeholderTextStyle The text style for the search field placeholder.
 * @param countriesTextStyle The text style for the countries list.
 * @param showFullScreenDialog Whether to show the dialog as a full screen dialog.
 * @param onDismissRequest Callback when the dialog is dismissed.
 */
@Composable
fun CountryPickerDialog(
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    dialogTitle: @Composable () -> Unit,
    onItemSelected: (country: Country) -> Unit,
    searchFieldTextStyle: TextStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
    placeholderTextStyle: TextStyle = MaterialTheme.typography.labelMedium.copy(
        color = Color.Gray,
        fontSize = 16.sp,
    ),
    countriesTextStyle: TextStyle = TextStyle(),
    showFullScreenDialog: Boolean = false,
    onDismissRequest: () -> Unit
) {
    var searchValue by rememberSaveable { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val scope = rememberCoroutineScope()
    val modifier = if (showFullScreenDialog) {
        Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    } else {
        Modifier
            .wrapContentHeight()
            .heightIn(max = (screenHeight * 0.85f))
            .clip(shape = shape)
            .background(color = backgroundColor, shape)
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = !showFullScreenDialog,
            dismissOnBackPress = true,
            dismissOnClickOutside = !showFullScreenDialog
        )
    ) {
        Column(
            modifier = modifier
        ) {
            dialogTitle()
            CountrySearchView(searchValue, searchFieldTextStyle, placeholderTextStyle) {
                searchValue = it
            }

            Countries(searchValue, countriesTextStyle) {
                scope.launch {
                    onDismissRequest()
                    onItemSelected(it)
                }
            }
        }
    }
}