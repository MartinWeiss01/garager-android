package cz.martinweiss.garager.ui.screens.fuelList

import cz.martinweiss.garager.model.Fueling

sealed class FuelListUIState {
    object Default : FuelListUIState()
    class Success(val fuelRecords: List<Fueling>) : FuelListUIState()
}
