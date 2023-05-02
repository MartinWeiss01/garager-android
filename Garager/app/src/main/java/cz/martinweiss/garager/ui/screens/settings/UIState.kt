package cz.martinweiss.garager.ui.screens.settings

sealed class SettingsUIState {
    object Default : SettingsUIState()
    object Updated : SettingsUIState()
    object Loading : SettingsUIState()
}
