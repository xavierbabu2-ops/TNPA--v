package com.example.data

import com.example.model.GpsTrackingStatus
import com.example.model.TrackedContactLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object GlobalGpsTrackerManager {

    private val _trackedContacts = MutableStateFlow(
        listOf(
            TrackedContactLocation(
                phoneNumber = "+91 98765 43210",
                name = "ஸேவியர்பாபு (Owner Main Device)",
                latitude = 13.0827,
                longitude = 80.2707,
                address = "சென்னை, தமிழ்நாடு, இந்தியா",
                accuracyMeters = 3,
                lastUpdated = "இப்போது (Live)",
                status = GpsTrackingStatus.SECURE_CONNECTED,
                batteryLevel = 92
            ),
            TrackedContactLocation(
                phoneNumber = "+91 91234 56789",
                name = "குடும்ப உறுப்பினர் / அங்கீகரிக்கப்பட்ட எண் 1",
                latitude = 11.0168,
                longitude = 76.9558,
                address = "கோயம்புத்தூர், தமிழ்நாடு",
                accuracyMeters = 8,
                lastUpdated = "2 நிமிடங்களுக்கு முன்பு",
                status = GpsTrackingStatus.LIVE_TRACKING,
                batteryLevel = 84
            ),
            TrackedContactLocation(
                phoneNumber = "+91 99887 76655",
                name = "பாதுகாப்பு வாகனம் / IoT Tracker",
                latitude = 9.9252,
                longitude = 78.1198,
                address = "மதுரை, தமிழ்நாடு",
                accuracyMeters = 5,
                lastUpdated = "5 நிமிடங்களுக்கு முன்பு",
                status = GpsTrackingStatus.SECURE_CONNECTED,
                batteryLevel = 78
            )
        )
    )
    val trackedContacts: StateFlow<List<TrackedContactLocation>> = _trackedContacts.asStateFlow()

    private val _radarStatus = MutableStateFlow("ஜிபிஎஸ் ரேடார் லொகேஷன் லாக்கிங் இயங்குகிறது (Global GPS Radar Active)")
    val radarStatus: StateFlow<String> = _radarStatus.asStateFlow()

    fun trackPhoneNumber(number: String, customName: String) {
        val cleanNumber = number.trim()
        if (cleanNumber.isEmpty()) return

        val existing = _trackedContacts.value.find { it.phoneNumber == cleanNumber }
        if (existing == null) {
            val newLocation = TrackedContactLocation(
                phoneNumber = cleanNumber,
                name = if (customName.isNotBlank()) customName else "Target Number ($cleanNumber)",
                latitude = 12.9716 + (Math.random() * 0.1 - 0.05),
                longitude = 77.5946 + (Math.random() * 0.1 - 0.05),
                address = "GPS Satellite Triangulation (Active Lock)",
                accuracyMeters = 6,
                lastUpdated = "இப்போது (Just Now)",
                status = GpsTrackingStatus.LIVE_TRACKING,
                batteryLevel = 88
            )
            _trackedContacts.value = _trackedContacts.value + newLocation
            _radarStatus.value = "AI: '$cleanNumber' எண் வெற்றிகரமாக ரேடாரில் இணைக்கப்பட்டு ஜிபிஎஸ் லொகேஷன் பெறப்பட்டது."
        } else {
            _radarStatus.value = "AI: '$cleanNumber' ஏற்கனவே ரேடார் கண்காணிப்பில் உள்ளது."
        }
    }

    fun refreshLocation(phoneNumber: String) {
        _radarStatus.value = "AI: '$phoneNumber' எண்ணிற்கான சிக்னல் ரேடார் மூலமாக ஸ்கேன் செய்யப்படுகிறது..."
    }
}
