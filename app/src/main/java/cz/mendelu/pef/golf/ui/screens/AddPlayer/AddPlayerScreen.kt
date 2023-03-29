package cz.mendelu.pef.golf.ui.screens.AddPlayer

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.KeyboardType
import cz.mendelu.pef.golf.navigation.INavigationRouter
import cz.mendelu.pef.golf.ui.elements.BackArrowScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun AddPlayerScreen(navigation: INavigationRouter, viewModel: AddPlayerViewModel = getViewModel()) {
    viewModel.addPlayerUIState.value.let {
        when(it) {
            AddPlayerUIState.Default -> {

            }
            AddPlayerUIState.PlayerSaved -> {
                LaunchedEffect(it) {
                    navigation.returnBack()
                }
            }
        }
    }
    
    BackArrowScreen(appBarTitle = "Add item", onBackClick = { navigation.returnBack() }) {
        AddPlayerScreenContent(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlayerScreenContent(actions: AddPlayerActions) {
    val name = remember {
        mutableStateOf("")
    }

    val score = remember {
        mutableStateOf("")
    }

    OutlinedTextField(value = name.value, onValueChange = {
        name.value = it
    })

    OutlinedTextField(value = score.value, onValueChange = {
        score.value = it
    }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
    
    OutlinedButton(
        onClick = { actions.savePlayer(name = name.value, score = score.value.toInt()) },
        enabled = ValidateValue(score.value)) {
        Text(text = "Save")
    }
}

fun ValidateValue(value: String): Boolean {
    return try {
        value.toInt()
        true
    } catch (e: Exception) {
        false
    }
}
