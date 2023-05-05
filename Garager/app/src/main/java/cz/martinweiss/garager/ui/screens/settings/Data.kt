package cz.martinweiss.garager.ui.screens.settings

import cz.martinweiss.garager.datastore.DEFAULT_CURRENCY_VALUE
import cz.martinweiss.garager.datastore.DEFAULT_MOT_WARNING_DAYS_VALUE

class SettingsData {
    var motDaysWarning: Int = DEFAULT_MOT_WARNING_DAYS_VALUE
    var currency: String = DEFAULT_CURRENCY_VALUE
}