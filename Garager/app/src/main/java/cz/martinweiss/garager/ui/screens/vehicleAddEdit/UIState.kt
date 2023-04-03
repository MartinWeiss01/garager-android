package cz.martinweiss.garager.ui.screens.vehicleAddEdit

sealed class AddEditVehicleUIState {
    object Default : AddEditVehicleUIState()
    object VehicleSaved : AddEditVehicleUIState()
}
