package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices")
data class DeviceEntity(
    @PrimaryKey val id: String,
    val name: String,
    val modelName: String,
    val manufacturer: String,
    val manufacturerCountry: String = "USA",
    val category: DeviceCategory,
    val devicePurpose: String,
    val keyCapabilities: String, // Comma-separated or bullet items
    val howItWorks: String,
    val connectionProtocol: ConnectionProtocol,
    val macAddress: String,
    val ipAddress: String = "",
    val room: String = "Living Room",
    val isOnline: Boolean = true,
    val isFavorite: Boolean = false,
    val signalRssi: Int = -55,
    val batteryPercent: Int = -1, // -1 means AC powered
    val firmwareVersion: String = "v1.0.0",
    val lastSyncTimestamp: Long = System.currentTimeMillis(),
    val powerState: Boolean = true,
    val primaryMetricLabel: String = "Status",
    val primaryMetricValue: String = "Active",
    val secondaryMetricLabel: String = "Connection",
    val secondaryMetricValue: String = "Connected",
    val customNotes: String = "",
    // Fitness & Wearable Telemetry (Fitbit, Balco, Garmin, Apple Watch)
    val heartRateBpm: Int = 72,
    val restingHeartRateBpm: Int = 60,
    val caloriesBurned: Int = 540,
    val calorieGoal: Int = 2200,
    val stepCount: Int = 8420,
    val stepGoal: Int = 10000,
    val distanceKm: Float = 5.8f,
    val activeMinutes: Int = 38,
    val sleepHours: Float = 7.5f,
    // Device Controls (Smart Hubs, Lighting, Climate, Audio, Power, Security)
    val targetTemperature: Float = 71.0f,
    val climateMode: String = "HEAT", // HEAT, COOL, ECO, OFF
    val fanSpeed: String = "AUTO", // AUTO, LOW, MED, HIGH
    val brightnessPercent: Int = 85,
    val colorHex: String = "#FFB300",
    val activeScene: String = "Warm Sunset",
    val volumePercent: Int = 42,
    val isMuted: Boolean = false,
    val playbackState: String = "Playing",
    val currentTrack: String = "Lo-Fi Chill • Acoustic Lounge",
    val isLocked: Boolean = true,
    val hubChildNodesJson: String = ""
)
