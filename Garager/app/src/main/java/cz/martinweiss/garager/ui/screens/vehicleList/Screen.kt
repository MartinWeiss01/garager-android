package cz.martinweiss.garager.ui.screens.vehicleList

import androidx.compose.foundation.layout.*
import cz.martinweiss.garager.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.martinweiss.garager.model.Vehicle
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BottomNavigationBar
import org.koin.androidx.compose.getViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleListScreen(navigation: INavigationRouter, viewModel: VehicleListViewModel = getViewModel()) {
    val listState = rememberLazyListState()

    val vehicles = remember {
        mutableStateListOf<Vehicle>()
    }

    viewModel.vehicleListUIState.value.let {
        when(it) {
            VehicleListUIState.Default -> {
                viewModel.loadVehicles()
            }
            is VehicleListUIState.Success -> {
                vehicles.clear()
                vehicles.addAll(it.vehicles)
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name)
                        .uppercase(Locale.getDefault()))
                },
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = stringResource(id = R.string.btn_add_new_vehicle)) },
                icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = "") },
                onClick = { navigation.navigateToAddEditVehicleScreen(-1L) },
                expanded = listState.isScrollingUp()
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navigation.getNavController())
        }
    ) {
        VehicleListContent(
            paddingValues = it,
            vehicles = vehicles,
            listState = listState
        )
    }
}

@Composable
fun VehicleListContent(
    paddingValues: PaddingValues,
    vehicles: MutableList<Vehicle>,
    listState: LazyListState
) {
    Surface(
        color = MaterialTheme.colorScheme.inverseSurface,
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Box(modifier = Modifier.padding(vertical = 40.dp, horizontal = 20.dp)) {
            VehicleItemList(vehicles = vehicles, listState = listState)
        }
    }
}

@Composable
fun VehicleItemList(vehicles: MutableList<Vehicle>, listState: LazyListState) {
    LazyColumn(state = listState) {
        vehicles.forEach {
            item(key = it.id) {
                VehicleItem(vehicle = it)
            }
        }
    }
}

@Composable
fun VehicleItem(vehicle: Vehicle) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column {
            Text(text = vehicle.licensePlate)
            vehicle.name?.let { Text(text = it) }
        }
        
        Icon(imageVector = Icons.Filled.Info, contentDescription = "")
    }
}

/** Google Code Labs source code
 * Returns whether the lazy list is currently scrolling up.
 */
@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}