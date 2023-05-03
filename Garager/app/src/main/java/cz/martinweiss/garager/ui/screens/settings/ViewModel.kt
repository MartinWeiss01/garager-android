package cz.martinweiss.garager.ui.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.datastore.DATASTORE_MOT_DAYS
import cz.martinweiss.garager.datastore.IDataStoreController
import cz.martinweiss.garager.datastore.VALUE_TYPE
import kotlinx.coroutines.launch

class SettingsViewModel(private val dataStore: IDataStoreController): BaseViewModel(), SettingsActions {
    var data: SettingsData = SettingsData()
    val settingsUIState: MutableState<SettingsUIState> = mutableStateOf(SettingsUIState.Loading)

    fun loadInitSettings() {
        launch {
            val motWarning = dataStore.getValueByKey(DATASTORE_MOT_DAYS, VALUE_TYPE.INT)
            if(motWarning != null) {
                data.motDaysWarning = motWarning as Int
            } else {
                dataStore.updateKey(DATASTORE_MOT_DAYS, data.motDaysWarning)
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
}