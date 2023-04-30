package cz.martinweiss.garager.ui.screens.fuelDetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import kotlinx.coroutines.launch

class DetailFuelingViewModel(private val repository: IVehiclesRepository): BaseViewModel(), DetailFuelingActions {
    var data: DetailFuelingData = DetailFuelingData()
    var fuelingId: Long = 1
    var detailFuelingUIState: MutableState<DetailFuelingUIState> = mutableStateOf(DetailFuelingUIState.Loading)

    fun initData() {
        try {

        launch {
            try {
                repository.getLiveFuelingRecordById(fuelingId).collect() { fueling ->
                    data.fueling = fueling.fueling
                    data.vehicleName = fueling.vehicle.name
                    detailFuelingUIState.value = DetailFuelingUIState.Default
                }
            } catch (e: Exception) {
                detailFuelingUIState.value = DetailFuelingUIState.FuelingDeleted
            }
        }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun deleteFueling() {
        /*
        TODO: crashing app without try-catch, try to replace it with local temp variable
         */
        launch {
            detailFuelingUIState.value = DetailFuelingUIState.FuelingDeleted
            repository.deleteFueling(data.fueling)
        }
    }
}