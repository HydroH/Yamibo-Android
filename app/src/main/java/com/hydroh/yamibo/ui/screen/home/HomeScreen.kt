package com.hydroh.yamibo.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.hydroh.yamibo.ui.component.CommonHomeListView
import com.hydroh.yamibo.ui.screen.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator? = null,
    loginRecipient: ResultRecipient<LoginScreenDestination, Boolean>? = null,
    viewModel: HomeViewModel = HomeViewModel(),
) {
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.getCommonHomeContent()
    }
    loginRecipient?.onNavResult { result ->
        when (result) {
            is NavResult.Value -> {
                if (result.value) viewModel.getCommonHomeContent()
            }
            else -> {}
        }
    }

    CommonHomeListView(
        uiState = uiState,
        onRefresh = { viewModel.getCommonHomeContent() },
        onSectionClick = { /*TODO*/ },
        onPostClick = { /*TODO*/ }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}