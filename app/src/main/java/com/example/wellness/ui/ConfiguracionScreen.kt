package com.example.wellness.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.wellness.worker.WaterReminderWorker
import java.util.concurrent.TimeUnit

@Composable
fun ConfiguracionScreen() {
    val context = LocalContext.current
    var waterReminderEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Configuración", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Recordatorio de Agua cada 15 min")
            Switch(
                checked = waterReminderEnabled,
                onCheckedChange = { isChecked ->
                    waterReminderEnabled = isChecked
                    val workManager = WorkManager.getInstance(context)
                    if (isChecked) {
                        val request = PeriodicWorkRequestBuilder<WaterReminderWorker>(15, TimeUnit.MINUTES).build()
                        workManager.enqueue(request)
                    } else {
                        workManager.cancelAllWork()
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text("Calendario", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        HabitCalendar()
    }
}