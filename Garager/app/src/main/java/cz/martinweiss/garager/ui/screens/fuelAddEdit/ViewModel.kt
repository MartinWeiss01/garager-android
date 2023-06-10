package cz.martinweiss.garager.ui.screens.fuelAddEdit

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.R
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
import cz.martinweiss.garager.datastore.DATASTORE_CURRENCY
import cz.martinweiss.garager.datastore.DEFAULT_CURRENCY_VALUE
import cz.martinweiss.garager.datastore.IDataStoreController
import cz.martinweiss.garager.model.Vehicle
import kotlinx.coroutines.launch

class AddEditFuelingViewModel(
    private val repository: IVehiclesRepository,
    private val dataStore: IDataStoreController
) : BaseViewModel(), AddEditFuelingActions {
    var data: AddEditFuelingData = AddEditFuelingData()
    var fuelingId: Long? = null
    val addEditFuelingUIState: MutableState<AddEditFuelingUIState> = mutableStateOf(AddEditFuelingUIState.Loading)

    fun initData() {
        launch {
            data.availableVehicles = repository.getAvailableVehicles()

            fuelingId?.let {
                val fuelingRecord = repository.getFuelingRecordById(it)
                data.fueling = fuelingRecord.fueling
                data.vehicle = fuelingRecord.vehicle
                // to make Double input fields more user friendly, it is handled in its own temp variable which allows string (so anything user writes down)
                data.fuelingUnitPrice = fuelingRecord.fueling.priceUnit.toString()
                data.fuelingQuantity = fuelingRecord.fueling.quantity.toString()
            }

            data.loading = false
            addEditFuelingUIState.value = AddEditFuelingUIState.FuelingChanged
        }

        launch {
            data.currency = dataStore.getStringByKey(DATASTORE_CURRENCY) ?: DEFAULT_CURRENCY_VALUE
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
            data.vehicle = vehicle
        }
        addEditFuelingUIState.value = AddEditFuelingUIState.FuelingChanged
    }

    override fun onDateChange(date: Long) {
        data.fueling.date = date
        addEditFuelingUIState.value = AddEditFuelingUIState.FuelingChanged
    }

    override fun onPricePerUnitChange(unitPrice: String?) {
        data.fuelingUnitPrice = unitPrice
        addEditFuelingUIState.value = AddEditFuelingUIState.FuelingChanged
    }

    override fun onQuantityChange(quantity: String?) {
        data.fuelingQuantity = quantity
        addEditFuelingUIState.value = AddEditFuelingUIState.FuelingChanged
    }

    override fun onFuelSpecificationChange(specification: String?) {
        data.fueling.specification = specification
        addEditFuelingUIState.value = AddEditFuelingUIState.FuelingChanged
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
        val res1 = data.fuelingUnitPrice != null
        if(res1) {
            val unitDouble = data.fuelingUnitPrice!!.toDoubleOrNull()
            val res2 = unitDouble != null

            if (res2) {
                val res3 = unitDouble!! > 0 && unitDouble < 999999
                if(res3) {
                    data.fueling.priceUnit = unitDouble
                    data.selectUnitPriceError = null
                } else {
                    data.selectUnitPriceError = R.string.add_edit_fueling_field_invalid_range
                }
                return res3
            } else {
                data.selectUnitPriceError = R.string.add_edit_fueling_field_invalid_format
            }
            return res2
        } else data.selectUnitPriceError = R.string.add_edit_fueling_field_required
        return res1
    }

    override fun isQuantityValid(): Boolean {
        val res1 = data.fuelingQuantity != null
        if(res1) {
            val quantityDouble = data.fuelingQuantity!!.toDoubleOrNull()
            val res2 = quantityDouble != null

            if(res2) {
                val res3 = quantityDouble!! > 0 && quantityDouble < 999999
                if(res3) {
                    data.fueling.quantity = quantityDouble
                    data.selectQuantityError = null
                } else {
                    data.selectQuantityError = R.string.add_edit_fueling_field_invalid_range
                }
                return res3
            } else {
                data.selectQuantityError = R.string.add_edit_fueling_field_invalid_format
            }
            return res2
        } else data.selectQuantityError = R.string.add_edit_fueling_field_required
        return res1
    }
}