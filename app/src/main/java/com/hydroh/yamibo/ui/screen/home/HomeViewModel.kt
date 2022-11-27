package com.hydroh.yamibo.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hydroh.yamibo.data.DataProvider
import com.hydroh.yamibo.model.Section
import com.hydroh.yamibo.model.SectionGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var uiState by mutableStateOf(
        HomeUIState(
            // Placeholder display
            sectionGroups = mutableStateListOf(
                SectionGroup(sections = arrayListOf(Section())),
                SectionGroup(sections = arrayListOf(Section(), Section())),
            )
        )
    )
        private set

    fun getHomeContent() {
        uiState = uiState.copy(homeState = HomeState.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                uiState = DataProvider.getHomeData()
                uiState = uiState.copy(homeState = HomeState.SUCCESS)
            } catch (e: Exception) {
                uiState = uiState.copy(
                    sectionGroups = mutableStateListOf(),
                    avatarUrl = null,
                    exception = e,
                    homeState = HomeState.FAIL,
                )
            }
        }
    }
}

data class HomeUIState(
    var sectionGroups: SnapshotStateList<SectionGroup> = mutableStateListOf(),
    var avatarUrl: String? = null,
    val homeState: HomeState = HomeState.BEFORE,
    val exception: Exception? = null,
)

enum class HomeState {
    BEFORE,
    LOADING,
    SUCCESS,
    FAIL,
}