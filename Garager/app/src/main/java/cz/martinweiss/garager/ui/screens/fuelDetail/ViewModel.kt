package cz.martinweiss.garager.ui.screens.fuelDetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.datastore.DATASTORE_CURRENCY
import cz.martinweiss.garager.datastore.IDataStoreController
import kotlinx.coroutines.launch

class DetailFuelingViewModel(
    private val repository: IVehiclesRepository,
    private val dataStore: IDataStoreController
): BaseViewModel(), DetailFuelingActions {
    var data: DetailFuelingData = DetailFuelingData()
    var fuelingId: Long = -1L
    var detailFuelingUIState: MutableState<DetailFuelingUIState> = mutableStateOf(DetailFuelingUIState.Loading)

    fun initData() {
        launch {
            data.currency = dataStore.getStringByKey(DATASTORE_CURRENCY)!!
        }

        if(fuelingId != -1L) {
            launch {
                repository.getLiveFuelingRecordById(fuelingId).collect() { fueling ->
                    if(fueling != null) {
                        data.fueling = fueling.fueling
                        data.vehicle = fueling.vehicle
                        detailFuelingUIState.value = DetailFuelingUIState.Default
                    } else {
                        detailFuelingUIState.value = DetailFuelingUIState.ReturnToPreviousScreen
                    }
                }
            }
        } else {
            detailFuelingUIState.value = DetailFuelingUIState.ReturnToPreviousScreen
        }
    }

    override fun deleteFueling() {
        launch {
            detailFuelingUIState.value = DetailFuelingUIState.ReturnToPreviousScreen
            repository.deleteFueling(data.fueling)
        }
    }
}