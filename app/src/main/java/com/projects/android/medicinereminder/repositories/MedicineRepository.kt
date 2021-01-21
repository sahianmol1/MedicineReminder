package com.projects.android.medicinereminder.repositories

import com.projects.android.medicinereminder.database.Medicine
import com.projects.android.medicinereminder.database.MedicineDao
import javax.inject.Inject

class MedicineRepository @Inject constructor(private val dao: MedicineDao) {

    suspend fun upsert(medicine: Medicine) = dao.upsert(medicine)
    suspend fun delete(medicine: Medicine) = dao.delete(medicine)
    fun getAllMedicines() = dao.getAllMedicine()
}