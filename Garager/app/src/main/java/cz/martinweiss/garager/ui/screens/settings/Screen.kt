package cz.martinweiss.garager.ui.screens.settings

import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker
import cz.martinweiss.garager.BuildConfig
import cz.martinweiss.garager.R
import cz.martinweiss.garager.navigation.INavigationRouter
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
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = stringResource(id = R.string.title_settings_screen))
            Text(text = "Build: ${Build.VERSION.SDK_INT} | Q: ${Build.VERSION_CODES.Q}")
            SettingsDivider()
            ApplicationVersion()
            SettingsDivider()
            MOTExpirationDays()
        }
    }
}

@Composable
fun ApplicationVersion() {
    SettingsElement(
        title = stringResource(id = R.string.settings_version_app),
        caption = BuildConfig.VERSION_NAME
    ) {}
}

@Composable
fun MOTExpirationDays() {
    var pickerValue by remember { mutableStateOf(0) }

    SettingsElement(
        title = stringResource(id = R.string.settings_mot_days_warning_label),
        description = stringResource(id = R.string.settings_mot_days_warning_description),
        modifier = Modifier.background(Color.Gray)
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NumberPicker(
                value = pickerValue,
                range = 10..60,
                onValueChange = {
                    pickerValue = it
                    Log.d("Update", "$it")
                },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(text = stringResource(id = R.string.settings_mot_days_warning_days))
        }
    }
}

@Composable
fun SettingsElement(
    title: String,
    caption: String? = null,
    description: String? = null,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Text(text = title, fontSize = 16.sp)
            if(caption != null) Text(text = caption, fontSize = 14.sp, lineHeight = 15.sp)
            if(description != null) Text(text = description, fontSize = 10.sp, lineHeight = 15.sp)
        }

        content()
    }
}

@Composable
fun SettingsDivider() {
    Divider(
        color = Color.Red,
        modifier = Modifier
            .fillMaxWidth()
            .width(1.dp)
    )
}