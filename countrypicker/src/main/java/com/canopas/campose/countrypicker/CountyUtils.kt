package com.canopas.campose.countrypicker

import android.content.Context
import com.canopas.campose.countrypicker.model.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun countryList(context: Context): MutableList<Country> {
    val jsonFileString = getJsonDataFromAsset(context, "Countries.json")
    val type = object : TypeToken<List<Country>>() {}.type
    return Gson().fromJson(jsonFileString, type)
}

fun localeToEmoji(countryCode: String): String {
    val firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6
    val secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6
    return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
}

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun List<Country>.searchCountryList(countryName: String): MutableList<Country> {
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