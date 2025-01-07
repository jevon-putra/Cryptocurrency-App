package com.jop.cointracker.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jop.cointracker.data.model.Tag
import com.jop.cointracker.ui.theme.CoinTrackerTheme
import com.jop.cointracker.ui.theme.PADDING_HALF
import com.jop.cointracker.ui.theme.PADDING_MAIN
import com.jop.cointracker.ui.theme.surfaceLight
import com.jop.cointracker.util.shimmerBackground

@Composable
fun ChipTag(modifier: Modifier = Modifier, tag: Tag, isSelected: Boolean = false, onSelectionChanged: (Tag) -> Unit){
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(PADDING_MAIN))
            .border(
                width = 1.dp,
                color = if(isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceContainerHighest,
                shape = RoundedCornerShape(PADDING_MAIN)
            )
            .toggleable(
                value = isSelected,
                onValueChange = { onSelectionChanged(tag) }
            )
            .background(if(isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
            .padding(horizontal = PADDING_MAIN, vertical = PADDING_HALF),
        text = tag.title,
        style = MaterialTheme.typography.bodySmall,
        color = if(isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
    )
}

@Composable
fun ChipTagLoading(modifier: Modifier = Modifier, title: String){
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(PADDING_MAIN))
            .background(MaterialTheme.colorScheme.outlineVariant)
            .shimmerBackground()
            .padding(horizontal = PADDING_MAIN, vertical = PADDING_HALF),
        text = title,
        style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.outlineVariant
        ),
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewChipTag(){
    CoinTrackerTheme {
        ChipTagLoading(title = "Gello")
    }
}