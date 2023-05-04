package cz.martinweiss.garager.utils

import cz.martinweiss.garager.model.fuelTypes

class FuelUtils {
    companion object {
        fun getNameResourceID(id: String?): Int? {
            val fuelType = fuelTypes.firstOrNull {
                it.id == id
            }
            return fuelType?.nameResourceID
        }
    }
}
