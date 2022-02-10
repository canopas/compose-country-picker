# JetCountryPicker

Country code bottomsheet picker in Jetpack Compose

<img src="https://github.com/canopas/Country-picker-example/blob/main/gif/Peek%202022-01-27%2015-11.gif" height="540" />

## How to add in your project

Add it in your root build.gradle at the end of repositories:
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
  ```
  
Add the dependency
```gradle
 implementation 'com.github.canopas:JetCountrypicker:1.0.1'
```

## How to use ?
```kotlin
    Box {
        var expanded by remember { mutableStateOf(false) }
        var selectedCountry by remember { mutableStateOf<Country?>(null) }

        CountryPickerBottomSheet(title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "Select Country", textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }, expanded, onDismissRequest = {
            expanded = false
        }, onItemSelected = {
            selectedCountry = it
            expanded = false
        }) {
            CountryTextField(
                label = "Select country",
                modifier = Modifier
                    .padding(top = 50.dp)
                    .align(Alignment.TopCenter),
                expanded,
                selectedCountry
            ) {
                expanded = !expanded
            }

        }

    }
```
