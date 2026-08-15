package com.example.data.model

enum class ConnectionProtocol(val displayName: String, val badgeText: String) {
    BLE("Bluetooth Low Energy", "BLE 5.3"),
    WIFI_LOCAL("Local Wi-Fi Network", "Wi-Fi (mDNS)"),
    MATTER_THREAD("Matter over Thread", "Matter / Thread"),
    ZIGBEE_BRIDGE("Zigbee Mesh Bridge", "Zigbee 3.0"),
    ZWAVE("Z-Wave Mesh", "Z-Wave Plus"),
    CLOUD_SYNC("Universal Cloud Link", "Cloud Sync")
}
