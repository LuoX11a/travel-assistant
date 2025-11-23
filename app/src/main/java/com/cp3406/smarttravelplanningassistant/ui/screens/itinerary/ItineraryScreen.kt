package com.cp3406.smarttravelplanningassistant.ui.screens.itinerary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cp3406.smarttravelplanningassistant.data.local.entity.ItineraryItem
import com.cp3406.smarttravelplanningassistant.ui.viewmodel.MainViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun ItineraryScreen(
    viewModel: MainViewModel
) {
    val itinerary by viewModel.itinerary.collectAsState()
    val selectedTripId by viewModel.selectedTripId.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Itinerary", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        if (selectedTripId == null) {
            Text("Select a trip first on the Trips tab.")
            return
        }

        ItineraryForm(onAdd = { date, time, title, location, notes ->
            viewModel.addItineraryItem(date, time, title, location, notes)
        })

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(itinerary) { item ->
                ItineraryCard(item)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun ItineraryCard(item: ItineraryItem) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("${item.date}  ${item.time}", style = MaterialTheme.typography.bodySmall)
            Text(item.title, style = MaterialTheme.typography.titleMedium)
            Text(item.location, style = MaterialTheme.typography.bodyMedium)
            if (item.notes.isNotBlank()) {
                Text(item.notes, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun ItineraryForm(
    onAdd: (String, String, String, String, String) -> Unit
) {
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                label = { Text("Time") },
                modifier = Modifier.weight(1f)
            )
        }
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes (optional)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (title.isNotBlank()) {
                    onAdd(date, time, title, location, notes)
                    date = ""
                    time = ""
                    title = ""
                    location = ""
                    notes = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add item")
        }
    }
}
