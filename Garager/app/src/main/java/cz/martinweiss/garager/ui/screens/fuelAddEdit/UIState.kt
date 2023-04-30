package cz.martinweiss.garager.ui.screens.fuelAddEdit

sealed class AddEditFuelingUIState {
    object Default : AddEditFuelingUIState()
    object Loading : AddEditFuelingUIState()
    object FuelingSaved : AddEditFuelingUIState()
    object FuelingChanged : AddEditFuelingUIState()
}
