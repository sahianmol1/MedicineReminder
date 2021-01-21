package com.projects.android.medicinereminder.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projects.android.medicinereminder.R
import com.projects.android.medicinereminder.adapter.MedicineAdapter
import com.projects.android.medicinereminder.ui.MainActivity
import com.projects.android.medicinereminder.viewmodels.MedicineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.fragment_home) {

    lateinit var mViewModel: MedicineViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = (activity as MainActivity).mViewModel
        recycler_view.layoutManager = LinearLayoutManager(activity)


        mViewModel.getAllMedicine().observe(viewLifecycleOwner, Observer { allMedicineReminders ->
            allMedicineReminders?.let {
                if (allMedicineReminders.isNotEmpty()){
                    val mAdapter = MedicineAdapter(allMedicineReminders)
                    recycler_view.adapter = mAdapter
                    tv_let_start.visibility = View.GONE
                    tv_your_reminders.visibility = View.VISIBLE
                    recycler_view.visibility = View.VISIBLE
                }

            }
        })

        fab_add_medicines.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAlarmDetailsFragment())
        }
    }
}