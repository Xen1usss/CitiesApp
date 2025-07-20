package ks.citiesapp.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

@Composable
fun CircleIcon(
    color: Color = MaterialTheme.colorScheme.primary,
    size: Int = 24
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .background(color, shape = CircleShape)
    )
}