package cz.martinweiss.garager.ui.screens.vehicleList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import kotlinx.coroutines.launch

class VehicleListViewModel(private val repository: IVehiclesRepository): BaseViewModel() {
    val vehicleListUIState: MutableState<VehicleListUIState> = mutableStateOf(VehicleListUIState.Default)

    fun loadVehicles() {
        launch {
            repository.getVehicles().collect {
                vehicleListUIState.value = VehicleListUIState.Success(it)
            }
        }
    }
}