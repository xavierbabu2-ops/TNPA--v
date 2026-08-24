package com.example.model

data class SmartDevice(
    val id: String,
    val name: String,
    val type: DeviceType,
    val location: String,
    val isOnline: Boolean,
    val statusText: String,
    val batteryLevel: Int? = null,
    val isPoweredOn: Boolean = false,
    val valueInt: Int? = null, // e.g. volume or temperature
    val valueString: String? = null
)

enum class DeviceType {
    SMART_TV,
    MOTORCYCLE,
    CAR,
    LIGHT,
    AC,
    SECURITY_SENSOR,
    DOOR_SENSOR,
    TEMPERATURE_SENSOR,
    SMART_PLUG,
    SPEAKER
}
