package cz.martinweiss.garager.ui.screens.fuelAddEdit

import cz.martinweiss.garager.model.RawFueling
import cz.martinweiss.garager.model.Vehicle

class AddEditFuelingData {
    var availableVehicles: List<Vehicle> = arrayListOf()
    var loading: Boolean = true
    var fueling: RawFueling = RawFueling(-1L, -1L)
    var vehicle: Vehicle = Vehicle("")
    var fuelingUnitPrice: String? = null
    var fuelingQuantity: String? = null
    var currency: String = ""

    var selectVehicleError: Int? = null
    var selectDateError: Int? = null
    var selectUnitPriceError: Int? = null
    var selectQuantityError: Int? = null
}