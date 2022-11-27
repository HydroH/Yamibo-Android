package com.hydroh.yamibo.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Section(
    val title: String = "",
    val desc: String = "",
    val url: String = "",
    val newCount: String = "",
    val postCount: String = "",
    val replyCount: String = "",
)

class SectionGroup(
    val title: String = "",
    val sections: List<Section> = arrayListOf(),
    val initialExpanded: Boolean = true,
) {
    var expanded by mutableStateOf(initialExpanded)
}
