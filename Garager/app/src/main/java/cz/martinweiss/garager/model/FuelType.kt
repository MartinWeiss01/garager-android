package cz.martinweiss.garager.model

import cz.martinweiss.garager.R

data class FuelType(
    val id: String,
    val unit: String,
    val nameResourceID: Int,
    val options: List<String>
)

val fuelTypes = listOf(
    FuelType(
        id = "gas",
        unit = "l",
        nameResourceID = R.string.fuel_type_name_gas,
        options = listOf("95", "95+", "98", "98+", "100", "E10")
    ),
    FuelType(
        id = "diesel",
        unit = "l",
        nameResourceID = R.string.fuel_type_name_diesel,
        options = listOf("Diesel", "Premium", "Biodiesel", "AdBlue")
    ),
    FuelType(
        id = "electric",
        unit = "kWh",
        nameResourceID = R.string.fuel_type_name_electric,
        options = listOf("240 V", "DC 500 Fast Charge")
    ),
    FuelType(
        id = "naturalgas",
        unit = "m³",
        nameResourceID = R.string.fuel_type_name_naturalgas,
        options = listOf("CNG", "LNG")
    ),
    FuelType(
        id = "hydro",
        unit = "kg",
        nameResourceID = R.string.fuel_type_name_hydro,
        options = listOf()),
    FuelType(
        id = "ethanol",
        unit = "l",
        nameResourceID = R.string.fuel_type_name_ethanol,
        options = listOf("E85", "E100")
    ),
    FuelType(
        id = "propane",
        unit = "l",
        nameResourceID = R.string.fuel_type_name_propane,
        options = listOf()
    ),
    FuelType(
        id = "lpg",
        unit = "l",
        nameResourceID = R.string.fuel_type_name_lpg,
        options = listOf("LPG")
    ),
    FuelType(
        id = "cng",
        unit = "kg",
        nameResourceID = R.string.fuel_type_name_cng,
        options = listOf("CNG", "CBG", "BIOGAS")
    ),
    FuelType(
        id = "flex",
        unit = "l",
        nameResourceID = R.string.fuel_type_name_flex,
        options = listOf("E10", "E15", "E85")
    ),
)