package cz.martinweiss.garager.ui.elements

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackArrowScreen(
    topBarTitle: String,
    onBackClick: () -> Unit,
    actions: @Composable (RowScope.() -> Unit) = {},
    ignoreLazyColumn: Boolean = false,
    content: @Composable (paddingValues: PaddingValues) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(text = topBarTitle) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() } ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                    }
                },
                actions = actions,
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        if(!ignoreLazyColumn) {
            LazyColumn(modifier = Modifier.padding(it)) {
                item {
                    content(it)
                }
            }
        } else {
            content(it)
        }
    }
}