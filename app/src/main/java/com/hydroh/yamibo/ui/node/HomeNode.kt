package com.hydroh.yamibo.ui.node

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.hydroh.yamibo.model.IListItem
import com.hydroh.yamibo.network.parseListHtml

class HomeNode(
    buildContext: BuildContext,
    private val navToLogin: () -> Unit
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        var listItems by remember { mutableStateOf(listOf<IListItem>()) }

        LaunchedEffect(Unit) {
            listItems = parseListHtml()
        }

        if (listItems.isNotEmpty()) {
            HomeListContent(listItems)
        } else {
            Text("TODO")
        }
    }

    @Composable
    fun HomeListContent(listItems: List<IListItem>) {
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            listItems.map { item { it.ListItem() } }
        }
    }
}
