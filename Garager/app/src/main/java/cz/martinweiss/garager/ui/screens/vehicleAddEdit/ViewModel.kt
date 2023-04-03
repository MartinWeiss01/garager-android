package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.model.Vehicle
import cz.martinweiss.garager.ui.screens.vehicleList.VehicleListUIState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import cz.martinweiss.garager.R

class AddEditVehicleViewModel(private val repository: IVehiclesRepository) : BaseViewModel(), AddEditVehicleActions {
    val addEditVehicleUIState: MutableState<AddEditVehicleUIState> = mutableStateOf(AddEditVehicleUIState.Default)

    override fun loadManufacturers() {
        launch {
            repository.getManufacturers().collect() {
                addEditVehicleUIState.value = AddEditVehicleUIState.Success(it)
            }
        }
    }

    override fun saveVehicle(name: String, licensePlate: String, vin: String, manufacturerId: Long?) {
        launch {
            val id = repository.insertVehicle(
                Vehicle(
                    name = name,
                    vin = vin,
                    licensePlate = licensePlate,
                    manufacturer = manufacturerId
                )
            )

            if(id > 0) {
                addEditVehicleUIState.value = AddEditVehicleUIState.VehicleSaved
            } else {
                Log.d("[ERROR::AddEditVehicleViewModel]", "saveVehicle condition, invalid insertion")
            }
        }
    }

    override fun isNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    override fun isVINValid(vin: String): Boolean {
        return (
            vin.isEmpty() ||
            (vin.length in 5..13) ||
            vin.length == 17
        )
    }
}