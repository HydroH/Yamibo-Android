package com.hydroh.yamibo.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
            verticalArrangement = Arrangement.spacedBy(8.dp),
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
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                            it.sections.forEach {
                                // SectionItem
                                ElevatedCard(
                                    onClick = {},
                                    Modifier.padding(vertical = 8.dp),
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
                                    Text(
                                        text = it.desc,
                                        textAlign = TextAlign.Left,
                                        style = MaterialTheme.typography.bodyMedium,
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

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}