package cz.martinweiss.garager.ui.screens.fuelList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.datastore.DATASTORE_CURRENCY
import cz.martinweiss.garager.datastore.IDataStoreController
import cz.martinweiss.garager.datastore.VALUE_TYPE
import kotlinx.coroutines.launch

class FuelListViewModel(
    private val repository: IVehiclesRepository,
    private val dataStore: IDataStoreController
): BaseViewModel() {
    var currency: String = ""
    val fuelListUIState: MutableState<FuelListUIState> = mutableStateOf(FuelListUIState.Default)

    fun loadFuelings() {
        launch {
            val tempCurrency = dataStore.getValueByKey(DATASTORE_CURRENCY, VALUE_TYPE.STRING)
            if(tempCurrency != null) {
                currency = tempCurrency as String
            }
            /* TODO Splash Screen Default dataStore value */
        }

        launch {
            repository.getFuelingRecords().collect {
                fuelListUIState.value = FuelListUIState.Success(it)
            }
        }
    }
}