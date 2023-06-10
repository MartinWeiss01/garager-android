package cz.martinweiss.garager.ui.screens.vehicleList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.datastore.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class VehicleListViewModel(private val repository: IVehiclesRepository, private val dataStore: IDataStoreController): BaseViewModel(), VehicleListActions {
    var data: VehicleListData = VehicleListData()
    val vehicleListUIState: MutableState<VehicleListUIState> = mutableStateOf(VehicleListUIState.Init)

    fun loadVehicles() {
        launch {
            data.motDaysWarning = dataStore.getIntByKey(DATASTORE_MOT_DAYS) ?: DEFAULT_MOT_WARNING_DAYS_VALUE
            data.currency = dataStore.getStringByKey(DATASTORE_CURRENCY) ?: DEFAULT_CURRENCY_VALUE

            val fuelings = repository.getFuelingRecords().first()
            val vehicles = repository.getVehicles().first()
            data.loading = false
            vehicleListUIState.value = VehicleListUIState.Success(vehicles, fuelings)
        }
    }

    override fun updateVehicleSnapIndex(index: Int) {
        data.scrollSnapIndex = index
        vehicleListUIState.value = VehicleListUIState.Changed
    }
}