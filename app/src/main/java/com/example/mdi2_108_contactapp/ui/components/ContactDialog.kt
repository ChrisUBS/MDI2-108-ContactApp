package com.example.mdi2_108_contactapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mdi2_108_contactapp.data.Contact
import com.example.mdi2_108_contactapp.ui.theme.MDI2108ContactAppTheme

@Composable
fun ContactDialog(
    contact: Contact? = null,
    onConfirm: (Contact) -> Unit,
    onDismiss: () -> Unit
) {
    // Local state for the three input fields
    // These are separate from the ViewModel - purely UI state
    // They exist only while the dialog is open
    var name by remember { mutableStateOf(contact?.name ?: "") }
    var phoneNumber by remember { mutableStateOf(contact?.phoneNumber ?: "") }
    var email by remember { mutableStateOf(contact?.email ?: "") }

    // Determine the title
    // contact = null means create mode, otherwise edit mode
    val title = if(contact == null) "Add Contact" else "Edit Contact"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                // Name field -- required
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                // Phone number -- field required
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                // Email -- field optional
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email (optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
            } // end for column
        },
        confirmButton = {
            Button(
                onClick = {
                    if(name.isNotBlank() && phoneNumber.isNotBlank()) {
                        // Create a Contact object from that inputs fields
                        // if editing, preserve the original ID: if creating use 0 (Room assigns a new one)
                        val newContact = Contact(
                            id = contact?.id ?: 0,
                            name = name,
                            phoneNumber = phoneNumber,
                            email = email
                        )
                        onConfirm(newContact)
                    }
                }
            ) { Text("Save") }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}