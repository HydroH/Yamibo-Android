package com.hydroh.yamibo.ui.navigation

import android.os.Parcelable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackFader
import com.hydroh.yamibo.ui.home.HomeNode
import com.hydroh.yamibo.ui.login.LoginNode
import kotlinx.parcelize.Parcelize

class RootNode(
    buildContext: BuildContext,
    private val backStack: BackStack<NavTarget> = BackStack(
        initialElement = NavTarget.Home,
        savedStateMap = buildContext.savedStateMap
    )
) :
    ParentNode<RootNode.NavTarget>(
        buildContext = buildContext,
        navModel = backStack
    ) {
    sealed class NavTarget : Parcelable {
        @Parcelize
        object Home : NavTarget()

        @Parcelize
        object Login : NavTarget()
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(
            navModel = backStack,
            modifier = Modifier.fillMaxSize(),
            transitionHandler = rememberBackstackFader()
        )
    }

    override fun resolve(navTarget: NavTarget, buildContext: BuildContext) =
        when (navTarget) {
            is NavTarget.Home -> HomeNode(buildContext, backStack)
            is NavTarget.Login -> LoginNode(buildContext, backStack)
        }
}
