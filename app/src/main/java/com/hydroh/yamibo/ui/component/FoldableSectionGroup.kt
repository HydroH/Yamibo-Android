package com.hydroh.yamibo.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hydroh.yamibo.model.SectionGroup

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FoldableSectionGroup(
    sectionGroup: SectionGroup,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.clickable {
            onExpandedChange(sectionGroup.expanded)
        },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = sectionGroup.name)
        AnimatedVisibility(
            initiallyVisible = sectionGroup.initialExpanded,
            visible = sectionGroup.expanded,
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
