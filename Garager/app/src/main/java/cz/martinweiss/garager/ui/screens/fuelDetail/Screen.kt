package cz.martinweiss.garager.ui.screens.fuelDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cz.martinweiss.garager.R
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BackArrowScreen
import cz.martinweiss.garager.utils.DateUtils
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
            DetailFuelingUIState.FuelingDeleted -> {
                LaunchedEffect(it) {
                    navigation.returnBack()
                }
            }
            DetailFuelingUIState.Loading -> {
                viewModel.initData()
            }
        }
    }

    BackArrowScreen(
        topBarTitle = stringResource(id = R.string.title_detail_fueling),
        onBackClick = { navigation.returnBack() },
        actionIcon = Icons.Default.Edit,
        onActionClick = { navigation.navigateToAddEditFuelingScreen(id) }
    ) {
        DetailFuelingContent(
            navigation = navigation,
            data = data,
            actions = viewModel
        )
    }
}

@Composable
fun DetailFuelingContent(
    navigation: INavigationRouter,
    data: DetailFuelingData,
    actions: DetailFuelingViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Column() {
            Text(text = stringResource(id = R.string.detail_fueling_date), fontWeight = FontWeight.Bold)
            Text(
                text = DateUtils.getDateString(data.fueling.date, includeTime = true)
            )
        }

        Column() {
            Text(text = stringResource(id = R.string.detail_fueling_vehicle), fontWeight = FontWeight.Bold)
            Text(text = data.vehicleName)
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
        Row() {
            Text(
                text = stringResource(id = R.string.detail_fueling_price_unit),
                fontWeight = FontWeight.Bold
            )
            Text(text = data.fueling.priceUnit.toString())
        }

        Row() {
            Text(
                text = stringResource(id = R.string.detail_fueling_quantity),
                fontWeight = FontWeight.Bold
            )
            Text(text = data.fueling.quantity.toString())
        }

        Row() {
            Text(
                text = stringResource(id = R.string.detail_fueling_total),
                fontWeight = FontWeight.Bold
            )
            Text(text = "${data.fueling.priceUnit * data.fueling.quantity}")
        }

    }

    Column() {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = {
                    actions.deleteFueling()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(text = stringResource(id = R.string.detail_fueling_delete_button))
            }
        }
    }
}