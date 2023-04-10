package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.model.Vehicle

sealed class AddEditVehicleUIState {
    object Default : AddEditVehicleUIState()
    object Loading : AddEditVehicleUIState()
    object VehicleSaved : AddEditVehicleUIState()
    object VehicleChanged : AddEditVehicleUIState()
}
