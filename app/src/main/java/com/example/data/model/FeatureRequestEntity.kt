package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class FeatureStatus(val label: String, val badgeColorName: String) {
    GATHERING("Gathering Community Votes", "indigo"),
    PROCESSING_BY_GEMINI("Processing in 30-Day Cycle", "violet"),
    ADDED_TO_ALLCONNECT("Added to AllConnect! (Cycle Winner)", "green"),
    IN_DEVELOPMENT("In Active Development", "amber")
}

@Entity(tableName = "feature_requests")
data class FeatureRequestEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val rawUserThought: String,
    val aiSynthesis: String,
    val technicalFeasibility: String, // e.g. "High • Direct BLE GATT Service", "Medium • Matter 1.3 spec"
    val protocolsInvolved: String,   // e.g. "BLE GATT", "Matter", "Zigbee", "Local REST"
    val category: String,            // "Wearables", "Smart Home Hubs", "Automation", "Security", "Manuals"
    val requestVotes: Int,
    val userHasVoted: Boolean = false,
    val status: FeatureStatus = FeatureStatus.GATHERING,
    val gatherCycleMonth: String = "Cycle 8 (Current Month)",
    val submittedTimestamp: Long = System.currentTimeMillis(),
    val isWinningFeature: Boolean = false,
    val engineeringNotes: String = ""
)
