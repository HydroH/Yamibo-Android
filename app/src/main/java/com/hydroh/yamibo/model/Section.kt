package com.hydroh.yamibo.model

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

data class Section(
    val name: String,
    val newCount: String,
    val desc: String,
    val postCount: String,
    val replyCount: String,
): IListItem {
    @Composable
    override fun ListItem() {
        Row {
            Column {
                Text(text = name)
                Text(text = desc)
            }
        }
    }
}

@Preview
@Composable
fun SectionItemPreview() {
    Section(
        name = "动漫区",
        newCount = "123",
        desc = "测试测试测试",
        postCount = "2万",
        replyCount = "6万"
    ).ListItem()
}