package cz.martinweiss.garager.ui.elements

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.martinweiss.garager.R
import cz.martinweiss.garager.ui.theme.globalSpacer
import cz.martinweiss.garager.ui.theme.screenTitleStyle

@Composable
fun LoadingSpinner() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.spacedBy(globalSpacer(), Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CircularProgressIndicator(
                modifier = Modifier.size(size = 36.dp)
            )
            Spacer(modifier = Modifier.width(globalSpacer()*2))
            Text(
                text = stringResource(id = R.string.loading),
                color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                style = screenTitleStyle()
            )
        }
    }
}