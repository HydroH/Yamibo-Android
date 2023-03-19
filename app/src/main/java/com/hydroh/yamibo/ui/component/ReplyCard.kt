package com.hydroh.yamibo.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.hydroh.yamibo.data.network.HttpSingleton
import com.hydroh.yamibo.model.Reply
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun ReplyCard(
    reply: Reply,
    modifier: Modifier = Modifier,
    placeHolder: Boolean = false
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = reply.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .placeholder(
                        visible = placeHolder,
                        highlight = PlaceholderHighlight.fade(),
                    ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = reply.author,
                modifier = Modifier.placeholder(
                    visible = placeHolder,
                    highlight = PlaceholderHighlight.fade(),
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = reply.time,
                modifier = Modifier.placeholder(
                    visible = placeHolder,
                    highlight = PlaceholderHighlight.fade(),
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            MarkdownText(
                markdown = reply.text,
                imageLoader = HttpSingleton.imageLoader,
                modifier = Modifier.placeholder(
                    visible = placeHolder,
                    highlight = PlaceholderHighlight.fade(),
                )
            )
        }
    }
}