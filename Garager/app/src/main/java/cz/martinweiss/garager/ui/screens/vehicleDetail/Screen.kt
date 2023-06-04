package cz.martinweiss.garager.ui.screens.vehicleDetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.martinweiss.garager.R
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BackArrowScreen
import cz.martinweiss.garager.ui.theme.primaryMargin
import cz.martinweiss.garager.utils.DateUtils
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
            DetailVehicleUIState.ReturnToPreviousScreen -> {
                LaunchedEffect(it) {
                    navigation.returnBack()
                }
            }
        }
    }

    val context = LocalContext.current
    BackArrowScreen(
        topBarTitle = stringResource(id = R.string.title_detail_vehicle),
        onBackClick = { navigation.returnBack() },
        actions = {
            IconButton(onClick = {
                navigation.navigateToAddEditVehicleScreen(id)
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            }

            IconButton(onClick = {
                viewModel.deleteVehicle(context = context)
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.detail_vehicle_delete_button)
                )
            }
        }
    ) {
        DetailVehicleScreenContent(
            navigation = navigation,
            data = data,
            actions = viewModel
        )
    }
}

@Composable
fun DetailVehicleScreenContent(
    navigation: INavigationRouter,
    data: DetailVehicleData,
    actions: DetailVehicleViewModel
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(primaryMargin()),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Column() {
            Text(text = "${data.vehicle.name}", fontSize = 35.sp, fontWeight = FontWeight.Bold)
            Text(
                text = data.vehicle.licensePlate ?: stringResource(id = R.string.detail_vehicle_placeholder_license_plate),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Column() {
                Text(text = stringResource(id = R.string.detail_vehicle_manufacturer), fontWeight = FontWeight.Bold)
                Text(
                    text = data.manufacturer?.name ?: stringResource(id = R.string.detail_vehicle_placeholder_blank),
                )
            }

            Column() {
                Text(text = stringResource(id = R.string.detail_vehicle_vin), fontWeight = FontWeight.Bold)
                Text(
                    text = data.vehicle.vin ?: stringResource(id = R.string.detail_vehicle_placeholder_blank)
                )
            }

            Column() {
                Text(text = stringResource(id = R.string.detail_vehicle_mot_date), fontWeight = FontWeight.Bold)

                val tempMOT = data.vehicle.motDate
                if(tempMOT != null) {
                    val remainingDays = DateUtils.getRemainingDays(tempMOT)
                    Text(text = "${DateUtils.getDateString(tempMOT)} ${LocalContext.current.resources.getQuantityString(R.plurals.detail_vehicle_mot_date_remaining, remainingDays, remainingDays)}")
                } else {
                    Text(text = stringResource(id = R.string.detail_vehicle_placeholder_blank))
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = {
                    data.vehicle.greenCardFilename?.let {
                        navigation.navigateToGreenCardVehicleScreen(it)
                    }
                },
                enabled = data.vehicle.greenCardFilename != null
            ) {
                Text(text = if(data.vehicle.greenCardFilename != null) stringResource(id = R.string.detail_vehicle_green_card_btn)
                    else stringResource(id = R.string.detail_vehicle_green_card_btn_unavailable)
                )
            }
        }
    }
}
