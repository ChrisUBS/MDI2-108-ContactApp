package com.example.mdi2_108_contactapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdi2_108_contactapp.data.Contact
import com.example.mdi2_108_contactapp.data.ContactDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    // Get the DAO from the database singleton
    private val dao = ContactDatabase
        .getDatabase(application)
        .contactDao()

    // Expose all contacts as a StateFlow
    // This starts as an empty list and emits a new list whenever the database changes

    val contacts: StateFlow<List<Contact>> = dao.getAllContacts().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun saveContact(contact: Contact) {
        viewModelScope.launch {
            dao.insertContact(contact)
        }
    }

    // Update and existing contact in the database

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            dao.updateContact(contact)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            dao.deleteContact(contact)
        }
    }
}