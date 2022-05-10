package com.canopas.campose.countrypicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun countrySearchView(state: ModalBottomSheetState): String {

    var searchValue: String by rememberSaveable { mutableStateOf("") }
    var showClearIcon by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    showClearIcon = searchValue.isNotEmpty()

    if (!state.isVisible) {
        searchValue = ""
    }

    Row {
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .background(
                        Color.LightGray.copy(0.6f),
                        shape = RoundedCornerShape(10.dp)
                    ),
                value = searchValue,
                onValueChange = {
                    searchValue = it
                },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 14.sp
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Black.copy(0.3f)
                    )
                },
                trailingIcon = {
                    if (showClearIcon) {
                        IconButton(onClick = {
                            searchValue = ""
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Cancel,
                                tint = Color.Black.copy(0.3f),
                                contentDescription = "Clear icon"
                            )
                        }
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
            )
            if (searchValue.isEmpty()) {
                Text(
                    text = stringResource(R.string.search_text),
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 52.dp)
                )
            }
        }
    }
    return searchValue
}


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewSearchView() {
    countrySearchView(
        rememberModalBottomSheetState(ModalBottomSheetValue.Expanded)
    )
}