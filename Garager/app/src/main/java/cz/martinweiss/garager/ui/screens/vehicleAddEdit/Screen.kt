package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.martinweiss.garager.R
import cz.martinweiss.garager.model.Manufacturer
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BackArrowScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun AddEditVehicleScreen(navigation: INavigationRouter, id: Long?, viewModel: AddEditVehicleViewModel = getViewModel()) {
    val manufacturers = remember {
        mutableStateListOf<Manufacturer>()
    }

    viewModel.addEditVehicleUIState.value.let {
        when(it) {
            AddEditVehicleUIState.Default -> {
                manufacturers.add(Manufacturer(""))
                viewModel.loadManufacturers()
            }
            AddEditVehicleUIState.VehicleSaved -> {
                LaunchedEffect(it) {
                    navigation.returnBack()
                }
            }
            is AddEditVehicleUIState.Success -> {
                manufacturers.clear()
                manufacturers.addAll(it.manufacturers)
            }
        }
    }

    BackArrowScreen(
        topBarTitle = stringResource(id = R.string.title_add_edit_vehicle),
        onBackClick = { navigation.returnBack() }
    ) {
        AddEditVehicleContent(
            actions = viewModel,
            manufacturers = manufacturers
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditVehicleContent(
    actions: AddEditVehicleViewModel,
    manufacturers: MutableList<Manufacturer>
) {
    var expanded by remember { mutableStateOf(false) }

    var f_name = remember { mutableStateOf("") }
    var f_licensePlate = remember { mutableStateOf("") }
    var f_vin = remember { mutableStateOf("") }
    var f_manufacturer = remember { mutableStateOf(manufacturers.firstOrNull()) }

    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column {
            TextField(
                value = f_name.value,
                label = { Text(text = stringResource(id = R.string.add_edit_vehicle_name_field)) },
                onValueChange = {
                    f_name.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                isError = !actions.isNameValid(f_name.value)
            )
            Text(
                text = if(actions.isNameValid(f_name.value)) "" else stringResource(id = R.string.add_edit_vehicle_name_required),
                color = MaterialTheme.colorScheme.error
            )
        }

        TextField(
            value = f_licensePlate.value,
            label = { Text(text = stringResource(id = R.string.add_edit_vehicle_license_plate_field)) },
            onValueChange = { f_licensePlate.value = it },
            modifier = Modifier.fillMaxWidth()
        )

        Column {
            TextField(
                value = f_vin.value,
                label = { Text(text = stringResource(id = R.string.add_edit_vehicle_vin_field)) },
                onValueChange = { f_vin.value = it },
                modifier = Modifier.fillMaxWidth(),
                isError = !actions.isVINValid(f_vin.value)
            )
            Text(
                text = if(actions.isVINValid(f_vin.value)) "" else stringResource(id = R.string.add_edit_vehicle_vin_format_error),
                color = MaterialTheme.colorScheme.error
            )
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                readOnly = true,
                value = f_manufacturer.value?.name ?: "",
                onValueChange = {},
                label = { Text(text = stringResource(id = R.string.add_edit_vehicle_manufacturer_field)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                manufacturers.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.name) },
                        onClick = {
                            f_manufacturer.value = it
                            expanded = false
                        }
                    )
                }
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    actions.saveVehicle(
                        name = f_name.value,
                        licensePlate = f_licensePlate.value,
                        vin = f_vin.value,
                        manufacturerId = f_manufacturer.value?.id
                    )
                },
                enabled = (actions.isNameValid(f_name.value) && actions.isVINValid(f_vin.value))
            ) {
                Text(text = stringResource(id = R.string.add_edit_vehicle_save_btn))
            }
        }
    }
}