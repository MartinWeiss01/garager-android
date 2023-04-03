package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.model.Vehicle
import kotlinx.coroutines.launch

class AddEditVehicleViewModel(private val repository: IVehiclesRepository) : BaseViewModel(), AddEditVehicleActions {
    val addEditVehicleUIState: MutableState<AddEditVehicleUIState> = mutableStateOf(AddEditVehicleUIState.Default)

    override fun loadManufacturers() {
        launch {
            repository.getManufacturers().collect() {
                addEditVehicleUIState.value = AddEditVehicleUIState.Success(it)
            }
        }
    }

    override fun loadManufacturersWithVehicle(id: Long) {
        launch {
            var vehicle: Vehicle = repository.getVehicleById(id)

            repository.getManufacturers().collect() {
                addEditVehicleUIState.value = AddEditVehicleUIState.SuccessEdit(vehicle = vehicle, manufacturers = it)
            }
        }
    }

    override fun saveVehicle(id: Long?, name: String, licensePlate: String, vin: String, manufacturerId: Long?) {
        launch {
            if(id != null) {
                repository.updateVehicle(
                    vehicleId = id,
                    vehicleName = name,
                    vehicleVin = vin,
                    vehicleLicensePlate = licensePlate,
                    vehicleManufacturerId = manufacturerId
                )
                addEditVehicleUIState.value = AddEditVehicleUIState.VehicleSaved
            } else {
                val insertedId = repository.insertVehicle(
                    Vehicle(
                        name = name,
                        vin = vin,
                        licensePlate = licensePlate,
                        manufacturer = manufacturerId
                    )
                )

                if(insertedId > 0) {
                    addEditVehicleUIState.value = AddEditVehicleUIState.VehicleSaved
                } else {
                    Log.d("[ERROR::AddEditVehicleViewModel]", "saveVehicle condition, invalid insertion")
                }
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