package cz.martinweiss.garager.ui.screens.vehicleList

import cz.martinweiss.garager.model.Vehicle

sealed class VehicleListUIState {
    object Default : VehicleListUIState()
    class Success(val vehicles: List<Vehicle>) : VehicleListUIState()
}
