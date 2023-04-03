package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.model.Vehicle
import kotlinx.coroutines.launch

class AddEditVehicleViewModel(private val repository: IVehiclesRepository) : BaseViewModel(), AddEditVehicleActions {
    val addEditVehicleUIState: MutableState<AddEditVehicleUIState> = mutableStateOf(AddEditVehicleUIState.Default)

    override fun saveVehicle(name: String, licensePlate: String, vin: String) {
        launch {
            val id = repository.insertVehicle(
                Vehicle(
                    name = name,
                    vin = vin,
                    licensePlate = licensePlate
                )
            )

            if(id > 0) {
                addEditVehicleUIState.value = AddEditVehicleUIState.VehicleSaved
            } else {
                Log.d("[ERROR::AddEditVehicleViewModel]", "saveVehicle condition, invalid insertion")
            }
        }
    }

}