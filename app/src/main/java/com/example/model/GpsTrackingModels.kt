package com.example.model

data class TrackedContactLocation(
    val phoneNumber: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val accuracyMeters: Int,
    val lastUpdated: String,
    val status: GpsTrackingStatus,
    val batteryLevel: Int
)

enum class GpsTrackingStatus {
    SECURE_CONNECTED,
    LIVE_TRACKING,
    PENDING_AUTHORIZATION,
    SIGNAL_LOST
}
