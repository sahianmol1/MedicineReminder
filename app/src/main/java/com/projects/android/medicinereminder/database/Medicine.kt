package com.projects.android.medicinereminder.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Medicine(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val medicineName1: String? = null,
    val medicineName2: String? = null,
    val medicineName3: String? = null,
    val medicineName4: String? = null,
    val time: Long? = null
): Serializable