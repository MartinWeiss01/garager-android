package cz.martinweiss.garager.ui.screens.fuelAddEdit

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.R
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.model.Vehicle
import cz.martinweiss.garager.ui.screens.vehicleAddEdit.AddEditVehicleUIState
import kotlinx.coroutines.launch

class AddEditFuelingViewModel(private val repository: IVehiclesRepository) : BaseViewModel(), AddEditFuelingActions {
    var data: AddEditFuelingData = AddEditFuelingData()
    var fuelingId: Long? = null
    val addEditFuelingUIState: MutableState<AddEditFuelingUIState> = mutableStateOf(AddEditFuelingUIState.Loading)

    fun initData() {
        launch {
            data.availableVehicles = repository.getAvailableVehicles()

            fuelingId?.let {
                val fuelingRecord = repository.getFuelingRecordById(it)
                data.fueling = fuelingRecord.fueling
                data.selectedVehicleName = fuelingRecord.vehicle.name
            }

            data.loading = false
            addEditFuelingUIState.value = AddEditFuelingUIState.FuelingChanged
        }
    }

    override fun saveFueling() {
        if(isFuelingValid()) {
            launch {
                if (fuelingId == null) {
                    val insertedId = repository.insertFueling(data.fueling)

                    if(insertedId > 0) {
                        addEditFuelingUIState.value = AddEditFuelingUIState.FuelingSaved
                    } else {
                        Log.d("[ERROR::AddEditVehicleViewModel]", "saveVehicle condition, invalid insertion")
                    }
                } else {
                    repository.updateFueling(data.fueling)
                    addEditFuelingUIState.value = AddEditFuelingUIState.FuelingSaved
                }
            }
        } else {
            addEditFuelingUIState.value = AddEditFuelingUIState.FuelingChanged
        }
    }

    override fun onVehicleChange(vehicle: Vehicle) {
        vehicle.id?.let {
            data.fueling.vehicleId = it
            data.selectedVehicleName = vehicle.name
        }
        addEditFuelingUIState.value = AddEditFuelingUIState.FuelingChanged
    }

    override fun onDateChange(date: Long) {
        data.fueling.date = date
        addEditFuelingUIState.value = AddEditFuelingUIState.FuelingChanged
    }

    override fun onPricePerUnitChange(unitPrice: Float?) {
        unitPrice?.let {
            data.fueling.priceUnit = unitPrice
            addEditFuelingUIState.value = AddEditFuelingUIState.FuelingChanged
        }
    }

    override fun onQuantityChange(quantity: Float?) {
        quantity?.let {
            data.fueling.quantity = quantity
            addEditFuelingUIState.value = AddEditFuelingUIState.FuelingChanged
        }
    }

    override fun onDescriptionChange(description: String) {
        data.fueling.description = description
        addEditFuelingUIState.value = AddEditFuelingUIState.FuelingChanged
    }

    fun isFuelingValid(): Boolean {
        var invalidCounter = 0
        if(!isVehicleValid()) invalidCounter += 1
        if(!isDateValid()) invalidCounter += 1
        if(!isUnitPriceValid()) invalidCounter += 1
        if(!isQuantityValid()) invalidCounter += 1
        return invalidCounter == 0
    }

    override fun isVehicleValid(): Boolean {
        val res = data.fueling.vehicleId != -1L
        if (res) data.selectVehicleError = null
        else data.selectVehicleError = R.string.add_edit_fueling_field_required
        return res
    }

    override fun isDateValid(): Boolean {
        val res = data.fueling.date != -1L
        if (res) data.selectDateError = null
        else data.selectDateError = R.string.add_edit_fueling_field_required
        return res
    }

    override fun isUnitPriceValid(): Boolean {
        val res = data.fueling.priceUnit != -1F
        if (res) data.selectUnitPriceError = null
        else data.selectUnitPriceError = R.string.add_edit_fueling_field_required
        return res
    }

    override fun isQuantityValid(): Boolean {
        val res = data.fueling.quantity != -1F
        if (res) data.selectQuantityError = null
        else data.selectQuantityError = R.string.add_edit_fueling_field_required
        return res
    }
}