package com.example.zadanie_frigma.ui.home.offline

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.zadanie_frigma.room_sql.tables.Item
import com.example.zadanie_frigma.room_sql.MainDb
import kotlinx.coroutines.*

import org.json.JSONArray
import org.json.JSONTokener
import java.lang.Thread.sleep


//MainFactory - передатчик this и аргументов (ОН НЕ ИМЕЕТ СМЫСЛА ! ЧИСТАЯ ПЕРЕДАЧА ДАННЫХ)
//Тут его даже нет!


//application - это контекст или this
class MViewModel_insert(application: Application, val json:String): AndroidViewModel(application) {//c аргументом




    //это типа обмениека между MainActivity и этим классом
    val liveData = MutableLiveData<String>()
    /*
    К liveData МОЖНО ТАК ОБРАЩАТЬСЯ В MainActivity
    mViewModel.liveData.observe(this, Observer {
            textView.text = it
        })
     */



    //init - запускается как только создался экземпляр данного класса
    init {
        readjson(json,application)
    }

    private fun readjson(json:String, application: Application) {

        val jsonArray = JSONTokener(json).nextValue() as JSONArray
        for (i in 0 until jsonArray.length()) {

            var imageUrl:String? = null
            var title:String? = null
            var price:String? = null
            // ID
            //val id = jsonArray.getJSONObject(i).getString("id")

            imageUrl = jsonArray.getJSONObject(i).getString("imageUrl")
            Log.i("fdd", "imageUrl: " + imageUrl)

            title = jsonArray.getJSONObject(i).getString("title")
            Log.i("fdd", "title: " + title)

            price = jsonArray.getJSONObject(i).getString("price")
            Log.i("fdd", price)

            if (imageUrl != null && title != null && price != null){
                insertDans(title,imageUrl,price,application)
            }

        }
    }

    private fun insertDans(title: String, img:String, price:String, application: Application) {
        val db = MainDb.getDb(application)
        val item = Item(null,
            title,
            img,
            price
        )

        GlobalScope.launch (Dispatchers.IO) {
            db.getDao().deleteQ()//удаляем старые данные
            delay(900L)//даем время на удаление(ЭТО ВРЕМЯ НА ЗАКРЫТИЕ И ОТКЫТИЕ ТРАНЗАКЦИИ)
            // (не лучший способ использовать, но работат корректнее .await())
            db.getDao().insertItem(item)//добавляем данные
        }



    }





}