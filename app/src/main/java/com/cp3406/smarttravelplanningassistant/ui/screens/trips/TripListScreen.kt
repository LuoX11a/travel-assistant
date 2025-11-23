package com.cp3406.smarttravelplanningassistant.ui.screens.trips

import androidx.compose.foundation.clickable
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
import com.cp3406.smarttravelplanningassistant.data.local.entity.Trip
import com.cp3406.smarttravelplanningassistant.ui.viewmodel.MainViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.collectAsState
import com.cp3406.smarttravelplanningassistant.data.repository.DestinationInfo

@Composable
fun TripListScreen(
    viewModel: MainViewModel
) {
    val trips by viewModel.trips.collectAsState()
    val selectedTripId by viewModel.selectedTripId.collectAsState()
    val destinationInfo by viewModel.destinationInfo.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Trips",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        TripForm(onAddTrip = { name, destination, startDate, endDate ->
            viewModel.addTrip(name, destination, startDate, endDate)
        })

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedTripId != null) {
            Button(
                onClick = { viewModel.loadDestinationInfoForSelectedTrip() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Load destination info")
            }

            Spacer(modifier = Modifier.height(8.dp))

            destinationInfo?.let { info ->
                DestinationInfoCard(info = info)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        LazyColumn {
            items(trips) { trip ->
                TripItem(
                    trip = trip,
                    selected = trip.id == selectedTripId,
                    onClick = { viewModel.selectTrip(trip.id) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

    }
}

@Composable
private fun TripItem(
    trip: Trip,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = trip.name, style = MaterialTheme.typography.titleMedium)
            Text(text = trip.destination, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "${trip.startDate} - ${trip.endDate}",
                style = MaterialTheme.typography.bodySmall
            )
            if (selected) {
                Text(
                    text = "Selected",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun TripForm(
    onAddTrip: (String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Trip name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("Destination") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = startDate,
                onValueChange = { startDate = it },
                label = { Text("Start date") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = endDate,
                onValueChange = { endDate = it },
                label = { Text("End date") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (name.isNotBlank() && destination.isNotBlank()) {
                    onAddTrip(name, destination, startDate, endDate)
                    name = ""
                    destination = ""
                    startDate = ""
                    endDate = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add trip")
        }
    }
}

@Composable
private fun DestinationInfoCard(info: DestinationInfo) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Country: ${info.country}", style = MaterialTheme.typography.titleMedium)
            Text("Capital: ${info.capital}", style = MaterialTheme.typography.bodyMedium)
            Text("Region: ${info.region}", style = MaterialTheme.typography.bodyMedium)
            Text("Population: ${info.population}", style = MaterialTheme.typography.bodySmall)

        }
    }
}