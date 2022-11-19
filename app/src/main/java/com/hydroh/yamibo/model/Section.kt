package com.hydroh.yamibo.model

data class Section(
    val name: String,
    val newCount: String,
    val desc: String,
    val postCount: String,
    val replyCount: String,
)

data class SectionGroup(
    val name: String,
    val SectionList: List<Section>,
    var expanded: Boolean,
)
