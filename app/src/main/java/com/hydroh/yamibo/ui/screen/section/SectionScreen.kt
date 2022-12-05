package com.hydroh.yamibo.ui.screen.section

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.hydroh.yamibo.ui.component.CommonHomeListView
import com.hydroh.yamibo.ui.screen.destinations.LoginScreenDestination
import com.hydroh.yamibo.ui.screen.destinations.SectionScreenDestination
import com.hydroh.yamibo.ui.screen.destinations.ThreadScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient


@Destination
@Composable
fun SectionScreen(
    url: String,
    navigator: DestinationsNavigator? = null,
    loginRecipient: ResultRecipient<LoginScreenDestination, Boolean>? = null,
    viewModel: SectionViewModel = SectionViewModel(),
) {
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.getCommonHomeContent(url)
    }
    loginRecipient?.onNavResult { result ->
        when (result) {
            is NavResult.Value -> {
                if (result.value) viewModel.getCommonHomeContent(url)
            }
            else -> {}
        }
    }

    CommonHomeListView(
        uiState = uiState,
        onRefresh = { viewModel.getCommonHomeContent(url) },
        onSectionClick = { navigator?.navigate(SectionScreenDestination(it.url)) },
        onPostClick = { navigator?.navigate(ThreadScreenDestination(it.url)) }
    )
}

@Preview
@Composable
fun SectionScreenPreview() {
    SectionScreen(url = "")
}