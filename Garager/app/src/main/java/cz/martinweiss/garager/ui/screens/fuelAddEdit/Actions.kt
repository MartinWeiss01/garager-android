package cz.martinweiss.garager.ui.screens.fuelAddEdit

import cz.martinweiss.garager.model.Vehicle

interface AddEditFuelingActions {
    fun saveFueling()
    fun onVehicleChange(vehicle: Vehicle)

    fun onDateChange(date: Long)
    fun onPricePerUnitChange(unitPrice: Float?)
    fun onQuantityChange(quantity: Float?)
    fun onFuelSpecificationChange(specification: String?)
    fun onDescriptionChange(description: String)

    fun isVehicleValid(): Boolean
    fun isDateValid(): Boolean
    fun isUnitPriceValid(): Boolean
    fun isQuantityValid(): Boolean
}