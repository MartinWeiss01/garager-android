package cz.martinweiss.garager.ui.screens.settings

import cz.martinweiss.garager.model.Currency

interface SettingsActions {
    fun updateMOTDaysWarning(days: Int)
    fun updateCurrency(currency: String)
}