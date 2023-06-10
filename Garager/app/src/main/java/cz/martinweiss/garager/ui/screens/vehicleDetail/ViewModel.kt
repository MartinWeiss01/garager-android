package cz.martinweiss.garager.ui.screens.vehicleDetail

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.utils.FileUtils
import kotlinx.coroutines.launch

class DetailVehicleViewModel(private val repository: IVehiclesRepository): BaseViewModel(), DetailVehicleActions {
    var data: DetailVehicleData = DetailVehicleData()
    var vehicleId: Long = -1L
    var detailVehicleUIState: MutableState<DetailVehicleUIState> = mutableStateOf(DetailVehicleUIState.Loading)

    fun initData() {
        if(vehicleId != -1L) {
            launch {
                repository.getVehicleById(vehicleId).collect { vehicle ->
                    if(vehicle != null) {
                        data.vehicle = vehicle.vehicle
                        data.manufacturer = vehicle.manufacturer
                        data.loading = false
                        detailVehicleUIState.value = DetailVehicleUIState.Default
                    } else {
                        detailVehicleUIState.value = DetailVehicleUIState.ReturnToPreviousScreen
                    }
                }
            }
        } else {
            detailVehicleUIState.value = DetailVehicleUIState.ReturnToPreviousScreen
        }
    }

    override fun deleteVehicle(context: Context) {
        launch {
            data.vehicle.greenCardFilename?.let {
                FileUtils.deleteInternalFile(context, it)
            }
            detailVehicleUIState.value = DetailVehicleUIState.ReturnToPreviousScreen
            repository.deleteVehicle(data.vehicle)
        }
    }
}