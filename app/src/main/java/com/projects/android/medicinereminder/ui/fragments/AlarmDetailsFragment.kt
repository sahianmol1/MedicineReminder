package com.projects.android.medicinereminder.ui.fragments

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.projects.android.medicinereminder.R
import com.projects.android.medicinereminder.broadcastReceivers.AlarmReceiver
import com.projects.android.medicinereminder.database.Medicine
import com.projects.android.medicinereminder.others.Constants
import com.projects.android.medicinereminder.ui.MainActivity
import com.projects.android.medicinereminder.viewmodels.MedicineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_alarm_details.*
import java.util.*

@AndroidEntryPoint
class AlarmDetailsFragment : Fragment(R.layout.fragment_alarm_details) {

    lateinit var mNotificationManager: NotificationManager
    lateinit var calendar: Calendar
    lateinit var alarmManager: AlarmManager
    var count: Int = 0

    lateinit var mViewModel: MedicineViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = (activity as MainActivity).mViewModel

        // Creating the Calendar Instance
        calendar = Calendar.getInstance()

        // Creating the Notification Manager Instance
        mNotificationManager =
            view.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Creating the Alarm Manager Instance
        alarmManager = view.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        setOnClickListeners(view.context)
        createNotificationChannel()

    }

    private fun setOnClickListeners(context: Context) {

        // Dynamically Adds the Edit Text Views on click of Add another Medicine Text
        tv_add_another_medicine.setOnClickListener {
            count++
            when (count) {
                1 -> {
                    et_medicine_name_2.visibility = View.VISIBLE
                }
                2 -> {
                    et_medicine_name_3.visibility = View.VISIBLE
                }
                3 -> {
                    et_medicine_name_4.visibility = View.VISIBLE
                    tv_add_another_medicine.visibility = View.GONE
                }
            }
        }


        // Display the Time Picker Dialog on Click of Edit Text - et_set_time
        et_set_time.setOnClickListener {
            showTimePickerDialog()
        }

        // Define the action on click of Remind Me! Button
        btn_set_reminder.setOnClickListener {

            val medicineName1 = et_medicine_name.text.toString()
            val medicineName2 = et_medicine_name_2.text.toString()
            val medicineName3 = et_medicine_name_3.text.toString()
            val medicineName4 = et_medicine_name_4.text.toString()

            // The notification Intent that will get the broadcast
            val notifyIntent = Intent(context, AlarmReceiver::class.java)
            notifyIntent.putExtra(
                Constants.MEDICINE_NAME,
                medicineName1
                        + " " + medicineName2
                        + " " + medicineName3
                        + " " + medicineName4
            )
            notifyIntent.putExtra(Constants.ALARM_TIMING, et_set_time.text.toString())

            val notifyPendingIntentId = System.currentTimeMillis().toInt()

            val notifyPendingIntent = PendingIntent.getBroadcast(
                context,
                notifyPendingIntentId,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            // Setting Up the Alarm Manager
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                notifyPendingIntent
            )
            val medicine = Medicine(
                medicineName1 = medicineName1,
                medicineName2 = medicineName2,
                medicineName3 = medicineName3,
                medicineName4 = medicineName4,
                time = calendar.timeInMillis
            )
            mViewModel.upsert(medicine)

            Toast.makeText(
                context,
                "Alarm is set for ${et_set_time.text.toString()}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showTimePickerDialog() {
        val mTimePickerDialog =
            TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { p0, hourOfDay, minutes ->
                et_set_time.setText("${hourOfDay}:${minutes}")
                setCalendar(hourOfDay, minutes)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)
        mTimePickerDialog.show()
    }

    private fun setCalendar(hourOfDay: Int, minutes: Int) {
        calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minutes)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                Constants.PRIMARY_CHANNEL_ID,
                "Medicine Reminder Notification",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = "Notifies the User to take Medicine at the given time"
            }

            mNotificationManager.createNotificationChannel(notificationChannel)
        }
    }
}