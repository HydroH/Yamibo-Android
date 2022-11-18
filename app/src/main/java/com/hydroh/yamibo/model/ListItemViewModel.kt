package com.hydroh.yamibo.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListItemViewModel: ViewModel() {
    val listItems: MutableLiveData<ArrayList<IListItem>> by lazy {
        MutableLiveData<ArrayList<IListItem>>()
    }
}
