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

    var fName = remember { mutableStateOf("") }
    var fLicensePlate = remember { mutableStateOf("") }
    var fVin = remember { mutableStateOf("") }
    var fManufacturer = remember { mutableStateOf(manufacturers.firstOrNull()) }

    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column {
            TextField(
                value = fName.value,
                label = { Text(text = stringResource(id = R.string.add_edit_vehicle_name_field)) },
                onValueChange = {
                    fName.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                isError = !actions.isNameValid(fName.value)
            )
            Text(
                text = if(actions.isNameValid(fName.value)) "" else stringResource(id = R.string.add_edit_vehicle_name_required),
                color = MaterialTheme.colorScheme.error
            )
        }

        TextField(
            value = fLicensePlate.value,
            label = { Text(text = stringResource(id = R.string.add_edit_vehicle_license_plate_field)) },
            onValueChange = { fLicensePlate.value = it },
            modifier = Modifier.fillMaxWidth()
        )

        Column {
            TextField(
                value = fVin.value,
                label = { Text(text = stringResource(id = R.string.add_edit_vehicle_vin_field)) },
                onValueChange = { fVin.value = it },
                modifier = Modifier.fillMaxWidth(),
                isError = !actions.isVINValid(fVin.value)
            )
            Text(
                text = if(actions.isVINValid(fVin.value)) "" else stringResource(id = R.string.add_edit_vehicle_vin_format_error),
                color = MaterialTheme.colorScheme.error
            )
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                readOnly = true,
                value = fManufacturer.value?.name ?: "",
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
                            fManufacturer.value = it
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
                        name = fName.value,
                        licensePlate = fLicensePlate.value,
                        vin = fVin.value,
                        manufacturerId = fManufacturer.value?.id
                    )
                },
                enabled = (actions.isNameValid(fName.value) && actions.isVINValid(fVin.value))
            ) {
                Text(text = stringResource(id = R.string.add_edit_vehicle_save_btn))
            }
        }
    }
}