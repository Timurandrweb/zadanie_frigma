package com.example.zadanie_frigma.ui.home.viewlogika_region_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


//MainFactory - передатчик this и аргументов (ОН НЕ ИМЕЕТ СМЫСЛА ! ЧИСТАЯ ПЕРЕДАЧА ДАННЫХ)
class MainFactory(val application: Application, val text:String):
    ViewModelProvider.AndroidViewModelFactory(application){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application,text) as T
    }
}