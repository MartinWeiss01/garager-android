package cz.martinweiss.garager.ui.screens.fuelList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import kotlinx.coroutines.launch

class FuelListViewModel(private val repository: IVehiclesRepository): BaseViewModel() {
    val fuelListUIState: MutableState<FuelListUIState> = mutableStateOf(FuelListUIState.Default)

    fun loadFuelings() {
        launch {
            repository.getFuelingRecords().collect {
                fuelListUIState.value = FuelListUIState.Success(it)
            }
        }
    }
}