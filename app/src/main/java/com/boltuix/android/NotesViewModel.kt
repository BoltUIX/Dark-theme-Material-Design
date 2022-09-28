package com.boltuix.android

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) :
    AndroidViewModel(application) {

    // DataStore
    private val uiDataStore = UIModePreference(application)

    // get UI mode
    val getUIMode = uiDataStore.uiMode

    // save UI mode
    fun saveToDataStore(isNightMode: Boolean) {
        viewModelScope.launch(IO) {
            uiDataStore.saveToDataStore(isNightMode)
        }
    }

}