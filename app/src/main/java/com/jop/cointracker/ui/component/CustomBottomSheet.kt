package com.jop.cointracker.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.jop.cointracker.data.model.Tag
import com.jop.cointracker.data.model.getAllTags
import com.jop.cointracker.ui.theme.PADDING_HALF
import com.jop.cointracker.ui.theme.PADDING_MAIN

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
fun FilterBottomSheet(state: SheetState, showBottomSheet: MutableState<Boolean>,
                      stateSelectedTags: SnapshotStateList<Tag>, confirmOrClearAction: (Boolean) -> Unit
){
    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = {
            showBottomSheet.value = false
        }
    ) {
        Column(
            modifier = Modifier.padding(bottom = PADDING_MAIN),
            verticalArrangement = Arrangement.spacedBy(PADDING_MAIN)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Filter",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            HorizontalDivider()
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(PADDING_HALF),
                verticalArrangement = Arrangement.spacedBy(PADDING_HALF),
                modifier = Modifier.padding(horizontal = PADDING_MAIN)
            ) {
                getAllTags().forEach { tag ->
                    ChipTag(tag = tag, isSelected = stateSelectedTags.contains(tag)) {
                        if(stateSelectedTags.contains(tag)){
                            stateSelectedTags.remove(tag)
                        } else {
                            stateSelectedTags.add(tag)
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.padding(horizontal = PADDING_MAIN).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(PADDING_MAIN)
            ) {
                CustomButtonOutlinePrimary(
                    modifier = Modifier.weight(1f),
                    text = "Reset Filter"
                ) {
                    showBottomSheet.value = false
                    confirmOrClearAction(false)
                }
                CustomButtonPrimary(
                    modifier = Modifier.weight(1f),
                    text = "Apply Filter"
                ) {
                    showBottomSheet.value = false
                    confirmOrClearAction(true)
                }
            }
        }
    }
}