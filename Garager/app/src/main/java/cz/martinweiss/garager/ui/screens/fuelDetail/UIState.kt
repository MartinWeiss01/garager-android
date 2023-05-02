package cz.martinweiss.garager.ui.screens.fuelDetail

sealed class DetailFuelingUIState {
    object Default : DetailFuelingUIState()
    object Loading : DetailFuelingUIState()
    object UnknownObject : DetailFuelingUIState()
    object FuelingDeleted : DetailFuelingUIState()
}
