package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import android.net.Uri
import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.model.Vehicle

class AddEditVehicleData {
    var manufacturers: List<Manufacturer> = arrayListOf()
    var selectedManufacturerName: String = ""
    var selectedFuelTypeResID: Int? = null
    var vehicle: Vehicle = Vehicle("")
    var loading: Boolean = true
    var selectedGreenCardURI: Uri? = null
    var deleteGreenCardFile: Boolean = false

    var vehicleNameError: Int? = null
    var vehicleVINError: Int? = null
    var vehicleManufacturerError: Int? = null
}