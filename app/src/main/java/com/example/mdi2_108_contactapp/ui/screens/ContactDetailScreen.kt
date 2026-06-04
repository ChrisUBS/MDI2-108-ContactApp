package com.example.mdi2_108_contactapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mdi2_108_contactapp.ui.ContactViewModel
import com.example.mdi2_108_contactapp.ui.components.ContactDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
    contactId: Int,
    viewModel: ContactViewModel,
    onBackClick: () -> Unit
) {
    // Collect the entire contacts list from the ViewModel
    val contacts by viewModel.contacts.collectAsState()

    // Find the current contact by ID
    val contact = contacts.firstOrNull { it.id == contactId }

    // if the contact was delete, go back to list
    if(contact == null) {
        onBackClick()
        return
    }

    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if(showEditDialog) {
        ContactDialog(
            contact = contact,
            onDismiss = { showEditDialog = false },
            onConfirm = { updatedContact ->
                viewModel.updateContact(updatedContact)
                showEditDialog = false
            }
        )
    }

    if(showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Contact") },
            text = { Text("Delete ${contact.name}? This cannot be undone") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteContact(contact)
                        showDeleteDialog = false
                        onBackClick()
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(contact.name, style = MaterialTheme.typography.headlineMedium)
            HorizontalDivider()
            Text("Phone: ${contact.phoneNumber}", style = MaterialTheme.typography.bodyLarge)
            if(contact.email.isNotEmpty()) {
                Text("Email: ${contact.email}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}