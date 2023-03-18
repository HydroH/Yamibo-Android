package com.hydroh.yamibo.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hydroh.yamibo.data.DataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class CommonHomeViewModel : ViewModel() {
    var uiState by mutableStateOf(
        CommonHomeUIState(
            // Placeholder
            sectionGroups = mutableStateListOf(
                SectionGroup(sections = arrayListOf(Section())),
                SectionGroup(sections = arrayListOf(Section(), Section())),
            )
        )
    )
        private set

    fun getCommonHomeContent(url: String? = null) {
        if (uiState.commonHomeState != CommonLoadState.BEFORE) {
            uiState = uiState.copy(commonHomeState = CommonLoadState.LOADING)
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                uiState = DataProvider.getCommonHomeData(url)
                uiState = uiState.copy(commonHomeState = CommonLoadState.SUCCESS)
            } catch (e: Exception) {
                uiState = uiState.copy(
                    sectionGroups = mutableStateListOf(),
                    topPosts = mutableStateListOf(),
                    posts = mutableStateListOf(),
                    avatarUrl = null,
                    exception = e,
                    commonHomeState = CommonLoadState.FAIL,
                )
            }
        }
    }
}

data class CommonHomeUIState (
    var sectionGroups: SnapshotStateList<SectionGroup> = mutableStateListOf(),
    var topPosts: SnapshotStateList<Post> = mutableStateListOf(),
    var posts: SnapshotStateList<Post> = mutableStateListOf(),
    var avatarUrl: String? = null,
    val commonHomeState: CommonLoadState = CommonLoadState.BEFORE,
    val exception: Exception? = null,
)

enum class CommonLoadState {
    BEFORE,
    LOADING,
    SUCCESS,
    FAIL,
}