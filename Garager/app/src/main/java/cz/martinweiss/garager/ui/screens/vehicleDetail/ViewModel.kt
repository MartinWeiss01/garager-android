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
                try {
                    repository.getVehicleById(vehicleId).collect { vehicle ->
                        data.vehicle = vehicle.vehicle
                        data.manufacturer = vehicle.manufacturer
                        detailVehicleUIState.value = DetailVehicleUIState.Default
                    }
                } catch (e: Exception) {
                    detailVehicleUIState.value = DetailVehicleUIState.VehicleDeleted
                }
            }
        } else {
            detailVehicleUIState.value = DetailVehicleUIState.UnknownObject
        }
    }

    override fun deleteVehicle(context: Context) {
        /*
        TODO: crashing app without try-catch, try to replace it with local temp variable
         */
        launch {
            data.vehicle.greenCardFilename?.let {
                FileUtils.deleteInternalFile(context, it)
            }
            repository.deleteVehicle(data.vehicle)
            detailVehicleUIState.value = DetailVehicleUIState.VehicleDeleted
        }
    }
}