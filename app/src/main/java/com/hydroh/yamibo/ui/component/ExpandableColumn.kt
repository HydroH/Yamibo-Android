package com.hydroh.yamibo.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hydroh.yamibo.R

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
    val rotationState by animateFloatAsState(if (expanded) 180f else 0f)

    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
                .clickable {
                    onExpandedChange(expanded)
                }
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Text(
                text = label,
                textAlign = TextAlign.Left,
            )
            Icon(
                imageVector = Icons.Filled.ExpandLess,
                contentDescription = stringResource(
                    id = if (expanded) R.string.retract else R.string.expand
                ),
                modifier = Modifier.rotate(rotationState)
            )
        }
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
