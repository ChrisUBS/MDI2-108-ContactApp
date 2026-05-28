package com.example.mdi2_108_contactapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    // retrieves all contacts ordered alphabetically by name
    @Query("SELECT * FROM contacts ORDER BY name ASC")
    fun getAllContacts(): Flow<List<Contact>>

    // on ConflictStrategy.REPLACE menas if a contact with the same ID exist then ROOM deletes the old
    // old row and insert the new one
    // suspend runs this disk operations off the main thread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    // updates an existing contact in the database
    // @Update tells Room to generate a SQL UPDATE statement
    // suspend runs this disk operations off the main thread
    @Update
    suspend fun updateContact(contact: Contact)

    // @Delete tells Room to generate a SQL Delete statement
    // suspend runs this disk operations off the main thread
    // Room matches the contact by its ID and removes the entire row
    @Delete
    suspend fun deleteContact(contact: Contact)
}