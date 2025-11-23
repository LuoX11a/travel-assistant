package com.cp3406.smarttravelplanningassistant.ui.screens.expenses

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
import com.cp3406.smarttravelplanningassistant.data.local.dao.ExpenseSummary
import com.cp3406.smarttravelplanningassistant.data.local.entity.Expense
import com.cp3406.smarttravelplanningassistant.ui.viewmodel.MainViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun ExpenseScreen(
    viewModel: MainViewModel
) {
    val expenses by viewModel.expenses.collectAsState()
    val summary by viewModel.expenseSummary.collectAsState()
    val selectedTripId by viewModel.selectedTripId.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Expenses", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        if (selectedTripId == null) {
            Text("Select a trip first on the Trips tab.")
            return
        }

        ExpenseForm(onAdd = { category, amount, currency, notes ->
            viewModel.addExpense(category, amount, currency, notes)
        })

        Spacer(modifier = Modifier.height(16.dp))

        Text("By category", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))

        summary.forEach { s: ExpenseSummary ->
            Text("${s.category}: ${"%.2f".format(s.total)}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("All expenses", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn {
            items(expenses) { e: Expense ->
                ExpenseCard(e)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun ExpenseCard(expense: Expense) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                "${expense.category} - ${expense.amount} ${expense.currency}",
                style = MaterialTheme.typography.titleMedium
            )
            if (expense.notes.isNotBlank()) {
                Text(expense.notes, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun ExpenseForm(
    onAdd: (String, Double, String, String) -> Unit
) {
    var category by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("USD") }
    var notes by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category (e.g. Flight, Hotel)") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                label = { Text("Amount") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = currency,
                onValueChange = { currency = it },
                label = { Text("Currency") },
                modifier = Modifier.weight(1f)
            )
        }
        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes (optional)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val amount = amountText.toDoubleOrNull()
                if (category.isNotBlank() && amount != null) {
                    onAdd(category, amount, currency, notes)
                    category = ""
                    amountText = ""
                    notes = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add expense")
        }
    }
}
