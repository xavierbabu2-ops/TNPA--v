package com.example.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.data.SovereignEngine
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Always-Available Voice-First Background AI Service Layer
 * Operates as a persistent background intelligence layer across any foreground app.
 */
class SovereignVoiceBackgroundService : Service() {

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Default + serviceJob)

    private val _isListeningForWakeWord = MutableStateFlow(true)
    val isListeningForWakeWord: StateFlow<Boolean> = _isListeningForWakeWord.asStateFlow()

    private val _activeState = MutableStateFlow("Background Sentinel Active (Waiting for 'பாபு' / 'Babu')...")
    val activeState: StateFlow<String> = _activeState.asStateFlow()

    companion object {
        const val CHANNEL_ID = "SovereignVoiceBackgroundChannel"
        const val NOTIFICATION_ID = 9999
        var instance: SovereignVoiceBackgroundService? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification("AI Background Voice Sentinel Active"))
        startWakeWordSentinelLoop()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Sovereign Background Voice Layer",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Always-available background voice intelligence for Xavier Babu"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    private fun createNotification(contentText: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Sovereign Voice-First AI Layer")
            .setContentText(contentText)
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .setOngoing(true)
            .build()
    }

    private fun startWakeWordSentinelLoop() {
        serviceScope.launch {
            while (isActive) {
                delay(12000)
                // Passive background monitoring for wake-word "[AI Name]"
                SovereignEngine.toggleVoiceListening()
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        instance = null
        serviceJob.cancel()
    }
}
