package com.cp3406.smarttravelplanningassistant.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cp3406.smarttravelplanningassistant.data.local.dao.ExpenseSummary
import com.cp3406.smarttravelplanningassistant.data.local.entity.Expense
import com.cp3406.smarttravelplanningassistant.data.local.entity.ItineraryItem
import com.cp3406.smarttravelplanningassistant.data.local.entity.Trip
import com.cp3406.smarttravelplanningassistant.data.repository.TravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.flowOf
import com.cp3406.smarttravelplanningassistant.data.repository.DestinationInfo

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TravelRepository
) : ViewModel() {

    private val _selectedTripId = MutableStateFlow<Long?>(null)
    val selectedTripId: StateFlow<Long?> = _selectedTripId

    val trips: StateFlow<List<Trip>> =
        repository.getTrips()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _destinationInfo = MutableStateFlow<DestinationInfo?>(null)
    val destinationInfo: StateFlow<DestinationInfo?> = _destinationInfo
    val itinerary: StateFlow<List<ItineraryItem>> =
        _selectedTripId.flatMapLatest { tripId ->
            if (tripId == null) flowOf(emptyList())
            else repository.getItinerary(tripId)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val expenses: StateFlow<List<Expense>> =
        _selectedTripId.flatMapLatest { tripId ->
            if (tripId == null) flowOf(emptyList())
            else repository.getExpenses(tripId)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val expenseSummary: StateFlow<List<ExpenseSummary>> =
        _selectedTripId.flatMapLatest { tripId ->
            if (tripId == null) flowOf(emptyList())
            else repository.getExpenseSummary(tripId)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectTrip(tripId: Long) {
        _selectedTripId.value = tripId
    }

    fun addTrip(name: String, destination: String, startDate: String, endDate: String) {
        viewModelScope.launch {
            repository.addTrip(
                Trip(
                    name = name,
                    destination = destination,
                    startDate = startDate,
                    endDate = endDate
                )
            )
        }
    }
    fun loadDestinationInfoForSelectedTrip() {
        val tripId = _selectedTripId.value ?: return
        val currentTrip = trips.value.firstOrNull { it.id == tripId } ?: return

        viewModelScope.launch {
            val info = repository.fetchDestinationInfo(currentTrip.destination)
            _destinationInfo.value = info
        }
    }

    fun addItineraryItem(date: String, time: String, title: String, location: String, notes: String) {
        val tripId = _selectedTripId.value ?: return
        viewModelScope.launch {
            repository.addItineraryItem(
                ItineraryItem(
                    tripId = tripId,
                    date = date,
                    time = time,
                    title = title,
                    location = location,
                    notes = notes
                )
            )
        }
    }

    fun addExpense(category: String, amount: Double, currency: String, notes: String) {
        val tripId = _selectedTripId.value ?: return
        viewModelScope.launch {
            repository.addExpense(
                Expense(
                    tripId = tripId,
                    category = category,
                    amount = amount,
                    currency = currency,
                    notes = notes
                )
            )
        }
    }
}
