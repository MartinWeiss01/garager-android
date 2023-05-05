package cz.martinweiss.garager.ui.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.datastore.DATASTORE_CURRENCY
import cz.martinweiss.garager.datastore.DATASTORE_MOT_DAYS
import cz.martinweiss.garager.datastore.IDataStoreController
import cz.martinweiss.garager.datastore.VALUE_TYPE
import cz.martinweiss.garager.model.Currency
import kotlinx.coroutines.launch

class SettingsViewModel(private val dataStore: IDataStoreController): BaseViewModel(), SettingsActions {
    var data: SettingsData = SettingsData()
    val settingsUIState: MutableState<SettingsUIState> = mutableStateOf(SettingsUIState.Loading)

    fun loadInitSettings() {
        launch {
            /* TODO Splash Screen Default dataStore value */
            /* TODO Simplify fetching */
            val motWarning = dataStore.getValueByKey(DATASTORE_MOT_DAYS, VALUE_TYPE.INT)
            val currency = dataStore.getValueByKey(DATASTORE_CURRENCY, VALUE_TYPE.STRING)
            if(motWarning != null) {
                data.motDaysWarning = motWarning as Int
            } else {
                dataStore.updateKey(DATASTORE_MOT_DAYS, data.motDaysWarning)
            }
            if(currency != null) {
                data.currency = currency as String
            } else {
                dataStore.updateKey(DATASTORE_CURRENCY, data.currency)
            }
            settingsUIState.value = SettingsUIState.Updated
        }
    }

    override fun updateMOTDaysWarning(days: Int) {
        launch {
            dataStore.updateKey(DATASTORE_MOT_DAYS, days)
            data.motDaysWarning = days
            settingsUIState.value = SettingsUIState.Updated
        }
    }

    override fun updateCurrency(currency: String) {
        launch {
            dataStore.updateKey(DATASTORE_CURRENCY, currency)
            data.currency = currency
            settingsUIState.value = SettingsUIState.Updated
        }
    }
}