package cz.martinweiss.garager.ui.screens.vehicleDetail

sealed class DetailVehicleUIState {
    object Default : DetailVehicleUIState()
    object Loading : DetailVehicleUIState()
    object VehicleDeleted : DetailVehicleUIState()
}