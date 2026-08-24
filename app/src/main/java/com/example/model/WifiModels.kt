package com.example.model

data class WifiNetworkItem(
    val ssid: String,
    val bssid: String,
    val isAuthorized: Boolean,
    val isConnected: Boolean,
    val signalStrength: String, // Excellent, Good, Fair
    val securityType: String, // WPA3, WPA2, Secured
    val frequency: String // 5GHz / 2.4GHz
)
