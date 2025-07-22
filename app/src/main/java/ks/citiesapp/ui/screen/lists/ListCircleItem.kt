package ks.citiesapp.ui.screen.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ks.citiesapp.domain.CityList

@Composable
fun ListCircleItem(
    list: CityList,
    isSelected: Boolean,
    size: Dp,
    onClick: () -> Unit
) {
    val borderWidth = if (isSelected) 4.dp else 2.dp
    val scale = if (isSelected) 1.2f else 1.0f

    Box(
        modifier = Modifier
            .size(size)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(CircleShape)
            .background(Color(list.color))
            .border(borderWidth, Color.White, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = list.name,
            color = Color.White,
            style = MaterialTheme.typography.labelMedium
        )
    }
}