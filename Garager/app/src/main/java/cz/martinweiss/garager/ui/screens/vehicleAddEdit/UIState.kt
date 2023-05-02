package cz.martinweiss.garager.ui.screens.vehicleAddEdit

sealed class AddEditVehicleUIState {
    object Default : AddEditVehicleUIState()
    object Loading : AddEditVehicleUIState()
    object VehicleSaved : AddEditVehicleUIState()
    object VehicleChanged : AddEditVehicleUIState()
}
