package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.model.Vehicle

sealed class AddEditVehicleUIState {
    object Default : AddEditVehicleUIState()
    class Success(val manufacturers: List<Manufacturer>) : AddEditVehicleUIState()
    class SuccessEdit(val vehicle: Vehicle, val manufacturers: List<Manufacturer>) : AddEditVehicleUIState()
    object VehicleSaved : AddEditVehicleUIState()
}
