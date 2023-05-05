package cz.martinweiss.garager.ui.screens.fuelDetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cz.martinweiss.garager.R
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BackArrowScreen
import cz.martinweiss.garager.utils.DateUtils
import cz.martinweiss.garager.utils.FuelUtils
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailFuelingScreen(navigation: INavigationRouter, id: Long, viewModel: DetailFuelingViewModel = getViewModel()) {
    viewModel.fuelingId = id
    var data: DetailFuelingData by remember {
        mutableStateOf(viewModel.data)
    }

    viewModel.detailFuelingUIState.value.let {
        when(it) {
            DetailFuelingUIState.Default -> { }
            DetailFuelingUIState.Loading -> {
                viewModel.initData()
            }
            DetailFuelingUIState.ReturnToPreviousScreen -> {
                LaunchedEffect(it) {
                    navigation.returnBack()
                }
            }
        }
    }

    BackArrowScreen(
        topBarTitle = stringResource(id = R.string.title_detail_fueling),
        onBackClick = { navigation.returnBack() },
        actions = {
            IconButton(onClick = {
                navigation.navigateToAddEditFuelingScreen(id)
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            }

            IconButton(onClick = {
                viewModel.deleteFueling()
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.detail_fueling_delete_button)
                )
            }
        }
    ) {
        DetailFuelingContent(
            data = data,
            actions = viewModel
        )
    }
}

@Composable
fun DetailFuelingContent(
    data: DetailFuelingData,
    actions: DetailFuelingViewModel
) {
    var fuelUnit = FuelUtils.getFuelUnit(data.vehicle.fuelTypeID)

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Column() {
                Text(text = stringResource(id = R.string.detail_fueling_date), fontWeight = FontWeight.Bold)
                Text(
                    text = DateUtils.getDateString(data.fueling.date, includeTime = true)
                )
            }

            Column() {
                Text(text = stringResource(id = R.string.detail_fueling_vehicle), fontWeight = FontWeight.Bold)
                Text(text = data.vehicle.name)
            }

            data.fueling.description?.let {
                Column() {
                    Text(
                        text = stringResource(id = R.string.detail_fueling_description),
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = it)
                }
            }
        }


        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.detail_fueling_price_unit),
                    fontWeight = FontWeight.Bold
                )
                Text(text = data.fueling.priceUnit.toString())
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.detail_fueling_quantity),
                    fontWeight = FontWeight.Bold
                )
                Text(text = "${data.fueling.quantity} $fuelUnit")
            }

            Divider(
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .width(1.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.detail_fueling_total),
                    fontWeight = FontWeight.Bold
                )
                Text(text = "${data.fueling.priceUnit * data.fueling.quantity}")
            }
        }
    }
}