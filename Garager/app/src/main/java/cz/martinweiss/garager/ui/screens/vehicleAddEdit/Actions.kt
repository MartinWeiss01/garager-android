package cz.martinweiss.garager.ui.screens.vehicleAddEdit

interface AddEditVehicleActions {
    fun saveVehicle(name: String, licensePlate: String, vin: String)
}