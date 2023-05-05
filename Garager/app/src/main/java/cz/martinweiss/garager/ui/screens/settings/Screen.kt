package cz.martinweiss.garager.ui.screens.settings

import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker
import cz.martinweiss.garager.BuildConfig
import cz.martinweiss.garager.R
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BaseScreenSheetLayout
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(navigation: INavigationRouter, viewModel: SettingsViewModel = getViewModel()) {
    var data: SettingsData by remember {
        mutableStateOf(viewModel.data)
    }

    val sheetContentState = remember {
        mutableStateOf<@Composable ColumnScope.() -> Unit>({})
    }

    val isSheetVisibleState = remember {
        mutableStateOf(false)
    }

    viewModel.settingsUIState.value.let {
        when(it) {
            SettingsUIState.Default -> { }
            SettingsUIState.Loading -> {
                viewModel.loadInitSettings()
            }
            SettingsUIState.Updated -> {
                data = viewModel.data
                viewModel.settingsUIState.value = SettingsUIState.Default
            }
        }
    }

    BackHandler(enabled = true, onBack = {
        //handle navigation returnBack on hidden sheet
        sheetContentState.value = {}
    })

    BaseScreenSheetLayout(
        navController = navigation.getNavController(),
        hideFAB = true,
        sheetContent = sheetContentState.value,
        isSheetVisible = isSheetVisibleState.value,
        onSheetCollapsed = { sheetContentState.value = {} }
    ) {
        SettingsScreenContent(
            paddingValues = it,
            actions = viewModel,
            data = data,
            sheetContentState = sheetContentState,
            isSheetVisibleState = isSheetVisibleState,
            showSheet = {
                isSheetVisibleState.value = true
            }
        )
    }
}

@Composable
fun SettingsScreenContent(
    paddingValues: PaddingValues,
    actions: SettingsActions,
    data: SettingsData,
    sheetContentState: MutableState<@Composable ColumnScope.() -> Unit>,
    isSheetVisibleState: MutableState<Boolean>,
    showSheet: () -> Unit,
) {
    val motDaysWarningState = remember { mutableStateOf(data.motDaysWarning) }

    Surface(
        modifier = Modifier.padding(paddingValues)
    ) {
        Column(
            modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = stringResource(id = R.string.title_settings_screen))
            Text(text = "Build: ${Build.VERSION.SDK_INT} | Q: ${Build.VERSION_CODES.Q}")
            SettingsDivider()
            ApplicationVersion()
            SettingsDivider()
            Text(text = "${data.motDaysWarning}")
            Button(onClick = {
                sheetContentState.value = { MOTExpirationDays(actions = actions, motDaysWarningState = motDaysWarningState) }
                showSheet()
            }) {
                Text(text = "HARDCODED: DISPLAY")
            }
        }
    }
}

@Composable
fun SheetItem(
    sheetContentState: MutableState<@Composable ColumnScope.() -> Unit>,
    showSheet: () -> Unit,
    sheetContent: @Composable RowScope.() -> Unit
) {

}

@Composable
fun ApplicationAuthor() {
    SettingsElement(
        title = stringResource(id = R.string.settings_author_app), caption = "Martin Weiss"
    ) {}
}

@Composable
fun ApplicationVersion() {
    SettingsElement(
        title = stringResource(id = R.string.settings_version_app),
        caption = BuildConfig.VERSION_NAME
    ) {}
}

@Composable
fun MOTExpirationDays(
    actions: SettingsActions,
    motDaysWarningState: MutableState<Int>
) {
    LaunchedEffect(motDaysWarningState.value) {
        Log.d("[###############]", "PROVADIM REKOMPOZICI MOT EXPIRATION DAYS")
    }
    SettingsElement(
        title = stringResource(id = R.string.settings_mot_days_warning_label),
        description = stringResource(id = R.string.settings_mot_days_warning_description),
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NumberPicker(
                value = motDaysWarningState.value,
                range = 10..60,
                onValueChange = {
                    actions.updateMOTDaysWarning(it)
                    motDaysWarningState.value = it
                    Log.d("###########", "Weird Recomposition: $it")
                },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                dividersColor = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = LocalContext.current.resources.getQuantityString(
                    R.plurals.settings_mot_days_warning_days, motDaysWarningState.value, motDaysWarningState.value
                )
            )
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
            if (caption != null) Text(text = caption, fontSize = 14.sp, lineHeight = 15.sp)
            if (description != null) Text(text = description, fontSize = 10.sp, lineHeight = 15.sp)
        }

        content()
    }
}

@Composable
fun SettingsDivider() {
    Divider(
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = Modifier
            .fillMaxWidth()
            .width(1.dp)
    )
}