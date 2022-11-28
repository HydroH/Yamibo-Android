package com.hydroh.yamibo.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hydroh.yamibo.model.CommonHomeState
import com.hydroh.yamibo.model.CommonHomeUIState
import com.hydroh.yamibo.model.Post
import com.hydroh.yamibo.model.Section

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommonHomeListView(
    uiState: CommonHomeUIState,
    onRefresh: () -> Unit,
    onSectionClick: (Section) -> Unit,
    onPostClick: (Post) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullRefreshState = rememberPullRefreshState(
        uiState.commonHomeState == CommonHomeState.BEFORE || uiState.commonHomeState == CommonHomeState.LOADING,
        { onRefresh() })

    Surface(modifier) {
        Box(Modifier.pullRefresh(pullRefreshState)) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                uiState.sectionGroups.map {
                    item {
                        ExpandableColumn(
                            label = it.title,
                            initialExpanded = it.initialExpanded,
                            expanded = it.expanded,
                            onExpandedChange = { expanded ->
                                it.expanded = !expanded
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                it.sections.forEach {
                                    SectionCard(
                                        section = it,
                                        onClick = { onSectionClick(it) },
                                        placeHolder = uiState.commonHomeState == CommonHomeState.BEFORE,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = uiState.commonHomeState == CommonHomeState.BEFORE || uiState.commonHomeState == CommonHomeState.LOADING,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}