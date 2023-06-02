package cz.martinweiss.garager.ui.screens.fuelAddEdit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cz.martinweiss.garager.R
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BackArrowScreen
import cz.martinweiss.garager.ui.elements.CustomDropdownField
import cz.martinweiss.garager.ui.elements.CustomTextField
import cz.martinweiss.garager.ui.elements.ReactiveField
import cz.martinweiss.garager.ui.screens.vehicleAddEdit.Section
import cz.martinweiss.garager.ui.theme.primaryMargin
import cz.martinweiss.garager.utils.DateUtils
import cz.martinweiss.garager.utils.FuelUtils
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
fun AddEditFuelingScreen(navigation: INavigationRouter, id: Long?, viewModel: AddEditFuelingViewModel = getViewModel()) {
    viewModel.fuelingId = id
    var data: AddEditFuelingData by remember {
        mutableStateOf(viewModel.data)
    }

    viewModel.addEditFuelingUIState.value.let {
        when(it) {
            AddEditFuelingUIState.Default -> { }
            AddEditFuelingUIState.FuelingChanged -> {
                data = viewModel.data
                viewModel.addEditFuelingUIState.value = AddEditFuelingUIState.Default
            }
            AddEditFuelingUIState.FuelingSaved -> {
                LaunchedEffect(it) {
                    navigation.returnBack()
                }
            }
            AddEditFuelingUIState.Loading -> {
                viewModel.initData()
            }
        }
    }

    BackArrowScreen(
        topBarTitle = if(viewModel.fuelingId != null)
            stringResource(id = R.string.title_add_edit_fueling_edit) else
            stringResource(id = R.string.title_add_edit_fueling_add),
        onBackClick = { navigation.returnBack() },
        actions = {
            IconButton(onClick = {
                viewModel.saveFueling()
            }) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(id = R.string.add_edit_fueling_save_btn)
                )
            }
        }
    ) {
        AddEditFuelingContent(
            actions = viewModel,
            data = data
        )
    }
}

@Composable
fun AddEditFuelingContent(
    actions: AddEditFuelingViewModel,
    data: AddEditFuelingData
) {
    var expandedVehicle by remember { mutableStateOf(false) }
    var expandedFuelType by remember { mutableStateOf(false) }
    var fuelOptions = FuelUtils.getFuelIDOptions(data.vehicle.fuelTypeID)
    var fuelUnit = FuelUtils.getFuelUnit(data.vehicle.fuelTypeID)

    Column(
        modifier = Modifier.padding(primaryMargin()),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Section(title = stringResource(id = R.string.add_edit_fueling_section_main)) {
            CustomDropdownField(
                value = data.vehicle.name,
                label = stringResource(id = R.string.add_edit_fueling_vehicle_field),
                expanded = expandedVehicle,
                onExpandedChange = { expandedVehicle = !expandedVehicle },
                onDismissRequest = { expandedVehicle = false },
                error = if(data.selectVehicleError != null) stringResource(id = data.selectVehicleError!!) else ""
            ) {
                data.availableVehicles.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.name) },
                        onClick = {
                            actions.onVehicleChange(it)
                            expandedVehicle = false
                        }
                    )
                }
            }

            val calendar = Calendar.getInstance()
            if(data.fueling.date != -1L) {
                calendar.timeInMillis = data.fueling.date
            }

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(LocalContext.current,
                { _, hour, minute ->
                    actions.onDateChange(DateUtils.getUnixTime(year, month, day, hour, minute))
                },
                hour,
                minute,
                true
            )

            val datePickerDialog = DatePickerDialog(
                LocalContext.current,
                { dialog: DatePicker, year: Int, month: Int, day: Int ->
                    actions.onDateChange(DateUtils.getUnixTime(year, month, day, hour, minute))
                    timePickerDialog.show()
                },
                year,
                month,
                day
            )

            ReactiveField(
                value = if(data.fueling.date != -1L) DateUtils.getDateString(data.fueling.date, includeTime = true) else null,
                label = stringResource(id = R.string.add_edit_fueling_date_field),
                leadingIcon = R.drawable.ic_event_24,
                onClick = { datePickerDialog.show() },
                onClearClick = { actions.onDateChange(-1L) },
                error = if(data.selectDateError != null) stringResource(id = data.selectDateError!!) else ""
            )

            CustomTextField(
                value = data.fuelingUnitPrice ?: "",
                label = stringResource(id = R.string.add_edit_fueling_price_unit_field),
                onValueChange = { actions.onPricePerUnitChange(it) },
                error = if(data.selectUnitPriceError != null) stringResource(id = data.selectUnitPriceError!!) else "",
                trailingIcon = {
                    Text(text = data.currency)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            CustomTextField(
                value = data.fuelingQuantity ?: "",
                label = stringResource(id = R.string.add_edit_fueling_quantity_field),
                onValueChange = { actions.onQuantityChange(it) },
                error = if(data.selectQuantityError != null) stringResource(id = data.selectQuantityError!!) else "",
                trailingIcon = {
                    Text(text = fuelUnit)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            fuelOptions?.let {
                if(it.isNotEmpty()) {
                    CustomDropdownField(
                        value = data.fueling.specification ?: "",
                        label = stringResource(id = R.string.add_edit_fueling_specification_field),
                        expanded = expandedFuelType,
                        onExpandedChange = { expandedFuelType = !expandedFuelType },
                        onDismissRequest = { expandedFuelType = false },
                    ) {
                        it.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it) },
                                onClick = {
                                    actions.onFuelSpecificationChange(it)
                                    expandedFuelType = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Section(title = stringResource(id = R.string.add_edit_fueling_section_details)) {
            CustomTextField(
                value = data.fueling.description ?: "",
                label = stringResource(id = R.string.add_edit_fueling_description_field),
                onValueChange = { actions.onDescriptionChange(it) },
                error = "",
                maxLines = 5
            )
        }
    }
}