package cz.martinweiss.garager.ui.screens.vehicleDetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import kotlinx.coroutines.launch

class DetailVehicleViewModel(private val repository: IVehiclesRepository): BaseViewModel() {
    var data: DetailVehicleData = DetailVehicleData()
    var vehicleId: Long = 1
    var detailVehicleUIState: MutableState<DetailVehicleUIState> = mutableStateOf(DetailVehicleUIState.Loading)

    fun initData() {
        launch {
            val vehicleWithManufacturer = repository.getVehicleWithManufacturerById(vehicleId)
            data.vehicle = vehicleWithManufacturer.vehicle
            data.manufacturer = vehicleWithManufacturer.manufacturer

            detailVehicleUIState.value = DetailVehicleUIState.Default
        }
    }
}