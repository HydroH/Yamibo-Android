package com.hydroh.yamibo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hydroh.yamibo.data.DataProvider
import com.hydroh.yamibo.model.SectionGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState(arrayListOf()))
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    fun getHomeContent() {
        _uiState.apply {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    value = value.copy(sectionGroups = DataProvider.getHomeItemList())
                } catch (e: Exception) {
                    TODO()
                }
            }
        }
    }
}

data class HomeUIState(
    val sectionGroups: ArrayList<SectionGroup>
)