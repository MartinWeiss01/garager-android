package cz.martinweiss.garager.ui.screens.settings

interface SettingsActions {
    fun updateMOTDaysWarning(days: Int)
    fun updateCurrency(currency: String)
}