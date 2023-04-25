package cz.martinweiss.garager.ui.screens.vehicleAddEdit

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import android.Manifest
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import cz.martinweiss.garager.R
import cz.martinweiss.garager.navigation.INavigationRouter
import cz.martinweiss.garager.ui.elements.BackArrowScreen
import cz.martinweiss.garager.ui.elements.CustomTextField
import cz.martinweiss.garager.ui.elements.ReactiveField
import cz.martinweiss.garager.utils.DateUtils
import org.koin.androidx.compose.getViewModel
import java.util.Calendar

@Composable
fun AddEditVehicleScreen(navigation: INavigationRouter, id: Long?, viewModel: AddEditVehicleViewModel = getViewModel()) {
    viewModel.vehicleId = id
    var data: AddEditVehicleData by remember {
        mutableStateOf(viewModel.data)
    }

    viewModel.addEditVehicleUIState.value.let {
        when(it) {
            AddEditVehicleUIState.Default -> { }
            AddEditVehicleUIState.VehicleSaved -> {
                LaunchedEffect(it) {
                    navigation.returnBack()
                }
            }
            AddEditVehicleUIState.Loading -> {
                viewModel.initData()
            }
            AddEditVehicleUIState.VehicleChanged -> {
                data = viewModel.data
                viewModel.addEditVehicleUIState.value = AddEditVehicleUIState.Default
            }
        }
    }

    BackArrowScreen(
        topBarTitle = stringResource(id = R.string.title_add_edit_vehicle),
        onBackClick = { navigation.returnBack() }
    ) {
        AddEditVehicleContent(
            actions = viewModel,
            data = data
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditVehicleContent(
    actions: AddEditVehicleViewModel,
    data: AddEditVehicleData
) {
    Text(text = "${Build.VERSION.SDK_INT} -- ${Build.VERSION_CODES.Q}")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CustomTextField(
            value = data.vehicle.name,
            label = stringResource(id = R.string.add_edit_vehicle_name_field),
            onValueChange = { actions.onNameChange(it) },
            error = if(data.vehicleNameError != null) stringResource(id = data.vehicleNameError!!) else ""
        )

        CustomTextField(
            value = data.vehicle.licensePlate,
            label = stringResource(id = R.string.add_edit_vehicle_license_plate_field),
            onValueChange = { actions.onLicensePlateChange(it) },
            error = ""
        )

        CustomTextField(
            value = data.vehicle.vin,
            label = stringResource(id = R.string.add_edit_vehicle_vin_field),
            onValueChange = { actions.onVINChange(it) },
            error = if(data.vehicleVINError != null) stringResource(id = data.vehicleVINError!!) else ""
        )

        val calendar = Calendar.getInstance()
        data.vehicle.motDate?.let {
            calendar.timeInMillis = it
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            LocalContext.current,
            { dialog: DatePicker, year: Int, month: Int, day: Int ->
                actions.onDateChange(DateUtils.getUnixTime(year, month, day))
            },
            year,
            month,
            day
        )

        ReactiveField(
            value = if(data.vehicle.motDate != null) DateUtils.getDateString(data.vehicle.motDate!!) else null,
            label = stringResource(id = R.string.add_edit_vehicle_mot_date_field),
            leadingIcon = R.drawable.ic_event_24,
            onClick = { datePickerDialog.show() },
            onClearClick = { actions.onDateChange(null) }
        )
        
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                readOnly = true,
                value = data.selectedManufacturerName,
                onValueChange = {},
                label = { Text(text = stringResource(id = R.string.add_edit_vehicle_manufacturer_field)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                data.manufacturers.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.name) },
                        onClick = {
                            actions.onManufacturerChange(it)
                            expanded = false
                        }
                    )
                }
            }
        }

        FilePickerDev()

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    actions.saveVehicle()
                },
                //enabled = (actions.isNameValid(fName.value) && actions.isVINValid(fVin.value))
            ) {
                Text(text = stringResource(id = R.string.add_edit_vehicle_save_btn))
            }
        }
    }
}

@Composable
fun FilePickerDev() {
    val context = LocalContext.current
    val result = remember { mutableStateOf<Uri?>(null) }

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    // Android 10+ (API Level 29+), permissions not required
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        result.value = it
        if(it == null) {
            Log.d("[ ################### ]", "Selected URI is null")
        } else {
            var fileContent = context.contentResolver.openInputStream(it)?.readBytes()
            bitmap = fileContent?.let { BitmapFactory.decodeByteArray(fileContent, 0, it.size) }
            Log.d("[ ################### ]", it.toString())
            //Log.d("[ ################### ]", fileContent.toString())
        }
    }

    // Android <10 (API Level <29), permissions required
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            launcher.launch(arrayOf("application/pdf", "image/*"))
        } else {
            Toast.makeText(context, "Oprávnění bylo zamítnuto", Toast.LENGTH_SHORT).show()
        }
    }

    Column() {
        Text(text = stringResource(id = R.string.add_edit_vehicle_file_field))
        Button(onClick = {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                launcher.launch(arrayOf("application/pdf", "image/*"))
            }
        }) {
            Text(text = stringResource(id = R.string.add_edit_vehicle_upload_file))
        }
    }

    var scale by remember { mutableStateOf(1f) }
    val state = rememberTransformableState { zoomChange, _, _ ->
        scale *= zoomChange
    }

    if(bitmap != null) {
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
}