package cz.martinweiss.garager.ui.screens.fuelAddEdit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
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
import cz.martinweiss.garager.ui.screens.vehicleAddEdit.AddEditVehicleContent
import cz.martinweiss.garager.ui.screens.vehicleAddEdit.Section
import cz.martinweiss.garager.utils.DateUtils
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
        onBackClick = { navigation.returnBack() }
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
    var expanded by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Section(title = stringResource(id = R.string.add_edit_fueling_section_main)) {
            CustomDropdownField(
                value = data.selectedVehicleName,
                label = stringResource(id = R.string.add_edit_fueling_vehicle_field),
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                onDismissRequest = { expanded = false },
                error = if(data.selectVehicleError != null) stringResource(id = data.selectVehicleError!!) else ""
            ) {
                data.availableVehicles.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.name) },
                        onClick = {
                            actions.onVehicleChange(it)
                            expanded = false
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
                value = if(data.fueling.priceUnit != -1F) data.fueling.priceUnit.toString() else "",
                label = stringResource(id = R.string.add_edit_fueling_price_unit_field),
                onValueChange = { actions.onPricePerUnitChange(it.toFloatOrNull()) },
                error = if(data.selectUnitPriceError != null) stringResource(id = data.selectUnitPriceError!!) else "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            CustomTextField(
                value = if(data.fueling.quantity != -1F) data.fueling.quantity.toString() else "",
                label = stringResource(id = R.string.add_edit_fueling_quantity_field),
                onValueChange = { actions.onQuantityChange(it.toFloatOrNull()) },
                error = if(data.selectQuantityError != null) stringResource(id = data.selectQuantityError!!) else "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
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

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    actions.saveFueling()
                },
            ) {
                Text(text = stringResource(id = R.string.add_edit_fueling_save_btn))
            }
        }
    }
}