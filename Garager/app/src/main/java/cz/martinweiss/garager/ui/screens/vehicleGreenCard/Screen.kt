package cz.martinweiss.garager.ui.screens.vehicleGreenCard

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import cz.martinweiss.garager.R
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BackArrowScreen
import cz.martinweiss.garager.ui.elements.ZoomableImage
import cz.martinweiss.garager.utils.FileUtils
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreenCardVehicleScreen(navigation: INavigationRouter, greenCardFileName: String) {
    if(FileUtils.isInternalFilePDF(greenCardFileName)) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.title_green_card_vehicle)) },
                    navigationIcon = {
                        IconButton(onClick = { navigation.returnBack() } ) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                        }
                    }
                )
            }
        ) {
            GreenCardVehicleScreenContentPDF(it, greenCard = greenCardFileName)
        }
    } else {
        BackArrowScreen(
            topBarTitle = stringResource(id = R.string.title_green_card_vehicle),
            onBackClick = { navigation.returnBack() }
        ) {
            GreenCardVehicleScreenContentImage(greenCard = greenCardFileName)
        }
    }
}

@Composable
fun GreenCardVehicleScreenContentPDF(
    paddingValues: PaddingValues,
    greenCard: String
) {
    val file = File(LocalContext.current.filesDir, greenCard)
    val uri = file.toUri()
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Local(uri),
        isZoomEnable = true
    )

    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(paddingValues)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GreenCardVehicleScreenContentImage(greenCard: String) {
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val file = File(LocalContext.current.filesDir, greenCard)
    val fileByteArray = file.readBytes()
    bitmap = fileByteArray.let { BitmapFactory.decodeByteArray(fileByteArray, 0, it.size) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        bitmap?.let { image ->
            ZoomableImage(
                painter = BitmapPainter(image.asImageBitmap()),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
