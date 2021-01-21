package com.projects.android.medicinereminder.broadcastReceivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.projects.android.medicinereminder.others.Constants
import com.projects.android.medicinereminder.R

class AlarmReceiver : BroadcastReceiver() {
    lateinit var mNotificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        deliverNotification(context, intent)
    }

    private fun deliverNotification(context: Context, intent: Intent) {
        mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val medicineName = intent.getStringExtra(Constants.MEDICINE_NAME)
        val alarmTiming = intent.getStringExtra(Constants.ALARM_TIMING)

        val notificationBuilder = NotificationCompat.Builder(context,
            Constants.PRIMARY_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_medical)
            .setContentTitle("Time to Take Medicines")
            .setContentText("Hey Anmol, Have you taken $medicineName at $alarmTiming?")
            .setStyle(NotificationCompat.BigTextStyle().bigText("Hey Anmol, Have you taken $medicineName at $alarmTiming?"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        mNotificationManager.notify(Constants.NOTIFICATION_ID, notificationBuilder.build())
    }
}