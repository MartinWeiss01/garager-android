package cz.martinweiss.garager.ui.screens.fuelList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.martinweiss.garager.R
import cz.martinweiss.garager.model.Fueling
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BaseScreenLayout
import cz.martinweiss.garager.ui.elements.PlaceholderScreen
import cz.martinweiss.garager.ui.elements.isScrollingUp
import cz.martinweiss.garager.ui.screens.vehicleList.*
import cz.martinweiss.garager.utils.DateUtils
import cz.martinweiss.garager.utils.FuelUtils
import org.koin.androidx.compose.getViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelListScreen(navigation: INavigationRouter, viewModel: FuelListViewModel = getViewModel()) {
    val listState = rememberLazyListState()

    val fuelings = remember {
        mutableStateListOf<Fueling>()
    }

    viewModel.fuelListUIState.value.let {
        when(it) {
            FuelListUIState.Default -> {
                viewModel.loadFuelings()
            }
            is FuelListUIState.Success -> {
                fuelings.clear()
                fuelings.addAll(it.fuelRecords)
            }
        }
    }

    BaseScreenLayout(
        navController = navigation.getNavController(),
        textFAB = stringResource(id = R.string.btn_add_new_fueling),
        expandedFAB = listState.isScrollingUp(),
        onFABClick = { navigation.navigateToAddEditFuelingScreen(-1L) }
    ) {
        FuelListContent(
            paddingValues = it,
            navigation = navigation,
            fuelings = fuelings,
            listState = listState,
            currency = viewModel.currency
        )
    }
}

@Composable
fun FuelListContent(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    fuelings: MutableList<Fueling>,
    listState: LazyListState,
    currency: String
) {
    Surface(
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if(fuelings.size == 0) {
            PlaceholderScreen(
                icon = painterResource(id = R.drawable.icon_breaking_news_filled),
                title = stringResource(id = R.string.fuel_list_empty_title),
                description = stringResource(id = R.string.fuel_list_empty_description, stringResource(id = R.string.btn_add_new_fueling))
            )
        } else {
            Box(modifier = Modifier.padding(top = 40.dp, start = 35.dp, end = 35.dp)) {
                FuelingRecordList(fuelings = fuelings, listState = listState, navigation = navigation, currency = currency)
            }
        }
    }
}

@Composable
fun FuelingRecordList(
    fuelings: MutableList<Fueling>,
    listState: LazyListState,
    navigation: INavigationRouter,
    currency: String
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(text = stringResource(id = R.string.fuel_list_title), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        fuelings.forEach {
            item(key = it.fueling.id) {
                FuelingRecord(fueling = it, onClick = {
                    it.fueling.id?.let { fuelingId ->
                        navigation.navigateToDetailFuelingScreen(fuelingId)
                    }
                }, currency = currency)
            }
        }
    }
}

@Composable
fun FuelingRecord(
    fueling: Fueling,
    onClick: () -> Unit,
    currency: String
) {
    var fuelUnit = FuelUtils.getFuelUnit(fueling.vehicle.fuelTypeID)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Box(modifier = Modifier.padding(20.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Column() {
                    val dateString = DateUtils.getDateString(fueling.fueling.date)
                    Text(text = dateString, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = fueling.vehicle.name, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        /* TODO round number */
                        Text(text = "${fueling.fueling.priceUnit} ${currency}/${fuelUnit} × ${fueling.fueling.quantity} $fuelUnit", fontSize = 10.sp, fontWeight = FontWeight.Bold)
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
                        /* TODO round number */
                        Text(text = stringResource(id = R.string.fuel_list_record_total_price), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(text = "${fueling.fueling.priceUnit * fueling.fueling.quantity} $currency", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}