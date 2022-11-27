package com.hydroh.yamibo.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.hydroh.yamibo.model.Section

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionCard(
    section: Section,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeHolder: Boolean = false,
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = section.title,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(
                        visible = placeHolder,
                        highlight = PlaceholderHighlight.fade(),
                    )
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = section.desc,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(
                        visible = placeHolder,
                        highlight = PlaceholderHighlight.fade(),
                    )
            )
        }
    }
}