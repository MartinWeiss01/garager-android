package cz.martinweiss.garager.ui.screens.fuelDetail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import kotlinx.coroutines.launch

class DetailFuelingViewModel(private val repository: IVehiclesRepository): BaseViewModel(), DetailFuelingActions {
    var data: DetailFuelingData = DetailFuelingData()
    var fuelingId: Long = -1L
    var detailFuelingUIState: MutableState<DetailFuelingUIState> = mutableStateOf(DetailFuelingUIState.Loading)

    fun initData() {
        if(fuelingId != -1L) {
            launch {
                repository.getLiveFuelingRecordById(fuelingId).collect() { fueling ->
                    if(fueling != null) {
                        data.fueling = fueling.fueling
                        data.vehicleName = fueling.vehicle.name
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