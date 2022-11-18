package com.hydroh.yamibo.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.hydroh.yamibo.model.IListItem
import com.hydroh.yamibo.network.parseListHtml

@Composable
fun HomeScreen() {
    var listItems by remember { mutableStateOf(listOf<IListItem>()) }

    LaunchedEffect(Unit) {
        listItems = parseListHtml()
    }

    if (listItems.isNotEmpty()) {
        HomeListContent(listItems)
    } else {
        TODO()
    }
}

@Composable
fun HomeListContent(listItems: List<IListItem>) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        listItems.map { item { it.ListItem() } }
    }
}
