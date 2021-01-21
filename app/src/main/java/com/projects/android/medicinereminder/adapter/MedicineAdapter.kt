package com.projects.android.medicinereminder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projects.android.medicinereminder.R
import com.projects.android.medicinereminder.database.Medicine
import kotlinx.android.synthetic.main.layout_medicine_reminders.view.*

class MedicineAdapter(private val list: List<Medicine>) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_medicine_reminders, parent, false)

        return MedicineViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class MedicineViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(medicine: Medicine) {
            itemView.tv_recycler_medicine_name.text = medicine.medicineName1 + ", " + medicine.medicineName2
            itemView.tv_recycler_time.text = medicine.time.toString()
        }
    }

}