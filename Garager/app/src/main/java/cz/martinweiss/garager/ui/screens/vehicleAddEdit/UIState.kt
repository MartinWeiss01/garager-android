package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import cz.martinweiss.garager.model.Manufacturer

sealed class AddEditVehicleUIState {
    object Default : AddEditVehicleUIState()
    class Success(val manufacturers: List<Manufacturer>) : AddEditVehicleUIState()
    object VehicleSaved : AddEditVehicleUIState()
}
