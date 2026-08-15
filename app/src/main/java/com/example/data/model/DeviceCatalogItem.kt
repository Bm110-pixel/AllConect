package com.example.data.model

data class DeviceCatalogItem(
    val id: String,
    val name: String,
    val modelName: String,
    val manufacturer: String,
    val manufacturerCountry: String,
    val category: DeviceCategory,
    val devicePurpose: String,
    val keyCapabilities: List<String>,
    val howItWorks: String,
    val defaultProtocol: ConnectionProtocol,
    val defaultBatteryPowered: Boolean,
    val typicalBatteryPct: Int = 85,
    val defaultRoom: String = "Living Room",
    val samplePrimaryLabel: String,
    val samplePrimaryValue: String,
    val sampleSecondaryLabel: String,
    val sampleSecondaryValue: String,
    val pairingInstructions: String
)
