package cz.martinweiss.garager.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.martinweiss.garager.ui.theme.globalSpacer

@Composable
fun PlaceholderScreen(
    icon: Painter,
    title: String,
    description: String,
    darkContainer: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.spacedBy(globalSpacer(), Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = icon,
            contentDescription = "",
            modifier = Modifier.size(70.dp),
            tint = if(darkContainer) Color.White else LocalContentColor.current
        )

        Text(
            text = title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = if(darkContainer) Color.White else Color.Unspecified
        )
        Text(
            text = description,
            textAlign = TextAlign.Center,
            color = if(darkContainer) Color.White else Color.Unspecified
        )
    }
}