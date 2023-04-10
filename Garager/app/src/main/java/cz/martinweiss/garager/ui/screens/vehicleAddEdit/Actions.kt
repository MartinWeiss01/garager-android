package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import cz.martinweiss.garager.model.Manufacturer

interface AddEditVehicleActions {
    fun saveVehicle()
    fun onNameChange(name: String)
    fun onLicensePlateChange(licensePlate: String)
    fun onVINChange(vin: String)
    fun onManufacturerChange(manufacturer: Manufacturer?)

    fun isVehicleValid(): Boolean
    fun isNameValid(): Boolean
    fun isVINValid(): Boolean
}