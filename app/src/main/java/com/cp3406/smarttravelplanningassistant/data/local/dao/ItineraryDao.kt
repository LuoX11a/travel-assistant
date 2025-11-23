package com.cp3406.smarttravelplanningassistant.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cp3406.smarttravelplanningassistant.data.local.entity.ItineraryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ItineraryDao {

    @Query("SELECT * FROM itinerary_items WHERE tripId = :tripId ORDER BY date, time")
    fun getItemsForTrip(tripId: Long): Flow<List<ItineraryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItineraryItem)
}
