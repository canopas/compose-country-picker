package com.canopas.campose.countrypicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Composable for displaying a search field.
 *
 * @param searchValue The current search value.
 * @param searchFieldTextStyle The text style for the search field.
 * @param placeholderTextStyle The text style for the placeholder.
 * @param onSearch Callback when the search value changes.
 */
@Composable
internal fun CountrySearchView(
    searchValue: String,
    searchFieldTextStyle: TextStyle = TextStyle(),
    placeholderTextStyle: TextStyle = TextStyle(),
    onSearch: (searchValue: String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Row {
        Box(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        ) {
            TextField(modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(
                    Color.LightGray.copy(0.6f), shape = RoundedCornerShape(10.dp)
                ), value = searchValue, onValueChange = {
                onSearch(it)
            }, textStyle = searchFieldTextStyle, placeholder = {
                Text(
                    text = stringResource(R.string.search_text),
                    style = placeholderTextStyle
                )
            }, singleLine = true,
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Black.copy(0.3f)
                    )
                }, trailingIcon = {
                    if (searchValue.isNotEmpty()) {
                        IconButton(onClick = {
                            onSearch("")
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Cancel,
                                tint = Color.Black.copy(0.3f),
                                contentDescription = "Clear icon"
                            )
                        }
                    }
                }, colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ), keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ), keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearchView() {
    CountrySearchView("search", onSearch = {})
}