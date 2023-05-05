package cz.martinweiss.garager.ui.elements

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownField(
    value: String,
    label: String,
    error: String = "",
    expanded: Boolean,
    onExpandedChange: () -> Unit,
    onDismissRequest: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit)
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange() }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = value,
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            isError = error.isNotEmpty(),
            supportingText = { if (error.isNotEmpty()) Text(text = error) }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismissRequest() }
        ) {
            content()
        }
    }
}
