package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import android.content.Context
import android.net.Uri
import cz.martinweiss.garager.model.FuelType
import cz.martinweiss.garager.model.Manufacturer

interface AddEditVehicleActions {
    fun saveVehicle(context: Context)
    fun onNameChange(name: String)
    fun onLicensePlateChange(licensePlate: String)
    fun onVINChange(vin: String)
    fun onManufacturerChange(manufacturer: Manufacturer?)
    fun onFuelTypeChange(fuelType: FuelType?)
    fun onDateChange(date: Long?)
    fun onGreenCardChange(uri: Uri?)
    fun isVehicleValid(): Boolean
    fun isNameValid(): Boolean
    fun isVINValid(): Boolean
}