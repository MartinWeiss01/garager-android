package cz.martinweiss.garager.ui.screens.vehicleList

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.martinweiss.garager.R
import cz.martinweiss.garager.extensions.isScrollingUp
import cz.martinweiss.garager.model.Fueling
import cz.martinweiss.garager.model.Vehicle
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BaseScreenLayout
import cz.martinweiss.garager.ui.elements.LoadingSpinner
import cz.martinweiss.garager.ui.elements.PlaceholderScreen
import cz.martinweiss.garager.ui.screens.fuelList.FuelingRecord
import cz.martinweiss.garager.ui.theme.*
import cz.martinweiss.garager.utils.DateUtils
import org.koin.androidx.compose.getViewModel

@Composable
fun VehicleListScreen(
    navigation: INavigationRouter,
    viewModel: VehicleListViewModel = getViewModel()
) {
    val listState = rememberLazyListState()

    var data: VehicleListData by remember {
        mutableStateOf(viewModel.data)
    }

    val vehicles = remember {
        mutableStateListOf<Vehicle>()
    }

    val fuelings = remember {
        mutableStateListOf<Fueling>()
    }

    val loaded = remember {
        mutableStateOf(false)
    }

    viewModel.vehicleListUIState.value.let {
        when (it) {
            VehicleListUIState.Init -> {
                if(!loaded.value) {
                    viewModel.loadVehicles()
                    loaded.value = true
                }
            }
            is VehicleListUIState.Success -> {
                vehicles.clear()
                vehicles.addAll(it.vehicles)
                fuelings.clear()
                fuelings.addAll(it.fuelings)
                viewModel.vehicleListUIState.value = VehicleListUIState.Init
            }
            VehicleListUIState.Changed -> {
                data = viewModel.data
                viewModel.vehicleListUIState.value = VehicleListUIState.Init
            }
        }
    }

    BaseScreenLayout(
        navController = navigation.getNavController(),
        textFAB = stringResource(id = R.string.btn_add_new_vehicle),
        expandedFAB = listState.isScrollingUp(),
        onFABClick = { navigation.navigateToAddEditVehicleScreen(-1L) }
    ) {
        if(data.loading) {
            LoadingSpinner()
        } else {
            VehicleListContent(
                paddingValues = it,
                navigation = navigation,
                vehicles = vehicles,
                fuelings = fuelings,
                listState = listState,
                data = data,
                actions = viewModel
            )
        }
    }
}

