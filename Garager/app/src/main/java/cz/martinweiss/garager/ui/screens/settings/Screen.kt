package cz.martinweiss.garager.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.ListItemPicker
import com.chargemap.compose.numberpicker.NumberPicker
import cz.martinweiss.garager.BuildConfig
import cz.martinweiss.garager.R
import cz.martinweiss.garager.model.currencies
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BaseScreenSheetLayout
import cz.martinweiss.garager.ui.elements.ClickableListItem
import cz.martinweiss.garager.ui.theme.globalSpacer
import cz.martinweiss.garager.ui.theme.primaryMargin
import cz.martinweiss.garager.ui.theme.screenTitleStyle
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

@OptIn(ExperimentalMaterial3Api::class)
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
    val currencyState = remember { mutableStateOf(data.currency) }

    Surface(
        modifier = Modifier.padding(paddingValues)
    ) {
        Column(
            modifier = Modifier.padding(top = 0.dp, start = primaryMargin(), end = primaryMargin()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(globalSpacer())) {
                Text(
                    text = stringResource(id = R.string.title_settings_screen_info),
                    style = screenTitleStyle()
                )
                Column {
                    ApplicationAuthor()
                    ApplicationVersion()
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(globalSpacer())) {
                Text(
                    text = stringResource(id = R.string.title_settings_screen),
                    style = screenTitleStyle()
                )
                Column {
                    val daysString = LocalContext.current.resources.getQuantityString(
                        R.plurals.settings_mot_days_warning_days, data.motDaysWarning, data.motDaysWarning
                    )

                    ClickableListItem(
                        headlineText = { Text(text = stringResource(id = R.string.settings_mot_days_warning_label)) },
                        supportingText = { Text(text = "${data.motDaysWarning} $daysString") },
                        trailingContent = {
                            Icon(
                                Icons.Filled.KeyboardArrowRight,
                                contentDescription = null,
                            )
                        },
                        onClick = {
                            motDaysWarningState.value = data.motDaysWarning //TODO binding problem
                            sheetContentState.value = { MOTExpirationDays(actions = actions, motDaysWarningState = motDaysWarningState) }
                            showSheet()
                        }
                    )

                    ClickableListItem(
                        headlineText = { Text(text = stringResource(id = R.string.settings_currency_label)) },
                        supportingText = { Text(text = "${data.currency}") },
                        trailingContent = {
                            Icon(
                                Icons.Filled.KeyboardArrowRight,
                                contentDescription = null,
                            )
                        },
                        onClick = {
                            currencyState.value = data.currency //TODO binding problem
                            sheetContentState.value = { CurrencySelect(actions = actions, currencyState = currencyState)}
                            showSheet()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationAuthor() {
    ClickableListItem(
        headlineText = { Text(text = stringResource(id = R.string.settings_author_app)) },
        supportingText = { Text(text = "Martin Weiss") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationVersion() {
    ClickableListItem(
        headlineText = { Text(text = stringResource(id = R.string.settings_version_app)) },
        supportingText = { Text(text = BuildConfig.VERSION_NAME) }
    )
}

@Composable
fun MOTExpirationDays(
    actions: SettingsActions,
    motDaysWarningState: MutableState<Int>
) {
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
fun CurrencySelect(
    actions: SettingsActions,
    currencyState: MutableState<String>
) {
    SettingsElement(
        title = stringResource(id = R.string.settings_currency_label),
        description = stringResource(id = R.string.settings_currency_description),
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ListItemPicker(
                value = currencyState.value,
                list = currencies.map { it.name },
                onValueChange = { it
                    actions.updateCurrency(it)
                    currencyState.value = it
                },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                dividersColor = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(modifier = Modifier.width(10.dp))
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
