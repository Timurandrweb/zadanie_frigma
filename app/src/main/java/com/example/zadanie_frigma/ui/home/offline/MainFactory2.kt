package com.example.zadanie_frigma.ui.home.offline

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zadanie_frigma.ui.home.viewlogika_region_list.MainViewModel


//MainFactory - передатчик this и аргументов (ОН НЕ ИМЕЕТ СМЫСЛА ! ЧИСТАЯ ПЕРЕДАЧА ДАННЫХ)
class MainFactory2(val application: Application, val text:String):
    ViewModelProvider.AndroidViewModelFactory(application){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MViewModel_insert(application,text) as T
    }
}