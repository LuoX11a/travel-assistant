package com.cp3406.smarttravelplanningassistant.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cp3406.smarttravelplanningassistant.data.local.entity.Expense
import kotlinx.coroutines.flow.Flow

data class ExpenseSummary(
    val category: String,
    val total: Double
)

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses WHERE tripId = :tripId")
    fun getExpensesForTrip(tripId: Long): Flow<List<Expense>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Query(
        "SELECT category AS category, SUM(amount) AS total " +
                "FROM expenses WHERE tripId = :tripId GROUP BY category"
    )
    fun getExpenseSummary(tripId: Long): Flow<List<ExpenseSummary>>
}
