package cz.martinweiss.garager.ui.screens.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.R
import cz.martinweiss.garager.ui.elements.BottomNavigationBar
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navigation: INavigationRouter) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_settings_screen)
                        .uppercase(Locale.getDefault())
                    )
                },
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navigation.getNavController())
        }
    ) {
        SettingsScreenContent(paddingValues = it)
    }
}

@Composable
fun SettingsScreenContent(
    paddingValues: PaddingValues
) {
    Surface(
        modifier = Modifier.padding(paddingValues)
    ) {
        Text(text = "Settings Screen")
    }
}