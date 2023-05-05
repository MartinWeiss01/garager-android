package cz.martinweiss.garager.model

data class Currency(
    val name: String
)

val currencies = listOf(
    Currency("Kč"),
    Currency("€"),
    Currency("$"),
)