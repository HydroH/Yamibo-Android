package com.hydroh.yamibo.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.hydroh.yamibo.ui.component.ExpandableColumn
import com.hydroh.yamibo.ui.navigation.RootNode

class HomeNode(
    buildContext: BuildContext,
    private val backStack: BackStack<RootNode.NavTarget>,
    private val viewModel: HomeViewModel = HomeViewModel(),
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        HomeScreen(
            modifier = modifier,
            backStack = backStack,
            viewModel = viewModel,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    backStack: BackStack<RootNode.NavTarget>? = null,
    viewModel: HomeViewModel = HomeViewModel(),
) {
    val uiState = viewModel.uiState
    LaunchedEffect(Unit) {
        viewModel.getHomeContent()
    }

    Surface {
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
                                            style = MaterialTheme.typography.titleLarge,
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
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}