package com.example.currencyconverter.domain.entity

import java.util.Locale

fun Currency.flagEmoji(): String = when (this) {
    Currency.USD -> "\uD83C\uDDFA\uD83C\uDDF8" // United States
    Currency.EUR -> "\uD83C\uDDEA\uD83C\uDDFA" // European Union
    Currency.GBP -> "\uD83C\uDDEC\uD83C\uDDE7" // United Kingdom
    Currency.RUB -> "\uD83C\uDDF7\uD83C\uDDFA" // Russia
    Currency.JPY -> "\uD83C\uDDEF\uD83C\uDDF5" // Japan
    Currency.CNY -> "\uD83C\uDDE8\uD83C\uDDF3" // China
    else -> "\uD83C\uDFC1" // flag icon placeholder
}

fun Currency.displayName(): String = try {
    java.util.Currency.getInstance(name).getDisplayName(Locale.getDefault())
} catch (e: Exception) {
    name
}
