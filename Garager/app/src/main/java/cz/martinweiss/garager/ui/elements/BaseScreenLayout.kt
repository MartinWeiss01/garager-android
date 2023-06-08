package cz.martinweiss.garager.ui.elements

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cz.martinweiss.garager.R
import cz.martinweiss.garager.ui.theme.globalRadius
import java.util.*

@Composable
fun AppLogo() = Text(
    text = stringResource(id = R.string.app_name).uppercase(Locale.getDefault()),
    fontSize = 24.sp,
    fontWeight = FontWeight.Black,
    letterSpacing = 0.sp
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreenLayout(
    navController: NavController,
    textFAB: String = "",
    iconFAB: ImageVector = Icons.Filled.Add,
    expandedFAB: Boolean = false,
    onFABClick: () -> Unit = {},
    hideFAB: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                AppLogo()
            },
        )
    }, floatingActionButton = {
        if(!hideFAB) {
            ExtendedFloatingActionButton(text = { Text(text = textFAB) },
                icon = { Icon(imageVector = iconFAB, contentDescription = "") },
                onClick = { onFABClick() },
                expanded = expandedFAB
            )
        }
    }, bottomBar = {
        BottomNavigationBar(navController = navController)
    }) {
        content(it)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun BaseScreenSheetLayout(
    navController: NavController,
    textFAB: String = "",
    iconFAB: ImageVector = Icons.Filled.Add,
    expandedFAB: Boolean = false,
    onFABClick: () -> Unit = {},
    hideFAB: Boolean = false,
    sheetContent: @Composable ColumnScope.() -> Unit = {},
    isSheetVisible: Boolean = false,
    onSheetVisibilityChanged: (Boolean) -> Unit = {},
    onSheetCollapsed: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(
            initialValue = if (isSheetVisible) BottomSheetValue.Expanded else BottomSheetValue.Collapsed
        )
    )

    LaunchedEffect(bottomSheetScaffoldState.bottomSheetState) {
        snapshotFlow { bottomSheetScaffoldState.bottomSheetState.isCollapsed }
            .collect { isCollapsed ->
                if (isCollapsed) {
                    onSheetCollapsed()
                }
            }
    }

    LaunchedEffect(bottomSheetScaffoldState.bottomSheetState) {
        snapshotFlow { bottomSheetScaffoldState.bottomSheetState.isExpanded }
            .collect { isExpanded ->
                onSheetVisibilityChanged(isExpanded)
            }
    }

    BottomSheetScaffold(
        sheetBackgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        sheetShape = RoundedCornerShape(topStart = globalRadius(), topEnd = globalRadius(), bottomStart = 0.dp, bottomEnd = 0.dp),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = sheetContent,
        sheetPeekHeight = 0.dp,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    AppLogo()
                },
            )
        },
        floatingActionButton = {
            if (!hideFAB) {
                ExtendedFloatingActionButton(
                    text = { Text(text = textFAB) },
                    icon = { Icon(imageVector = iconFAB, contentDescription = "") },
                    onClick = { onFABClick() },
                    expanded = expandedFAB
                )
            }
        },
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) {
            content(it)
        }
    }
}
