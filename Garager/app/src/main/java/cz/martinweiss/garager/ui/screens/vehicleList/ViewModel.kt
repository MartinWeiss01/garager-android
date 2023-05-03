package cz.martinweiss.garager.ui.screens.vehicleList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.datastore.DATASTORE_MOT_DAYS
import cz.martinweiss.garager.datastore.IDataStoreController
import cz.martinweiss.garager.datastore.VALUE_TYPE
import kotlinx.coroutines.launch

class VehicleListViewModel(private val repository: IVehiclesRepository, private val dataStore: IDataStoreController): BaseViewModel() {
    var data: VehicleListData = VehicleListData()
    val vehicleListUIState: MutableState<VehicleListUIState> = mutableStateOf(VehicleListUIState.Default)

    fun loadVehicles() {
        launch {
            val motWarning = dataStore.getValueByKey(DATASTORE_MOT_DAYS, VALUE_TYPE.INT)
            if(motWarning != null) {
                data.motDaysWarning = motWarning as Int
            } else {
                dataStore.updateKey(DATASTORE_MOT_DAYS, data.motDaysWarning)
            }

            repository.getVehicles().collect {
                vehicleListUIState.value = VehicleListUIState.Success(it)
            }
        }
    }
}