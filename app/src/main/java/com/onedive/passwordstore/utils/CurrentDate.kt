package com.onedive.passwordstore.utils

import java.util.Calendar
import java.util.Locale

/**
 * This function is used to get the time based on the current locale
 * @author Ridh
 */
fun getCurrentLocaleTime() : String{

    val calendar = Calendar.getInstance(Locale.getDefault()) // instance
    val date = calendar.get(Calendar.DATE) // return : Current Date
    val mount = calendar.get(Calendar.MONTH) + 1 // return : Current Mount
    val year = calendar.get(Calendar.YEAR) // return : Current years

    val hour = calendar.get(Calendar.HOUR_OF_DAY) // return : Current Hour
    val minute = calendar.get(Calendar.MINUTE) // return : Current minute

    return "$date/$mount/$year : $hour.$minute" // ex 26/12/2023 : 21.01
}