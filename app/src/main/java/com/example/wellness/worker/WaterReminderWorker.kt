package com.example.wellness.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class WaterReminderWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        // Aquí iría el código de NotificationManager para mostrar la alerta real
        Log.d("WaterReminderWorker", "¡Es hora de beber un vaso de agua!")
        return Result.success()
    }
}