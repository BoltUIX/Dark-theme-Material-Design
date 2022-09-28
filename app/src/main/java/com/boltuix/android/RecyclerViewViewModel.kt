package com.boltuix.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.boltuix.android.RecyclerViewModel

class RecyclerViewViewModel : ViewModel() {
    val liveNewsData = MutableLiveData<ArrayList<RecyclerViewModel>>()
    private var recyclerItemModels  =   ArrayList<RecyclerViewModel>()

    init {
        fetchSampleData()
    }
     private fun fetchSampleData(){
        recyclerItemModels.clear()
      /*  recyclerItemModels.add(RecyclerViewModel("Recycler View","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.."))
        recyclerItemModels.add(RecyclerViewModel("Card View","Lorem ipsum dolor sit amet."))
        recyclerItemModels.add(RecyclerViewModel("Dialog","Lorem ipsum dolor sit amet."))
        recyclerItemModels.add(RecyclerViewModel("Snackbar","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor."))
        recyclerItemModels.add(RecyclerViewModel("App Intro","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor."))
        recyclerItemModels.add(RecyclerViewModel("Permission","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor."))
        recyclerItemModels.add(RecyclerViewModel("Button","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor."))
*/        liveNewsData.value = recyclerItemModels
    }
}