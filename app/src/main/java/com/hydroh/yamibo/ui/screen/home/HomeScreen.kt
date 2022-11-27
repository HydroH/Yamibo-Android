package com.hydroh.yamibo.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.hydroh.yamibo.ui.component.ExpandableColumn
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = HomeViewModel(),
) {
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.getHomeContent()
    }

    val pullRefreshState = rememberPullRefreshState(
        uiState.homeState == HomeState.LOADING,
        { viewModel.getHomeContent() })

    Surface {
        Box(Modifier.pullRefresh(pullRefreshState)) {
            LazyColumn(
                modifier = modifier.fillMaxWidth(),
            ) {
                uiState.sectionGroups.map {
                    item {
                        ExpandableColumn(
                            label = it.name,
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
                                    // SectionItem
                                    ElevatedCard(
                                        onClick = {},
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(12.dp)
                                        ) {
                                            Text(
                                                text = it.name,
                                                textAlign = TextAlign.Left,
                                                style = MaterialTheme.typography.titleMedium,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .placeholder(
                                                        visible = uiState.homeState == HomeState.LOADING,
                                                        highlight = PlaceholderHighlight.fade(),
                                                    )
                                            )
                                            Spacer(Modifier.height(12.dp))
                                            Text(
                                                text = it.desc,
                                                textAlign = TextAlign.Left,
                                                style = MaterialTheme.typography.bodyLarge,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .placeholder(
                                                        visible = uiState.homeState == HomeState.LOADING,
                                                        highlight = PlaceholderHighlight.fade(),
                                                    )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = uiState.homeState == HomeState.LOADING,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}