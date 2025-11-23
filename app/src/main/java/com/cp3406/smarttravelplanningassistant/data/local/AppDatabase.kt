package com.cp3406.smarttravelplanningassistant.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cp3406.smarttravelplanningassistant.data.local.dao.ExpenseDao
import com.cp3406.smarttravelplanningassistant.data.local.dao.ItineraryDao
import com.cp3406.smarttravelplanningassistant.data.local.dao.TripDao
import com.cp3406.smarttravelplanningassistant.data.local.entity.Expense
import com.cp3406.smarttravelplanningassistant.data.local.entity.ItineraryItem
import com.cp3406.smarttravelplanningassistant.data.local.entity.Trip

@Database(
    entities = [
        Trip::class,
        ItineraryItem::class,
        Expense::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tripDao(): TripDao
    abstract fun itineraryDao(): ItineraryDao
    abstract fun expenseDao(): ExpenseDao
}
