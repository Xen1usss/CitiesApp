package ks.citiesapp.ui.screen.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ks.citiesapp.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateListScreen(
    onConfirm: (String, String, Int, List<String>) -> Unit,
    onDismiss: () -> Unit,
    availableCities: List<String>
) {
    var shortName by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var selectedCities by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedColor by remember { mutableStateOf(Color.Green.toArgb()) }

    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.error,
        MaterialTheme.colorScheme.surfaceVariant,
        MaterialTheme.colorScheme.outline
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                if (shortName.isNotEmpty() && fullName.isNotEmpty() && selectedCities.isNotEmpty()) {
                    onConfirm(shortName, fullName, selectedColor, selectedCities)
                }
            }) {
                Text(stringResource(R.string.button_ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.button_cancel))
            }
        },
        title = { Text(stringResource(R.string.title_new_list)) },
        text = {
            Column {
                OutlinedTextField(
                    value = shortName,
                    onValueChange = { shortName = it },
                    label = { Text(stringResource(R.string.hint_short_name)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text(stringResource(R.string.hint_full_name)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))

                Text(stringResource(R.string.label_select_cities))
                LazyColumn(modifier = Modifier.height(120.dp)) {
                    items(availableCities) { city ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = selectedCities.contains(city),
                                enabled = !selectedCities.contains(city) || selectedCities.size > 1,
                                onCheckedChange = { checked ->
                                    val newSelection = if (checked) {
                                        if (selectedCities.size < 5) selectedCities + city else selectedCities
                                    } else {
                                        selectedCities - city
                                    }
                                    selectedCities = newSelection
                                }
                            )
                            Text(city)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
                Text(stringResource(R.string.label_select_color))
                FlowRow {
                    colors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color)
                                .clickable { selectedColor = color.toArgb() }
                                .border(
                                    width = if (selectedColor == color.toArgb()) 3.dp else 1.dp,
                                    color = Color.Black,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    )
}