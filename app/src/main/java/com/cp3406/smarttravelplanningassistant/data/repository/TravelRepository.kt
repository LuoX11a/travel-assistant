package com.cp3406.smarttravelplanningassistant.data.repository

import com.cp3406.smarttravelplanningassistant.data.local.dao.ExpenseDao
import com.cp3406.smarttravelplanningassistant.data.local.dao.ExpenseSummary
import com.cp3406.smarttravelplanningassistant.data.local.dao.ItineraryDao
import com.cp3406.smarttravelplanningassistant.data.local.dao.TripDao
import com.cp3406.smarttravelplanningassistant.data.local.entity.Expense
import com.cp3406.smarttravelplanningassistant.data.local.entity.ItineraryItem
import com.cp3406.smarttravelplanningassistant.data.local.entity.Trip
import com.cp3406.smarttravelplanningassistant.data.remote.RestCountry
import com.cp3406.smarttravelplanningassistant.data.remote.TravelApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class DestinationInfo(
    val country: String,
    val capital: String,
    val region: String,
    val population: Long,
    val flagUrl: String
)

interface TravelRepository {
    fun getTrips(): Flow<List<Trip>>
    suspend fun addTrip(trip: Trip)

    fun getItinerary(tripId: Long): Flow<List<ItineraryItem>>
    suspend fun addItineraryItem(item: ItineraryItem)

    fun getExpenses(tripId: Long): Flow<List<Expense>>
    fun getExpenseSummary(tripId: Long): Flow<List<ExpenseSummary>>
    suspend fun addExpense(expense: Expense)

    suspend fun fetchDestinationInfo(country: String): DestinationInfo?
}

class TravelRepositoryImpl(
    private val tripDao: TripDao,
    private val itineraryDao: ItineraryDao,
    private val expenseDao: ExpenseDao,
    private val api: TravelApiService
) : TravelRepository {

    override fun getTrips(): Flow<List<Trip>> = tripDao.getAllTrips()

    override suspend fun addTrip(trip: Trip) {
        tripDao.insertTrip(trip)
    }

    override fun getItinerary(tripId: Long): Flow<List<ItineraryItem>> =
        if (tripId <= 0) flowOf(emptyList())
        else itineraryDao.getItemsForTrip(tripId)

    override suspend fun addItineraryItem(item: ItineraryItem) {
        itineraryDao.insertItem(item)
    }

    override fun getExpenses(tripId: Long): Flow<List<Expense>> =
        if (tripId <= 0) flowOf(emptyList())
        else expenseDao.getExpensesForTrip(tripId)

    override fun getExpenseSummary(tripId: Long): Flow<List<ExpenseSummary>> =
        if (tripId <= 0) flowOf(emptyList())
        else expenseDao.getExpenseSummary(tripId)

    override suspend fun addExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    override suspend fun fetchDestinationInfo(country: String): DestinationInfo? {
        return try {
            val countries: List<RestCountry> = api.getCountryByName(country)
            val first = countries.firstOrNull() ?: return null

            DestinationInfo(
                country = first.name?.common ?: country,
                capital = first.capital?.firstOrNull() ?: "Unknown",
                region = first.region ?: "Unknown",
                population = first.population ?: 0L,
                flagUrl = first.flags?.png ?: ""
            )
        } catch (e: Exception) {
            null
        }
    }
}