@Composable
fun VehicleListContent(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    vehicles: MutableList<Vehicle>,
    fuelings: MutableList<Fueling>,
    listState: LazyListState,
    data: VehicleListData,
    actions: VehicleListActions
) {
    Surface(
        shape = RoundedCornerShape(topStart = globalRadius()*2, topEnd = globalRadius()*2),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if (vehicles.size == 0) {
            PlaceholderScreen(
                icon = painterResource(id = R.drawable.icon_car_crash_filled),
                title = stringResource(id = R.string.vehicle_list_empty_title),
                description = stringResource(
                    id = R.string.vehicle_list_empty_description,
                    stringResource(id = R.string.btn_add_new_vehicle)
                )
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                item(key = 1) {
                    Text(
                        text = stringResource(id = R.string.vehicle_list_title),
                        style = screenTitleStyle(),
                        modifier = Modifier.padding(
                            top = primaryMargin() * 2,
                            start = primaryMargin()
                        )
                    )
                }

                item(key = 2) {
                    VehicleItemList(
                        vehicles = vehicles,
                        listState = listState,
                        navigation = navigation,
                        data = data,
                        actions = actions,
                        contentPadding = PaddingValues(primaryMargin())
                    )
                }

                if(data.scrollSnapIndex in vehicles.indices) {
                    val filteredFuelings = fuelings.filter { it.fueling.vehicleId == vehicles[data.scrollSnapIndex].id }.toMutableList()

                    item(key = 3) {
                        Column(
                            modifier = Modifier
                                .padding(top = primaryMargin() * 1)
                                .clip(RoundedCornerShape(globalRadius() * 2))
                                .background(surfaceContainerColor())
                                .padding(
                                    top = primaryMargin() * 2,
                                    start = primaryMargin(),
                                    end = primaryMargin(),
                                    bottom = primaryMargin() * 2
                                ),
                            verticalArrangement = Arrangement.spacedBy(globalSpacer())
                        ) {
                            Text(
                                text = stringResource(id = R.string.vehicle_list_title_fuel_history),
                                style = screenTitleStyle(),
                                color = Color.White,
                                modifier = Modifier.padding(
                                    bottom = primaryMargin() * 1,
                                )
                            )

                            if(filteredFuelings.size == 0) {
                                PlaceholderScreen(
                                    icon = painterResource(id = R.drawable.icon_breaking_news_filled),
                                    title = stringResource(id = R.string.fuel_list_empty_title),
                                    description = "",
                                    darkContainer = true
                                )
                            } else {
                                filteredFuelings.forEach {
                                    FuelingRecord(fueling = it, onClick = { it.fueling.id?.let { fuelingId ->
                                        navigation.navigateToDetailFuelingScreen(fuelingId)
                                    } }, currency = data.currency)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VehicleItemList(
    vehicles: MutableList<Vehicle>,
    listState: LazyListState,
    navigation: INavigationRouter,
    data: VehicleListData,
    actions: VehicleListActions,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LaunchedEffect(listState.isScrollInProgress) {
        if(!listState.isScrollInProgress) {
            Log.d("###################", "UPDATING")
            var currentIndex: Int = listState.firstVisibleItemIndex
            if(currentIndex != 0 || listState.canScrollBackward) currentIndex += 1
            else currentIndex = 0
            actions.updateVehicleSnapIndex(currentIndex)
        }
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(globalSpacer()),
        contentPadding = contentPadding,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    ) {
        vehicles.forEachIndexed { index, it ->
            item(key = it.id) {
                VehicleItem(vehicle = it, motDaysWarning = data.motDaysWarning, onClick = {
                    it.id?.let { vehicleId -> navigation.navigateToDetailVehicleScreen(vehicleId) }
                })
            }
        }
    }
}

@Composable
fun VehicleItem(
    vehicle: Vehicle,
    motDaysWarning: Int,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(globalRadius())
    var dateString: String = ""
    var remainingDays: Int? = null

    vehicle.motDate?.let {
        dateString = DateUtils.getDateString(it)
        remainingDays = DateUtils.getRemainingDays(it)
    }

    Card(
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = if (remainingDays != null && remainingDays!! > 0) warningContainerColor() else MaterialTheme.colorScheme.errorContainer,
            contentColor = if (remainingDays != null && remainingDays!! > 0) onWarningColor() else MaterialTheme.colorScheme.error,
        )
    ) {
        Column {
            Card(
                modifier = Modifier
                    .widthIn(min = 350.dp)
                    .clickable(onClick = onClick),
                shape = shape,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                //elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Row {
                        Column(verticalArrangement = Arrangement.spacedBy(globalSpacer())) {
                            Column() {
                                Text(text = vehicle.name, style = listItemLabel())
                                vehicle.licensePlate?.let {
                                    Text(text = it, style = listItemTitle())
                                }
                            }

                            Column() {
                                vehicle.vin?.let {
                                    Text(
                                        text = stringResource(id = R.string.vehicle_list_vin, it),
                                        style = listItemBodyStyle()
                                    )
                                }

                                remainingDays?.let {
                                    Text(
                                        text = LocalContext.current.resources.getQuantityString(
                                            R.plurals.vehicle_list_mot,
                                            it,
                                            dateString, it
                                        ),
                                        style = listItemBodyStyle(),
                                        color = if (it <= motDaysWarning) MaterialTheme.colorScheme.error else LocalContentColor.current
                                    )
                                }

                                if (vehicle.greenCardFilename != null) {
                                    Text(
                                        text = stringResource(id = R.string.vehicle_list_green_card_available),
                                        style = listItemBodyStyle(),
                                        color = colorResource(id = R.color.success)
                                    )
                                } else {
                                    Text(
                                        text = stringResource(id = R.string.vehicle_list_green_card_unavailable),
                                        style = listItemBodyStyle(),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }

            remainingDays?.let {
                if (it <= motDaysWarning) {
                    var infoText: String = ""
                    if (it < 0) {
                        infoText = LocalContext.current.resources.getQuantityString(
                            R.plurals.vehicle_list_mot_expired,
                            it,
                            it * -1
                        )
                    } else if (it == 0) {
                        infoText = stringResource(id = R.string.vehicle_list_mot_expired_today)
                    } else {
                        infoText = LocalContext.current.resources.getQuantityString(
                            R.plurals.vehicle_list_mot_active,
                            it,
                            it
                        )
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = null,
                            tint = if (remainingDays != null && remainingDays!! > 0) onWarningColor() else MaterialTheme.colorScheme.error,
                        )

                        Text(
                            text = infoText,
                            style = listItemBodyStyle()
                        )
                    }
                }
            }
        }
    }
}