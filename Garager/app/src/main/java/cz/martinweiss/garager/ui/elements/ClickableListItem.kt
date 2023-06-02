package cz.martinweiss.garager.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClickableListItem(
    headlineText: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    overlineText: @Composable() (() -> Unit)? = null,
    supportingText: @Composable() (() -> Unit)? = null,
    leadingContent: @Composable() (() -> Unit)? = null,
    trailingContent: @Composable() (() -> Unit)? = null,
) {
    ListItem(
        headlineText = headlineText,
        overlineText = overlineText,
        supportingText = supportingText,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(
                enabled = true,
                onClick = onClick
            )
    )
}