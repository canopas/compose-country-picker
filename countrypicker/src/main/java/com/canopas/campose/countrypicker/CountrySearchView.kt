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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CountrySearchView(
    searchValue: String, customFontFamily: FontFamily, onSearch: (searchValue: String) -> Unit
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
            }, textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp, fontFamily = customFontFamily
            ), placeholder = {
                Text(
                    text = stringResource(R.string.search_text),
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontFamily = customFontFamily
                )
            }, singleLine = true, leadingIcon = {
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
            }, colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ), keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
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
    CountrySearchView("search", customFontFamily = FontFamily.Default, {})
}