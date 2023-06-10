package cz.martinweiss.garager.ui.screens.vehicleList

import cz.martinweiss.garager.model.Fueling
import cz.martinweiss.garager.model.Vehicle

sealed class VehicleListUIState {
    object Init : VehicleListUIState()
    class Success(val vehicles: List<Vehicle>, val fuelings: List<Fueling>) : VehicleListUIState()
    object Changed : VehicleListUIState()
}
