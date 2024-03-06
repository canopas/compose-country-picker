package com.canopas.campose.countrypicker

import android.content.Context
import com.canopas.campose.countrypicker.model.Country
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException

/**
 * Retrieves a list of countries from a JSON file stored in the assets directory.
 *
 * @param context The context used to access the assets directory.
 * @return A mutable list of countries parsed from the JSON file, or an empty list if parsing fails.
 */
fun countryList(context: Context): MutableList<Country> {
    val moshi =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    val personListType = Types.newParameterizedType(List::class.java, Country::class.java)
    val jsonAdapter: JsonAdapter<MutableList<Country>> = moshi.adapter(personListType)

    val jsonFileString = getJsonDataFromAsset(context, "Countries.json")
    return jsonFileString?.let { jsonAdapter.fromJson(it) } ?: mutableListOf()
}

internal fun localeToEmoji(countryCode: String): String {
    val firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6
    val secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6
    return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
}

internal fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

internal fun List<Country>.searchCountryList(countryName: String): MutableList<Country> {
    val countryList = mutableListOf<Country>()
    this.forEach {
        if (it.name.lowercase().contains(countryName.lowercase()) ||
            it.dial_code.contains(countryName.lowercase())
        ) {
            countryList.add(it)
        }
    }
    return countryList
}