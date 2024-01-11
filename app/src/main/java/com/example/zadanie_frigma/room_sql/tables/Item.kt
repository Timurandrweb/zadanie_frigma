package com.example.zadanie_frigma.room_sql.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.zadanie_frigma.room_sql.tables.Item.Companion.table_name


@Entity (tableName = table_name)
data class Item (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "img")
    var img: String,
    @ColumnInfo(name = "price")
    var price: String
){
    companion object{
        const val table_name = "pizza";//назв
    }
}