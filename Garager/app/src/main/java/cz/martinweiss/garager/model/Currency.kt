package cz.martinweiss.garager.model

import cz.martinweiss.garager.R

data class Currency(
    val name: String,
    val resource: Int
)

val currencies = listOf(
    Currency(name = "€", resource = R.string.currency_name_eur),
    Currency(name = "Kč", resource = R.string.currency_name_czk),
    Currency(name = "$", resource = R.string.currency_name_usd),
)