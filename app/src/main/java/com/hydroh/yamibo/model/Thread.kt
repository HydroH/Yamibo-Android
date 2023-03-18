package com.hydroh.yamibo.model

data class Thread(
    val title: String = "",
    val author: String = "",
    val url: String = "",
    val tag: String = "",
    val postTime: String = "",
    val replyCount: String = "",
    val replies: List<Reply> = arrayListOf(),
)

data class Reply(
    val author: String = "",
    val avatarUrl: String = "",
    val text: String = "",
    val time: String = "",
)