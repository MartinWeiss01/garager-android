package cz.martinweiss.garager.ui.screens.settings

import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker
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
                    Text(text = stringResource(id = R.string.app_name)
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
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Text(text = stringResource(id = R.string.title_settings_screen))
            Text(text = "Build: ${Build.VERSION.SDK_INT} | Q: ${Build.VERSION_CODES.Q}")
            MOTExpirationDays()
        }
    }
}
@Composable
fun MOTExpirationDays() {
    var pickerValue by remember { mutableStateOf(0) }

    SettingsElement {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Text(text = stringResource(id = R.string.settings_mot_days_warning_label))
            Text(text = stringResource(id = R.string.settings_mot_days_warning_description), fontSize = 10.sp, lineHeight = 15.sp)
        }

        Row(
            modifier = Modifier.padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NumberPicker(
                value = pickerValue,
                range = 10..60,
                onValueChange = {
                    pickerValue = it
                }
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(text = stringResource(id = R.string.settings_mot_days_warning_days))
        }
    }
}

@Composable
fun SettingsElement(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}
