package cz.martinweiss.garager.ui.screens.fuelAddEdit

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import cz.martinweiss.garager.R
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BackArrowScreen
import cz.martinweiss.garager.ui.screens.vehicleAddEdit.AddEditVehicleContent
import org.koin.androidx.compose.getViewModel

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
    Text("TODO")
}