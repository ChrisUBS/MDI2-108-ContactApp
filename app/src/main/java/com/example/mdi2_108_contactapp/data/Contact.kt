package com.example.mdi2_108_contactapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")

data class Contact(
    // @PrimaryKey marks this field as the unique identifier for each row
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    // The contact full name
    // required - there is no default value so callers must always provide it
    val name: String,
    // String preserver leading Zeros, + sign, spaces, and international formats
    val phoneNumber: String,
    // Optional - defaults to empty string so callers do not have to provide it
    // An empty means "No email", we never store a null for this field
    val email: String = ""
)
