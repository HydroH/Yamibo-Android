package com.hydroh.yamibo.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Section(
    val name: String,
    val newCount: String,
    val desc: String,
    val url: String,
    val postCount: String,
    val replyCount: String,
)

class SectionGroup(
    val name: String,
    val sections: List<Section>,
    val initialExpanded: Boolean,
) {
    var expanded by mutableStateOf(initialExpanded)
}
