package cz.martinweiss.garager.ui.screens.fuelAddEdit

interface AddEditFuelingActions {
    fun saveFueling()
    fun onVehicleChange(vehicleId: Long)
    fun onDateChange(date: Long)
    fun onPricePerUnitChange(unitPrice: Float)
    fun onQuantityChange(quantity: Float)
    fun onDescriptionChange(description: String)

    fun isVehicleValid(): Boolean
    fun isDateValid(): Boolean
    fun isUnitPriceValid(): Boolean
    fun isQuantityValid(): Boolean
}