package com.example.mdi2_108_contactapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1, exportSchema = false)
// This is the only way the rest of the app accesses the database operations
// Abstract tells Rooms to generate the body of this function automatically
abstract class ContactDatabase : RoomDatabase() {
    // Abstract - Room generate
    // Room database is the base class all room databases must extends
    abstract fun contactDao(): ContactDao
    companion object {
        // @Volatile ensures every thread always reads this value from main memory
        // Without @Volatile a thread could read a stale null from its CPU cache
        // and try to create a second database instance - breaking the Singleton
        @Volatile
        private var INSTANCE: ContactDatabase? = null
        // Nobody ever instantiates ContactDatabase directly
        // context is needed by Room to create and locate the database file on disk
        fun getDatabase(context: Context): ContactDatabase {
            // The ?: elvis sign means " if null execute the right side
            return INSTANCE ?: synchronized(lock = this) {
                // DataBase builder was written in Java and requires this format
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    // The actual filename of the database file stored on the device
                    "contacts_database"
                ).build()
                INSTANCE = instance
                // Return the newly created instance
                instance
            }
        }
    }
}