package cz.martinweiss.garager.ui.screens.vehicleList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.martinweiss.garager.R
import cz.martinweiss.garager.model.Vehicle
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BaseScreenLayout
import cz.martinweiss.garager.ui.elements.PlaceholderScreen
import cz.martinweiss.garager.ui.elements.isScrollingUp
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

    viewModel.vehicleListUIState.value.let {
        when (it) {
            VehicleListUIState.Default -> {
                viewModel.loadVehicles()
            }
            is VehicleListUIState.Success -> {
                vehicles.clear()
                vehicles.addAll(it.vehicles)
            }
        }
    }

    BaseScreenLayout(
        navController = navigation.getNavController(),
        textFAB = stringResource(id = R.string.btn_add_new_vehicle),
        expandedFAB = listState.isScrollingUp(),
        onFABClick = { navigation.navigateToAddEditVehicleScreen(-1L) }
    ) {
        VehicleListContent(
            paddingValues = it,
            navigation = navigation,
            vehicles = vehicles,
            listState = listState,
            data = data
        )
    }
}

@Composable
fun VehicleListContent(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    vehicles: MutableList<Vehicle>,
    listState: LazyListState,
    data: VehicleListData
) {
    Surface(
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if(vehicles.size == 0) {
            PlaceholderScreen(
                icon = painterResource(id = R.drawable.icon_car_crash_filled),
                title = stringResource(id = R.string.vehicle_list_empty_title),
                description = stringResource(id = R.string.vehicle_list_empty_description, stringResource(id = R.string.btn_add_new_vehicle))
            )
        } else {
            Box(modifier = Modifier.padding(top = 40.dp, start = 35.dp, end = 35.dp)) {
                VehicleItemList(vehicles = vehicles, listState = listState, navigation = navigation, data = data)
            }
        }
    }
}

@Composable
fun VehicleItemList(
    vehicles: MutableList<Vehicle>,
    listState: LazyListState,
    navigation: INavigationRouter,
    data: VehicleListData
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                text = stringResource(id = R.string.vehicle_list_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        vehicles.forEach {
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Box(modifier = Modifier.padding(20.dp)) {
            Row {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Column() {
                        Text(text = vehicle.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        vehicle.licensePlate?.let {
                            Text(text = it, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Column() {
                        vehicle.vin?.let {
                            Text(
                                text = stringResource(id = R.string.vehicle_list_vin, it),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        vehicle.motDate?.let {
                            val dateString = DateUtils.getDateString(it)
                            val remainingDays = DateUtils.getRemainingDays(it)

                            Text(
                                text = LocalContext.current.resources.getQuantityString(
                                    R.plurals.vehicle_list_mot,
                                    remainingDays,
                                    dateString, remainingDays
                                ),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (remainingDays <= motDaysWarning) MaterialTheme.colorScheme.error else LocalContentColor.current
                            )
                        }

                        if (vehicle.greenCardFilename != null) {
                            Text(
                                text = stringResource(id = R.string.vehicle_list_green_card_available),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.success)
                            )
                        } else {
                            Text(
                                text = stringResource(id = R.string.vehicle_list_green_card_unavailable),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
