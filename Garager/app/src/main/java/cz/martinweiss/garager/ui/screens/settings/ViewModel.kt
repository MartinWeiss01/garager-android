package cz.martinweiss.garager.ui.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.datastore.DATASTORE_CURRENCY
import cz.martinweiss.garager.datastore.DATASTORE_MOT_DAYS
import cz.martinweiss.garager.datastore.IDataStoreController
import kotlinx.coroutines.launch

class SettingsViewModel(private val dataStore: IDataStoreController): BaseViewModel(), SettingsActions {
    var data: SettingsData = SettingsData()
    val settingsUIState: MutableState<SettingsUIState> = mutableStateOf(SettingsUIState.Loading)

    fun loadInitSettings() {
        launch {
            data.motDaysWarning = dataStore.getIntByKey(DATASTORE_MOT_DAYS)!!
            data.currency = dataStore.getStringByKey(DATASTORE_CURRENCY)!!
            settingsUIState.value = SettingsUIState.Updated
        }
    }

    override fun updateMOTDaysWarning(days: Int) {
        launch {
            dataStore.updateIntKey(DATASTORE_MOT_DAYS, days)
            data.motDaysWarning = days
            settingsUIState.value = SettingsUIState.Updated
        }
    }

    override fun updateCurrency(currency: String) {
        launch {
            dataStore.updateStringKey(DATASTORE_CURRENCY, currency)
            data.currency = currency
            settingsUIState.value = SettingsUIState.Updated
        }
    }
}