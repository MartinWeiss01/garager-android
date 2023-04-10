package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.model.Manufacturer
import kotlinx.coroutines.launch

class AddEditVehicleViewModel(private val repository: IVehiclesRepository) : BaseViewModel(), AddEditVehicleActions {
    var data: AddEditVehicleData = AddEditVehicleData()
    var vehicleId: Long? = null
    val addEditVehicleUIState: MutableState<AddEditVehicleUIState> = mutableStateOf(AddEditVehicleUIState.Loading)

    fun initData() {
        launch {
            data.manufacturers = repository.getManufacturers()

            if(vehicleId != null) {
                data.vehicle = repository.getVehicleById(vehicleId!!)
            }

            data.loading = false
            addEditVehicleUIState.value = AddEditVehicleUIState.VehicleChanged
        }
    }

    override fun saveVehicle() {
        if(isVehicleValid()) {
            launch {
                if(vehicleId == null) {
                    val insertedId = repository.insertVehicle(data.vehicle)

                    if(insertedId > 0) {
                        addEditVehicleUIState.value = AddEditVehicleUIState.VehicleSaved
                    } else {
                        Log.d("[ERROR::AddEditVehicleViewModel]", "saveVehicle condition, invalid insertion")
                    }
                } else {
                    repository.updateVehicle(data.vehicle)
                    addEditVehicleUIState.value = AddEditVehicleUIState.VehicleSaved
                }
            }
        } else {
            addEditVehicleUIState.value = AddEditVehicleUIState.VehicleChanged
        }
    }

    override fun onNameChange(name: String) {
        data.vehicle.name = name
        addEditVehicleUIState.value = AddEditVehicleUIState.VehicleChanged
    }

    override fun onLicensePlateChange(licensePlate: String) {
        data.vehicle.licensePlate = licensePlate
        addEditVehicleUIState.value = AddEditVehicleUIState.VehicleChanged
    }

    override fun onVINChange(vin: String) {
        data.vehicle.vin = vin
        addEditVehicleUIState.value = AddEditVehicleUIState.VehicleChanged
    }

    override fun onManufacturerChange(manufacturer: Manufacturer?) {
        data.vehicle.manufacturer = manufacturer?.id
        data.selectedManufacturer = manufacturer?.name ?: ""
        addEditVehicleUIState.value = AddEditVehicleUIState.VehicleChanged
    }

    override fun isVehicleValid(): Boolean {
        var invalidCounter = 0
        if(!isNameValid()) invalidCounter += 1
        if(!isVINValid()) invalidCounter += 1
        return invalidCounter == 0
    }

    override fun isNameValid(): Boolean {
        val res = data.vehicle.name.isNotEmpty()
        if (res) data.vehicleNameError = ""
        else data.vehicleNameError = "Toto pole je povinné" //R.string.add_edit_vehicle_name_required
        return res
    }

    override fun isVINValid(): Boolean {
        val res1 = data.vehicle.vin.isEmpty()
        val res2 = ((data.vehicle.vin.length in 5..13) || (data.vehicle.vin.length == 17))
        if (res1 || res2) data.vehicleVINError = ""
        else data.vehicleVINError = "Neplatný formát" //R.string.add_edit_vehicle_vin_format_error

        return (res1 || res2)
    }
}