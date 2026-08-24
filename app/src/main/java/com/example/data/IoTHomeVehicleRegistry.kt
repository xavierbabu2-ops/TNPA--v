package com.example.data

import com.example.model.DeviceType
import com.example.model.SmartDevice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object IoTHomeVehicleRegistry {

    private val _devices = MutableStateFlow(
        listOf(
            SmartDevice("tv_hall", "Sony Bravia Smart TV", DeviceType.SMART_TV, "ஹால் (Hall)", true, "YouTube இயக்கத்தில் உள்ளது", isPoweredOn = true, valueInt = 20),
            SmartDevice("bike_01", "Yamaha R15 V4 (Motorcycle)", DeviceType.MOTORCYCLE, "பைக் கேரேஜ்", true, "Fuel: 78% | Temp: 85°C | Battery: 12.6V", batteryLevel = 90, isPoweredOn = false, valueInt = 78),
            SmartDevice("car_01", "Hyundai Creta (Car)", DeviceType.CAR, "வெளி கேரேஜ்", true, "Fuel/Battery: 85% | Odometer: 14,200 km", batteryLevel = 95, isPoweredOn = false, valueInt = 85),
            SmartDevice("light_hall", "ஹால் மெயின் லைட்", DeviceType.LIGHT, "ஹால்", true, "எரியவில்லை (OFF)", isPoweredOn = false),
            SmartDevice("ac_bed", "மாஸ்டர் பெட்ரூம் AC", DeviceType.AC, "படுக்கையறை", true, "24°C | ஆட்டோ மோட்", isPoweredOn = true, valueInt = 24),
            SmartDevice("sensor_door", "முதன்மை வாசல் கதவு சென்சார்", DeviceType.DOOR_SENSOR, "நுழைவு வாசல்", true, "பாதுகாப்பாக பூட்டப்பட்டுள்ளது (Secure)", isPoweredOn = true),
            SmartDevice("sensor_temp", "ஹால் வெப்பமானி & ஈரப்பதமானி", DeviceType.TEMPERATURE_SENSOR, "ஹால்", true, "வெப்பநிலை: 28.5°C | ஈரப்பதம்: 62%", isPoweredOn = true, valueInt = 28)
        )
    )
    val devices: StateFlow<List<SmartDevice>> = _devices.asStateFlow()

    fun updateDevicePower(id: String, power: Boolean) {
        _devices.value = _devices.value.map {
            if (it.id == id) it.copy(isPoweredOn = power, statusText = if (power) "இயக்கத்தில் உள்ளது (ON)" else "நிறுத்தப்பட்டுள்ளது (OFF)") else it
        }
    }

    fun updateDeviceValue(id: String, newValue: Int) {
        _devices.value = _devices.value.map {
            if (it.id == id) it.copy(valueInt = newValue, statusText = "மதிப்பு மாற்றப்பட்டது: $newValue") else it
        }
    }
}
