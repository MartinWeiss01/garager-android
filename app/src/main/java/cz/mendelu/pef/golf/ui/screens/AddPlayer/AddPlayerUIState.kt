package cz.mendelu.pef.golf.ui.screens.AddPlayer

sealed class AddPlayerUIState {
    object Default : AddPlayerUIState()
    object PlayerSaved : AddPlayerUIState()
}