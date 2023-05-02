package cz.martinweiss.garager.ui.screens.fuelList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.martinweiss.garager.R
import cz.martinweiss.garager.model.Fueling
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BottomNavigationBar
import cz.martinweiss.garager.ui.screens.vehicleList.*
import cz.martinweiss.garager.utils.DateUtils
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
                text = { Text(text = stringResource(id = R.string.btn_add_new_fueling)) },
                icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = "") },
                onClick = { navigation.navigateToAddEditFuelingScreen(-1L) },
                expanded = listState.isScrollingUp()
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navigation.getNavController())
        }
    ) {
        FuelListContent(
            paddingValues = it,
            navigation = navigation,
            fuelings = fuelings,
            listState = listState
        )
    }
}

@Composable
fun FuelListContent(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    fuelings: MutableList<Fueling>,
    listState: LazyListState
) {
    Surface(
        color = MaterialTheme.colorScheme.inverseSurface,
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if(fuelings.size == 0) {
            EmptyFuelingsList()
        } else {
            Box(modifier = Modifier.padding(top = 40.dp, start = 35.dp, end = 35.dp)) {
                FuelingRecordList(fuelings = fuelings, listState = listState, navigation = navigation)
            }
        }
    }
}

@Composable
fun FuelingRecordList(
    fuelings: MutableList<Fueling>,
    listState: LazyListState,
    navigation: INavigationRouter
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
                })
            }
        }
    }
}

@Composable
fun FuelingRecord(
    fueling: Fueling,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(Color.Red),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Box(modifier = Modifier.padding(20.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Column() {
                    val dateString = DateUtils.getDateString(fueling.fueling.date)
                    Text(text = dateString, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(text = fueling.vehicle.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Row() {
                    Text(text = "${fueling.fueling.priceUnit} x ${fueling.fueling.quantity}", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }

                Row() {
                    Text(text = stringResource(id = R.string.fuel_list_record_total_price), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(text = "${fueling.fueling.priceUnit * fueling.fueling.quantity}", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun EmptyFuelingsList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painter = painterResource(id = R.drawable.icon_breaking_news_filled), contentDescription = "", modifier = Modifier.size(70.dp))

        Text(
            text = stringResource(id = R.string.fuel_list_empty_title),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(id = R.string.fuel_list_empty_description, stringResource(id = R.string.btn_add_new_fueling)),
            textAlign = TextAlign.Center
        )
    }
}