package com.hydroh.yamibo.ui.screen.thread

import androidx.compose.runtime.Composable
import com.hydroh.yamibo.ui.screen.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient

@Destination
@Composable
fun ThreadScreen(
    url: String,
    navigator: DestinationsNavigator? = null,
    loginRecipient: ResultRecipient<LoginScreenDestination, Boolean>? = null,
    viewModel: ThreadViewModel = ThreadViewModel(),
) {

}