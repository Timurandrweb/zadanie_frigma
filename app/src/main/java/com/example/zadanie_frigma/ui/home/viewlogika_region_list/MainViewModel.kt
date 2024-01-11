package com.example.zadanie_frigma.ui.home.viewlogika_region_list

import android.R
import android.app.Application
import android.widget.ArrayAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class MainViewModel(application: Application, val text: String): AndroidViewModel(application) {//c аргументом

    val liveData = MutableLiveData<ArrayAdapter<String>>()

    init {
        //создания списка городов
        spisok_regions(application)
    }

    private fun spisok_regions(application: Application) {
        var countries = arrayOf("Москва", "Чебоксары", "Казань", "Нижний Новгород", "Самара")
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(application, R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        liveData.value = adapter

        //Toast.makeText(getApplication(),"$text", Toast.LENGTH_LONG).show()
    }

}