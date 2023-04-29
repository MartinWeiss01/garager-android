package cz.martinweiss.garager.ui.screens.vehicleGreenCard

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import cz.martinweiss.garager.R
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BackArrowScreen
import java.io.File

@Composable
fun GreenCardVehicleScreen(navigation: INavigationRouter, greenCardFileName: String) {
    BackArrowScreen(
        topBarTitle = stringResource(id = R.string.title_green_card_vehicle),
        onBackClick = { navigation.returnBack() }
    ) {
        GreenCardVehicleScreenContent(greenCard = greenCardFileName)
    }
}

@Composable
fun GreenCardVehicleScreenContent(greenCard: String) {
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var scale by remember { mutableStateOf(1f) }
    val state = rememberTransformableState { zoomChange, _, _ ->
        scale *= zoomChange
    }

    val file = File(LocalContext.current.filesDir, greenCard)
    val fileByteArray = file.readBytes()
    bitmap = fileByteArray.let { BitmapFactory.decodeByteArray(fileByteArray, 0, it.size) }

    Box(
        Modifier
            .transformable(state = state)
    ) {
        Image(
            bitmap!!.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier.scale(scale)
        )
    }
}
