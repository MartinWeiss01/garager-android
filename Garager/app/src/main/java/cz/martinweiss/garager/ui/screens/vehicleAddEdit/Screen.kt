package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.martinweiss.garager.R
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BackArrowScreen

@Composable
fun AddEditVehicleScreen(navigation: INavigationRouter, id: Long?) {
    BackArrowScreen(
        topBarTitle = stringResource(id = R.string.title_add_edit_vehicle),
        onBackClick = { navigation.returnBack() }
    ) {
        AddEditVehicleContent()   
    }
}

@Composable
fun AddEditVehicleContent() {
    Text(text = "AddEditVehicleContent")
}