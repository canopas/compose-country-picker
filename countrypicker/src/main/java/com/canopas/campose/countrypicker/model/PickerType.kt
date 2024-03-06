package com.canopas.campose.countrypicker.model

import com.canopas.campose.countrypicker.R

/**
 * Enum class for the type of picker to display.
 */
enum class PickerType(val value: Int) {
    DIALOG(R.string.picker_type_dialog),
    FULL_SCREEN_DIALOG(R.string.picker_type_full_screen),
    BOTTOM_SHEET(R.string.picker_type_bottom_sheet)
}