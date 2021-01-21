package com.projects.android.medicinereminder.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.android.medicinereminder.database.Medicine
import com.projects.android.medicinereminder.repositories.MedicineRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MedicineViewModel @ViewModelInject constructor(val repository: MedicineRepository): ViewModel() {

    fun upsert(medicine: Medicine) = viewModelScope.launch { repository.upsert(medicine) }
    fun delete(medicine: Medicine) = viewModelScope.launch { repository.delete(medicine) }

    fun getAllMedicine() = repository.getAllMedicines()
}