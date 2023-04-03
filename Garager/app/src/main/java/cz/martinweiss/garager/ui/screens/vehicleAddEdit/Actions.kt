package cz.martinweiss.garager.ui.screens.vehicleAddEdit

interface AddEditVehicleActions {
    fun loadManufacturers()
    fun saveVehicle(name: String, licensePlate: String, vin: String, manufacturerId: Long?)
    fun isNameValid(name: String): Boolean
    fun isVINValid(vin: String): Boolean
}