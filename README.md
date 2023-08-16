# JetCountryPicker

Country code bottom sheet picker in Jetpack Compose with Search functionality.

<img src="https://github.com/canopas/JetCountrypicker/blob/main/gif/Peek%202022-04-11%2011-46.gif" />

## How to add in your project

Available on [Maven Central](https://repo1.maven.org/maven2/com/canopas/jetcountrypicker/jetcountrypicker/).
  
Add the dependency
```gradle
 implementation 'com.canopas.jetcountrypicker:jetcountrypicker:1.0.9'
```

## How to use ?

```kotlin
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf<Country?>(null) }
    
    Box {
        CountryTextField(
            label = stringResource(R.string.select_country_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 40.dp, end = 40.dp),
            selectedCountry = selectedCountry,
            defaultCountry = countryList(LocalContext.current).firstOrNull { it.code == "IN" },
            onShowCountryPicker = {
                openBottomSheet = true
            }, isPickerVisible = openBottomSheet
        )
    }

    if (openBottomSheet) {
        CountryPickerBottomSheet(
            bottomSheetTitle = {
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
            containerColor = Color.White,
            onItemSelected = {
                selectedCountry = it
                openBottomSheet = false
            }, onDismissRequest = {
                openBottomSheet = false
            }
        )
    }
```

# Demo
[Sample](https://github.com/canopas/JetCountrypicker/tree/main/app) app demonstrates how simple the usage of the library actually is.

# Bugs and Feedback
For bugs, questions and discussions please use the [Github Issues](https://github.com/canopas/JetCountrypicker/issues).

# Credits

JetCountryPicker is owned and maintained by the [Canopas team](https://canopas.com/). You can follow them on Twitter at [@canopassoftware](https://twitter.com/canopassoftware) for project updates and releases.

# Licence

```
Copyright 2022 Canopas Software LLP

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

