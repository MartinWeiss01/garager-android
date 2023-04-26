package cz.martinweiss.garager.ui.screens.vehicleDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import cz.martinweiss.garager.R
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BackArrowScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailVehicleScreen(navigation: INavigationRouter, id: Long, viewModel: DetailVehicleViewModel = getViewModel()) {
    viewModel.vehicleId = id
    var data: DetailVehicleData by remember {
        mutableStateOf(viewModel.data)
    }

    viewModel.detailVehicleUIState.value.let {
        when(it) {
            DetailVehicleUIState.Default -> { }
            DetailVehicleUIState.Loading -> {
                viewModel.initData()
            }
        }
    }

    BackArrowScreen(
        topBarTitle = stringResource(id = R.string.title_detail_vehicle),
        onBackClick = { navigation.returnBack() },
        actionIcon = Icons.Default.Edit,
        onActionClick = { navigation.navigateToAddEditVehicleScreen(id) }
    ) {
        DetailVehicleScreenContent(
            navigation = navigation,
            data = data
        )
    }
}

@Composable
fun DetailVehicleScreenContent(
    navigation: INavigationRouter,
    data: DetailVehicleData
) {
    Column() {
        Text(text = "${data.vehicle.name} (${data.vehicle.id})")
        Text(text = "${data.vehicle.licensePlate}")
        Text(text = "${data.vehicle.vin}")
        Text(text = "${data.vehicle.motDate}")
        Text(text = "${data.vehicle.greenCardFilename}")
        Text(text = "${data.manufacturer?.name} (${data.manufacturer?.id} === ${data.vehicle.manufacturer})")
    }
}
