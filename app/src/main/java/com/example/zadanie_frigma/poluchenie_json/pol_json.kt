package com.example.zadanie_frigma.poluchenie_json

import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.sql.DriverManager


class pol_json: LiveData<String>() {


    val url = "https://gist.githubusercontent.com/Archakov06/0421a3edb0ec6f4fc907a6fed3eb5433/raw/7ad89e3b5061b51255e04401b97a3ce226464d7e/pizza.json"
    lateinit var doc: Document

    override fun onActive() {
        super.onActive()
        DriverManager.println("onActive")
        //активна activity
        //connect db
    }

    override fun onInactive() {
        super.onInactive()
        DriverManager.println("onInactive")
        //не активна activity
    }

    fun sendServer() {
        val dispose = insertt()
            .subscribeOn(Schedulers.newThread())//тип потока (планировщик)
            //Schedulers.newThread() новый поток
            //Schedulers.io() работа с файлами
            //Schedulers.computation() вычисление
            //Schedulers.single() очередь - первый пришло первый ушёл

            .observeOn(AndroidSchedulers.mainThread())//если ставим эту фигню, то всё что в subscribe(функция ниже)
            // будет выполняться в главном потоке, кроме функции dataSource и появляется доступ view

            .subscribe({
                //тело потока
                postValue(it.toString())//возврат донных

            },{
                //Log.e("erd","Ошибка = ${it.localizedMessage}")
                postValue("error")
            })

    }




    fun insertt(): Maybe<String> {
        return Maybe.create ({ subscriber->

            doc = Jsoup.connect(url)
                .cookie("q", "q")
                .get()
            Log.e("swi",doc.text())

            val dodd: String = doc.text()
            subscriber.onSuccess(dodd)
            subscriber.onComplete()
        })
    }



}