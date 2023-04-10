package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.model.Vehicle

class AddEditVehicleData {
    var manufacturers: MutableList<Manufacturer> = arrayListOf()
    var selectedManufacturer: String = ""
    var vehicle: Vehicle = Vehicle("", "", "", null)
    var loading: Boolean = true

    var vehicleNameError: String = ""
    var vehicleVINError: String = ""
    var vehicleManufacturerError: String = ""
}