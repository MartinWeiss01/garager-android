package cz.martinweiss.garager.ui.elements

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cz.martinweiss.garager.R
import java.util.*

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
                Text(
                    text = stringResource(id = R.string.app_name).uppercase(Locale.getDefault()),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 6.sp
                )
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

/** Google Code Labs source code
 * Returns whether the lazy list is currently scrolling up.
 */
@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}