package cz.martinweiss.garager.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    error: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        label = { Text(text = label) },
        onValueChange = onValueChange,
        maxLines = maxLines,
        isError = !error.isEmpty(),
        modifier = Modifier.fillMaxWidth(),
        supportingText = { if (error.isNotEmpty()) Text(text = error) },
        keyboardOptions = keyboardOptions
    )
}