package com.projects.android.medicinereminder.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MedicineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(medicine: Medicine): Long

    @Delete
    suspend fun delete(medicine: Medicine)

    @Query("SELECT * FROM medicine")
    fun getAllMedicine(): LiveData<List<Medicine>>

}