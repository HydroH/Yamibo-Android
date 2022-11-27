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

    fun getCommonHomeContent() {
        if (uiState.commonHomeState != CommonHomeState.BEFORE) {
            uiState = uiState.copy(commonHomeState = CommonHomeState.LOADING)
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                uiState = DataProvider.getCommonHomeData()
                uiState = uiState.copy(commonHomeState = CommonHomeState.SUCCESS)
            } catch (e: Exception) {
                uiState = uiState.copy(
                    sectionGroups = mutableStateListOf(),
                    topPosts = mutableStateListOf(),
                    posts = mutableStateListOf(),
                    avatarUrl = null,
                    exception = e,
                    commonHomeState = CommonHomeState.FAIL,
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
    val commonHomeState: CommonHomeState = CommonHomeState.BEFORE,
    val exception: Exception? = null,
)

enum class CommonHomeState {
    BEFORE,
    LOADING,
    SUCCESS,
    FAIL,
}