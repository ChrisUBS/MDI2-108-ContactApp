package com.example.mdi2_108_contactapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mdi2_108_contactapp.data.Contact
import com.example.mdi2_108_contactapp.ui.ContactViewModel
import com.example.mdi2_108_contactapp.ui.components.ContactDialog
import com.example.mdi2_108_contactapp.ui.components.ContactItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    viewModel: ContactViewModel,
    onContactClick: (Contact) -> Unit
) {
    // Collect the contact StateFlow as compose State
    // Every time the viewModel emits a new list, this recomposes and show the updated list
    val contacts by viewModel.contacts.collectAsState()

    // showAddDialog = true when the user taps the FAB
    var showAddDialog by remember{ mutableStateOf(false) }

    if(showAddDialog) {
        ContactDialog(
            contact = null,
            onConfirm = { newContact ->
                viewModel.saveContact(newContact)
                // Close the dialog after saving
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    } // end of IF statement
    // Scaffold provides the Material Design Structure
    // floatingActionButton : the + button to add a contact
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contacts") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Contact")
            }
        }
    ) {
        padding ->
        if(contacts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No contacts yet. \nTap + to add one.",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(contacts, key = {it.id}) { contact ->
                    ContactItem(
                        contact = contact,
                        onClick = {onContactClick(contact)},
                        onDelete = {viewModel.deleteContact(contact)}
                    )
                }
            }
        }
    }
}