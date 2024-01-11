package com.example.zadanie_frigma.room_sql

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.zadanie_frigma.room_sql.tables.Item
import com.example.zadanie_frigma.room_sql.tables.Item.Companion.table_name
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertItem(item: Item)

    @Query("SELECT * FROM pizza")
    fun getAllItem(): Flow<List<Item>>

    @Query("DELETE FROM pizza")
    fun deleteQ()

    @Query("UPDATE ${table_name} SET price = :pra")
    fun updateQwrea(pra:String)
}