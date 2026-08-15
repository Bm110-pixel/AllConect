package com.example.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Watch
import androidx.compose.ui.graphics.vector.ImageVector

enum class DeviceCategory(val displayName: String) {
    ALL("All Devices"),
    WEARABLE("Wearables & Fitness"),
    SMART_HUB("Smart Home Hubs"),
    LIGHTING("Smart Lighting"),
    CLIMATE("Climate & Comfort"),
    POWER_ENERGY("Power & Plugs"),
    AUDIO_MEDIA("Audio & Media"),
    SECURITY_SENSOR("Security & Sensors"),
    HEALTH("Health Monitors");

    fun getIcon(): ImageVector = when (this) {
        ALL -> Icons.Default.Devices
        WEARABLE -> Icons.Default.Watch
        SMART_HUB -> Icons.Default.Hub
        LIGHTING -> Icons.Default.Lightbulb
        CLIMATE -> Icons.Default.Thermostat
        POWER_ENERGY -> Icons.Default.Power
        AUDIO_MEDIA -> Icons.Default.Speaker
        SECURITY_SENSOR -> Icons.Default.Security
        HEALTH -> Icons.Default.FitnessCenter
    }
}
