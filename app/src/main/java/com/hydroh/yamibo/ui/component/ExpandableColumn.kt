package com.hydroh.yamibo.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableColumn(
    label: String,
    modifier: Modifier = Modifier,
    initialExpanded: Boolean = true,
    expanded: Boolean = true,
    onExpandedChange: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onExpandedChange(expanded)
            },
    ) {
        Text(
            text = label,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(
            initiallyVisible = initialExpanded,
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically(),
        ) {
            content()
        }
    }
}
