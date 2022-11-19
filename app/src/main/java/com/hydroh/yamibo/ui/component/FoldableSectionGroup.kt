package com.hydroh.yamibo.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hydroh.yamibo.model.SectionGroup

@Composable
fun FoldableSectionGroup(
    sectionGroup: SectionGroup,
    expanded: Boolean,
    onClick: () -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = sectionGroup.name)
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically(),
        ) {
            sectionGroup.SectionList.map {
                // SectionItem
                Card() {
                    Text(text = it.name)
                    Text(text = it.desc)
                }
            }
        }
    }
}
