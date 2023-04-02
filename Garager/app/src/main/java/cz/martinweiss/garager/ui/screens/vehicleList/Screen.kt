package cz.martinweiss.garager.ui.screens.vehicleList

import cz.martinweiss.garager.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleListScreen() {
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
                onClick = { /*TODO*/ })
        }
    ) {
        VehicleListContent(paddingValues = it)
    }
}

@Composable
fun VehicleListContent(paddingValues: PaddingValues) {
    Column(modifier = Modifier.padding(paddingValues)) {
        Text(text = "VehicleListContent")
    }
}