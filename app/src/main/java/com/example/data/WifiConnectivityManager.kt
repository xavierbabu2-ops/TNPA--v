package com.example.data

import com.example.model.WifiNetworkItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object WifiConnectivityManager {

    private val _networks = MutableStateFlow(
        listOf(
            WifiNetworkItem("XavierBabu_Home_5G", "00:11:22:33:44:55", true, true, "சிறந்தது (Excellent)", "WPA3 Secure", "5 GHz"),
            WifiNetworkItem("Sovereign_IoT_Gateway", "AA:BB:CC:DD:EE:FF", true, false, "நல்லது (Good)", "WPA2 Secured", "2.4 GHz"),
            WifiNetworkItem("Xavier_Backup_Network", "11:22:33:44:55:66", true, false, "மிதமானது (Fair)", "WPA2 Secured", "2.4 GHz"),
            WifiNetworkItem("Public_Free_Wi-Fi", "99:88:77:66:55:44", false, false, "பலவீனமானது (Weak)", "Open", "2.4 GHz")
        )
    )
    val networks: StateFlow<List<WifiNetworkItem>> = _networks.asStateFlow()

    private val _connectionStatus = MutableStateFlow("XavierBabu_Home_5G நெட்வொர்க்குடன் பாதுகாப்பாக இணைக்கப்பட்டுள்ளது.")
    val connectionStatus: StateFlow<String> = _connectionStatus.asStateFlow()

    fun connectToNetwork(ssid: String) {
        val target = _networks.value.find { it.ssid == ssid }
        if (target != null && target.isAuthorized) {
            _networks.value = _networks.value.map {
                it.copy(isConnected = (it.ssid == ssid))
            }
            _connectionStatus.value = "AI: '${target.ssid}' நெட்வொர்க்கிற்கு பாதுகாப்பாக இணைக்கப்பட்டது."
        } else if (target != null && !target.isAuthorized) {
            _connectionStatus.value = "எச்சரிக்கை: '${target.ssid}' அங்கீகரிக்கப்படாத நெட்வொர்க். உரிமையாளர் அனுமதி தேவை."
        }
    }
}
