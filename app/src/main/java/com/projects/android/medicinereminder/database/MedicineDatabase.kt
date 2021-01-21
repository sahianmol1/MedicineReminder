package com.projects.android.medicinereminder.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Medicine::class], version = 1)
abstract class MedicineDatabase: RoomDatabase() {
    abstract fun getMedicineDao(): MedicineDao
}