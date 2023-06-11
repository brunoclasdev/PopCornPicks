package br.com.bclas.popcornpicks.presentation.util

import java.text.SimpleDateFormat
import java.util.Locale

object FormatDate {
    fun formateDate(date: String): String {
        val inputeDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDate = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("pt", "BR"))
        val dateParsed = inputeDate.parse(date)
        dateParsed?.let {
            return outputDate.format(dateParsed)
        }.apply {
            return "Date invalid"
        }
    }
}