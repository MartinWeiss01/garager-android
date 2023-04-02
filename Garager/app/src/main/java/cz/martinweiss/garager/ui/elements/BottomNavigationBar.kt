package cz.martinweiss.garager.ui.elements

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import cz.martinweiss.garager.navigation.Destination

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Destination.VehicleListScreen
    )
    
    NavigationBar() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach {
            NavigationBarItem(
                icon = {
                    Icon(painterResource(id = if (currentRoute == it.route) it.iconActive else it.icon), stringResource(id = it.title))
                },
                label = {
                    Text(text = stringResource(id = it.title))
                },
                selected = currentRoute == it.route,
                onClick = { /*TODO*/ }
            )
        }
    }
}