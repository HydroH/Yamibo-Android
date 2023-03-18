package com.hydroh.yamibo.ui.screen.thread

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hydroh.yamibo.data.DataProvider
import com.hydroh.yamibo.model.CommonLoadState
import com.hydroh.yamibo.model.Reply
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThreadViewModel : ViewModel() {
    var uiState by mutableStateOf(
        ThreadUIState(
            replies = mutableStateListOf(Reply())
        )
    )
        private set

    fun getThreadContent(url: String) {
        if (uiState.threadState != CommonLoadState.BEFORE) {
            uiState = uiState.copy(threadState = CommonLoadState.LOADING)
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                uiState = DataProvider.getThreadData(url)
                uiState = uiState.copy(threadState = CommonLoadState.SUCCESS)
            } catch (e: Exception) {
                uiState = uiState.copy(
                    replies = mutableStateListOf(),
                    exception = e,
                    threadState = CommonLoadState.FAIL,
                )
            }
        }
    }
}

data class ThreadUIState(
    var replies: SnapshotStateList<Reply> = mutableStateListOf(),
    var threadState: CommonLoadState = CommonLoadState.BEFORE,
    val exception: Exception? = null,
)