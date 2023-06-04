package cz.martinweiss.garager.ui.screens.vehicleList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.datastore.DATASTORE_MOT_DAYS
import cz.martinweiss.garager.datastore.IDataStoreController
import kotlinx.coroutines.launch

class VehicleListViewModel(private val repository: IVehiclesRepository, private val dataStore: IDataStoreController): BaseViewModel() {
    var data: VehicleListData = VehicleListData()
    val vehicleListUIState: MutableState<VehicleListUIState> = mutableStateOf(VehicleListUIState.Default)

    fun loadVehicles() {
        launch {
            val motWarning = dataStore.getIntByKey(DATASTORE_MOT_DAYS)
            if(motWarning != null) {
                data.motDaysWarning = motWarning
            } else {
                dataStore.updateIntKey(DATASTORE_MOT_DAYS, data.motDaysWarning)
            }

            repository.getVehicles().collect {
                vehicleListUIState.value = VehicleListUIState.Success(it)
            }
        }
    }
}