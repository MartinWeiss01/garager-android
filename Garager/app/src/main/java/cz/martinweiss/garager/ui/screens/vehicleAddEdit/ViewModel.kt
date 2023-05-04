package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.R
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.model.FuelType
import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.model.fuelTypes
import cz.martinweiss.garager.utils.FileUtils
import kotlinx.coroutines.launch

class AddEditVehicleViewModel(private val repository: IVehiclesRepository) : BaseViewModel(), AddEditVehicleActions {
    var data: AddEditVehicleData = AddEditVehicleData()
    var vehicleId: Long? = null
    val addEditVehicleUIState: MutableState<AddEditVehicleUIState> = mutableStateOf(AddEditVehicleUIState.Loading)

    fun initData() {
        launch {
            data.manufacturers = repository.getManufacturers()

            if(vehicleId != null) {
                val vehicleWithManufacturer = repository.getVehicleWithManufacturerById(vehicleId!!)
                data.vehicle = vehicleWithManufacturer.vehicle
                data.selectedManufacturerName = vehicleWithManufacturer.manufacturer?.name ?: ""
                val fuelType = fuelTypes.firstOrNull {
                    it.id == data.vehicle.fuelTypeID
                }
                fuelType?.let {
                    data.selectedFuelTypeResID = it.nameResourceID
                }
            }

            data.loading = false
            addEditVehicleUIState.value = AddEditVehicleUIState.VehicleChanged
        }
    }

    override fun saveVehicle(context: Context) {
        if(isVehicleValid()) {
            if(data.deleteGreenCardFile) {
                data.vehicle.greenCardFilename?.let {
                    if(FileUtils.deleteInternalFile(context, it)) data.vehicle.greenCardFilename = null
                }
            }

            data.selectedGreenCardURI?.let {
                var newGreenCardFilename: String? = FileUtils.copyExternalFile(context, it)
                data.vehicle.greenCardFilename = newGreenCardFilename
            }


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
        data.selectedManufacturerName = manufacturer?.name ?: ""
        addEditVehicleUIState.value = AddEditVehicleUIState.VehicleChanged
    }

    override fun onFuelTypeChange(fuelType: FuelType?) {
        data.selectedFuelTypeResID = fuelType?.nameResourceID
        data.vehicle.fuelTypeID = fuelType?.id
        addEditVehicleUIState.value = AddEditVehicleUIState.VehicleChanged
    }

    override fun onDateChange(date: Long?) {
        data.vehicle.motDate = date
        addEditVehicleUIState.value = AddEditVehicleUIState.VehicleChanged
    }

    override fun onGreenCardChange(uri: Uri?) {
        if(data.vehicle.greenCardFilename != null) data.deleteGreenCardFile = true
        data.selectedGreenCardURI = uri
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
        if (res) data.vehicleNameError = null
        else data.vehicleNameError = R.string.add_edit_vehicle_name_required
        return res
    }

    override fun isVINValid(): Boolean {
        val tempVin = data.vehicle.vin ?: ""
        val res1 = tempVin.isEmpty()
        val res2 = ((tempVin.length in 5..13) || (tempVin.length == 17))
        if (res1 || res2) data.vehicleVINError = null
        else data.vehicleVINError = R.string.add_edit_vehicle_vin_format_error

        return (res1 || res2)
    }
}