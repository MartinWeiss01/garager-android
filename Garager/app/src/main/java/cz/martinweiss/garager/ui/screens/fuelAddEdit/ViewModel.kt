package cz.martinweiss.garager.ui.screens.fuelAddEdit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.martinweiss.garager.architecture.BaseViewModel
import cz.martinweiss.garager.database.IVehiclesRepository
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
        TODO("Not yet implemented")
    }

    override fun onVehicleChange(vehicleId: Long) {
        TODO("Not yet implemented")
    }

    override fun onDateChange(date: Long) {
        TODO("Not yet implemented")
    }

    override fun onPricePerUnitChange(unitPrice: Float) {
        TODO("Not yet implemented")
    }

    override fun onQuantityChange(quantity: Float) {
        TODO("Not yet implemented")
    }

    override fun onDescriptionChange(description: String) {
        TODO("Not yet implemented")
    }

    override fun isVehicleValid(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isDateValid(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isUnitPriceValid(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isQuantityValid(): Boolean {
        TODO("Not yet implemented")
    }
}