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
import androidx.compose.ui.unit.dp
import cz.martinweiss.garager.R
import cz.martinweiss.garager.extensions.isScrollingUp
import cz.martinweiss.garager.extensions.round
import cz.martinweiss.garager.model.Fueling
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BaseScreenLayout
import cz.martinweiss.garager.ui.elements.LoadingSpinner
import cz.martinweiss.garager.ui.elements.PlaceholderScreen
import cz.martinweiss.garager.ui.screens.vehicleList.*
import cz.martinweiss.garager.ui.theme.*
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
        if(viewModel.loading) {
            LoadingSpinner()
        } else {
            FuelListContent(
                paddingValues = it,
                navigation = navigation,
                fuelings = fuelings,
                listState = listState,
                currency = viewModel.currency
            )
        }
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
            Box(modifier = Modifier.padding(top = 0.dp, start = primaryMargin(), end = primaryMargin())) {
                FuelingRecordList(fuelings = fuelings, listState = listState, navigation = navigation, currency = currency)
            }
        }
    }
}

@Composable
fun FuelingRecordList(
    fuelings: MutableList<Fueling>,
    listState: LazyListState = rememberLazyListState(),
    navigation: INavigationRouter,
    currency: String
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(globalSpacer())
    ) {
        item(key = 98986) {
            Text(
                text = stringResource(id = R.string.fuel_list_title),
                style = screenTitleStyle()
            )
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
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(globalSpacer())) {
                Column() {
                    val dateString = DateUtils.getDateString(fueling.fueling.date)
                    Text(text = dateString, style = listItemLabel())
                    Text(text = fueling.vehicle.name, style = listItemTitle())
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(globalSpacer())
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val fuelTypeResourceID = FuelUtils.getNameResourceID(fueling.vehicle.fuelTypeID)

                        Text(
                            text = if(fuelTypeResourceID != null) "${stringResource(id = fuelTypeResourceID)} ${fueling.fueling.specification ?: ""}" else "",
                            style = listItemBodyStyle()
                        )

                        Text(
                            text = "${fueling.fueling.priceUnit.round()} ${currency}/${fuelUnit} × ${fueling.fueling.quantity.round()} $fuelUnit",
                            style = listItemBodyStyle()
                        )
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
                            text = stringResource(id = R.string.fuel_list_record_total_price),
                            style = listItemBodyStyle()
                        )
                        Text(
                            text = "${(fueling.fueling.priceUnit * fueling.fueling.quantity).round()} $currency",
                            style = listItemBodyStyle()
                        )
                    }
                }
            }
        }
    }
}