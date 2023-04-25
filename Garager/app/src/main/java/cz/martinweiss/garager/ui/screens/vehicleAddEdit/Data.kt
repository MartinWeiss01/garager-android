package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.model.Vehicle

class AddEditVehicleData {
    var manufacturers: List<Manufacturer> = arrayListOf()
    var selectedManufacturerName: String = ""
    var vehicle: Vehicle = Vehicle("", "", "", null)
    var loading: Boolean = true

    var vehicleNameError: Int? = null
    var vehicleVINError: Int? = null
    var vehicleManufacturerError: Int? = null
}