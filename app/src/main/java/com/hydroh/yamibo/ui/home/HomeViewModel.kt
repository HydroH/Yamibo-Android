package com.hydroh.yamibo.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hydroh.yamibo.data.DataProvider
import com.hydroh.yamibo.model.SectionGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var uiState by mutableStateOf(HomeUIState())
        private set

    fun getHomeContent() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                uiState = DataProvider.getHomeData()
            } catch (e: Exception) {
                uiState = uiState.copy(
                    sectionGroups = mutableStateListOf(),
                    avatarUrl = null,
                    exception = e,
                )
            }
        }
    }
}

data class HomeUIState(
    var sectionGroups: SnapshotStateList<SectionGroup> = mutableStateListOf(),
    var avatarUrl: String? = null,
    val exception: Exception? = null,
)