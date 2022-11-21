package com.hydroh.yamibo.ui.home

import androidx.compose.runtime.*
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
                uiState.sectionGroups = DataProvider.getHomeItemList().toMutableStateList()
            } catch (e: Exception) {
                TODO()
            }
        }
    }
}

data class HomeUIState(
    var sectionGroups: SnapshotStateList<SectionGroup> = mutableStateListOf()
)