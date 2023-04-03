package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.martinweiss.garager.R
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BackArrowScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun AddEditVehicleScreen(navigation: INavigationRouter, id: Long?, viewModel: AddEditVehicleViewModel = getViewModel()) {
    viewModel.addEditVehicleUIState.value.let {
        when(it) {
            AddEditVehicleUIState.Default -> { }
            AddEditVehicleUIState.VehicleSaved -> {
                LaunchedEffect(it) {
                    navigation.returnBack()
                }
            }
        }
    }

    BackArrowScreen(
        topBarTitle = stringResource(id = R.string.title_add_edit_vehicle),
        onBackClick = { navigation.returnBack() }
    ) {
        AddEditVehicleContent(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditVehicleContent(actions: AddEditVehicleActions) {
    var name = remember { mutableStateOf("") }
    var licensePlate = remember { mutableStateOf("") }
    var vin = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            value = name.value,
            label = { Text(text = stringResource(id = R.string.add_edit_vehicle_name_field)) },
            onValueChange = { name.value = it },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = licensePlate.value,
            label = { Text(text = stringResource(id = R.string.add_edit_vehicle_license_plate_field)) },
            onValueChange = { licensePlate.value = it },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = vin.value,
            label = { Text(text = stringResource(id = R.string.add_edit_vehicle_vin_field)) },
            onValueChange = { vin.value = it },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { actions.saveVehicle(name = name.value, licensePlate = licensePlate.value, vin = vin.value) }) {
            Text(text = stringResource(id = R.string.add_edit_vehicle_save_btn))
        }
    }
}