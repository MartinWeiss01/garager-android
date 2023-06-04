package cz.martinweiss.garager.extensions

fun Double.round(): String {
    return String.format("%.2f", this)
}