package com.hydroh.yamibo.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.hydroh.yamibo.ui.component.FoldableSectionGroup
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

    Column(modifier) {
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            uiState.sectionGroups.map {
                item {
                    FoldableSectionGroup(
                        sectionGroup = it,
                        onExpandedChange = { expanded ->
                            it.expanded = !expanded
                        },
                    )
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