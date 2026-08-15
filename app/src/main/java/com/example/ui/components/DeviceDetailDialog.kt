package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.AppShortcut
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lan
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.DeviceCategory
import com.example.data.model.DeviceEntity
import com.example.ui.theme.ConnectCyan
import com.example.ui.theme.ConnectGreen
import com.example.ui.theme.ConnectIndigo
import com.example.ui.theme.ConnectPurple
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DeviceDetailDialog(
    device: DeviceEntity,
    isFetchingFitness: Boolean = false,
    onDismiss: () -> Unit,
    onSync: () -> Unit,
    onFetchFitnessData: () -> Unit,
    onTogglePower: () -> Unit,
    onRingFinder: () -> Unit,
    onUpdateLighting: (brightness: Int, colorHex: String, scene: String, powerState: Boolean) -> Unit,
    onUpdateClimate: (temp: Float, mode: String, fanSpeed: String) -> Unit,
    onUpdateAudio: (volume: Int, playbackState: String, isMuted: Boolean) -> Unit,
    onToggleLock: () -> Unit,
    onToggleHubNode: (nodeId: String) -> Unit,
    onSetAllHubNodes: (turnAllOn: Boolean) -> Unit,
    onSetHubScene: (sceneName: String, colorHex: String) -> Unit,
    onUpdateDetails: (name: String, room: String, notes: String) -> Unit,
    onRemoveDevice: () -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editedName by remember(device) { mutableStateOf(device.name) }
    var editedRoom by remember(device) { mutableStateOf(device.room) }
    var editedNotes by remember(device) { mutableStateOf(device.customNotes) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    // Live Control Local States
    var localBrightness by remember(device.brightnessPercent) { mutableFloatStateOf(device.brightnessPercent.toFloat()) }
    var localTemp by remember(device.targetTemperature) { mutableFloatStateOf(device.targetTemperature) }
    var localClimateMode by remember(device.climateMode) { mutableStateOf(device.climateMode) }
    var localVolume by remember(device.volumePercent) { mutableFloatStateOf(device.volumePercent.toFloat()) }
    var localAudioPlaying by remember(device.playbackState) { mutableStateOf(device.playbackState == "PLAYING") }

    val formattedSyncTime = remember(device.lastSyncTimestamp) {
        val sdf = SimpleDateFormat("h:mm a, MMM d", Locale.getDefault())
        sdf.format(Date(device.lastSyncTimestamp))
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(24.dp))
                .testTag("device_detail_dialog"),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header with Close and Edit
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            MaterialTheme.colorScheme.primaryContainer,
                                            MaterialTheme.colorScheme.secondaryContainer
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = device.category.getIcon(),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column {
                            Text(
                                text = device.category.displayName.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 0.8.sp
                            )
                            Text(
                                text = "Device Control & Telemetry",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = {
                                if (isEditing) {
                                    onUpdateDetails(editedName, editedRoom, editedNotes)
                                    isEditing = false
                                } else {
                                    isEditing = true
                                }
                            },
                            modifier = Modifier.testTag("edit_save_btn")
                        ) {
                            Icon(
                                imageVector = if (isEditing) Icons.Default.Save else Icons.Default.Edit,
                                contentDescription = if (isEditing) "Save changes" else "Edit details",
                                tint = if (isEditing) ConnectGreen else MaterialTheme.colorScheme.primary
                            )
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.testTag("close_detail_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Editable Name & Room or Display view
                if (isEditing) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = editedName,
                            onValueChange = { editedName = it },
                            label = { Text("Device Custom Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("edit_name_field")
                        )
                        OutlinedTextField(
                            value = editedRoom,
                            onValueChange = { editedRoom = it },
                            label = { Text("Room / Location Assignment") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("edit_room_field")
                        )
                        OutlinedTextField(
                            value = editedNotes,
                            onValueChange = { editedNotes = it },
                            label = { Text("Custom Notes & Placement") },
                            modifier = Modifier.fillMaxWidth().testTag("edit_notes_field")
                        )
                    }
                } else {
                    Column {
                        Text(
                            text = device.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = device.manufacturer,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Text(
                                text = "Model: ${device.modelName}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ==========================================
                // 1. DEDICATED CATEGORY CONTROLLER SECTION
                // ==========================================

                when (device.category) {
                    DeviceCategory.WEARABLE -> {
                        FitnessTrackerControlCard(
                            device = device,
                            isFetching = isFetchingFitness,
                            onFetch = onFetchFitnessData
                        )
                    }
                    DeviceCategory.SMART_HUB -> {
                        SmartHubNodesControlCard(
                            device = device,
                            onToggleNode = onToggleHubNode,
                            onSetAllNodes = onSetAllHubNodes,
                            onSetScene = onSetHubScene
                        )
                    }
                    DeviceCategory.LIGHTING -> {
                        LightingControlCard(
                            device = device,
                            localBrightness = localBrightness,
                            onBrightnessChange = {
                                localBrightness = it
                                onUpdateLighting(it.toInt(), device.colorHex, device.activeScene, device.powerState)
                            },
                            onSceneSelected = { sceneName, hex ->
                                onUpdateLighting(localBrightness.toInt(), hex, sceneName, true)
                            },
                            onTogglePower = onTogglePower
                        )
                    }
                    DeviceCategory.CLIMATE -> {
                        ClimateControlCard(
                            device = device,
                            localTemp = localTemp,
                            localMode = localClimateMode,
                            onTempChange = { newTemp ->
                                localTemp = newTemp
                                onUpdateClimate(newTemp, localClimateMode, device.fanSpeed)
                            },
                            onModeChange = { newMode ->
                                localClimateMode = newMode
                                onUpdateClimate(localTemp, newMode, device.fanSpeed)
                            }
                        )
                    }
                    DeviceCategory.AUDIO_MEDIA -> {
                        AudioControlCard(
                            device = device,
                            localVolume = localVolume,
                            isPlaying = localAudioPlaying,
                            onVolumeChange = { vol ->
                                localVolume = vol
                                onUpdateAudio(vol.toInt(), if (localAudioPlaying) "PLAYING" else "PAUSED", device.isMuted)
                            },
                            onTogglePlay = {
                                val next = !localAudioPlaying
                                localAudioPlaying = next
                                onUpdateAudio(localVolume.toInt(), if (next) "PLAYING" else "PAUSED", device.isMuted)
                            }
                        )
                    }
                    DeviceCategory.SECURITY_SENSOR -> {
                        SecurityControlCard(
                            device = device,
                            onToggleLock = onToggleLock
                        )
                    }
                    DeviceCategory.POWER_ENERGY -> {
                        PowerRelayControlCard(
                            device = device,
                            onTogglePower = onTogglePower
                        )
                    }
                    else -> {
                        // General Power & Quick Status
                        GeneralQuickControlCard(
                            device = device,
                            onTogglePower = onTogglePower
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // SECTION 2: WHAT IS THIS DEVICE FOR?
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                    ),
                    border = CardDefaults.outlinedCardBorder(enabled = true),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "WHAT IS THIS DEVICE FOR?",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 0.8.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = device.devicePurpose,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Key Hardware Capabilities:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            device.keyCapabilities.split(",").forEach { cap ->
                                if (cap.isNotBlank()) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.surface,
                                        border = CardDefaults.outlinedCardBorder(enabled = true)
                                    ) {
                                        Text(
                                            text = "• ${cap.trim()}",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // SECTION 3: UNIFIED PROTOCOL EXPLANATION
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f)
                    ),
                    border = CardDefaults.outlinedCardBorder(enabled = true),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AppShortcut,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "UNIFIED ALLCONNECT PROTOCOL",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.secondary,
                                letterSpacing = 0.8.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = device.howItWorks,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // SECTION 4: HARDWARE & CONNECTION SPECS
                Text(
                    text = "SPECIFICATIONS & TELEMETRY",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 0.8.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SpecRow(label = "Manufacturer", value = "${device.manufacturer} (${device.manufacturerCountry})", icon = Icons.Default.Business)
                    SpecRow(label = "Connection Protocol", value = device.connectionProtocol.displayName, icon = Icons.Default.Lan)
                    SpecRow(label = "MAC Address", value = device.macAddress, icon = Icons.Default.VpnKey)
                    if (device.ipAddress.isNotBlank()) {
                        SpecRow(label = "Local IP Address", value = device.ipAddress, icon = Icons.Default.Wifi)
                    }
                    SpecRow(label = "Signal Strength", value = "${device.signalRssi} dBm (RSSI)", icon = Icons.Default.SignalCellularAlt)
                    if (device.batteryPercent >= 0) {
                        SpecRow(label = "Battery Charge", value = "${device.batteryPercent}%", icon = Icons.Default.BatteryFull)
                    } else {
                        SpecRow(label = "Power Source", value = "Continuous AC Power", icon = Icons.Default.PowerSettingsNew)
                    }
                    SpecRow(label = "Firmware Version", value = device.firmwareVersion, icon = Icons.Default.CheckCircle)
                    SpecRow(label = "Last Synced", value = formattedSyncTime, icon = Icons.Default.Refresh)
                }

                if (device.customNotes.isNotBlank() && !isEditing) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Notes: ${device.customNotes}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // SECTION 5: ACTIONS
                Text(
                    text = "DEVICE ACTIONS",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 0.8.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onSync,
                        modifier = Modifier.weight(1f).testTag("detail_sync_btn"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Sync Now", maxLines = 1)
                    }

                    OutlinedButton(
                        onClick = onRingFinder,
                        modifier = Modifier.weight(1f).testTag("detail_ring_btn"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Locate / Ring", maxLines = 1)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = { showDeleteConfirm = true },
                    modifier = Modifier.fillMaxWidth().testTag("detail_delete_btn"),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Disconnect & Remove from allConnect")
                }
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Disconnect ${device.name}?") },
            text = {
                Text("This will remove the device and its local telemetry from allConnect. You can re-scan and pair it at any time.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteConfirm = false
                        onRemoveDevice()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Disconnect")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// ==========================================
// 1. FITNESS TRACKER (FITBIT / BALCO) CARD
// ==========================================
@Composable
private fun FitnessTrackerControlCard(
    device: DeviceEntity,
    isFetching: Boolean,
    onFetch: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "heartbeat")
    val heartScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 650, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heart_pulse"
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        ),
        border = CardDefaults.outlinedCardBorder(enabled = true),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth().testTag("fitness_tracker_card")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Watch,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "LIVE BIOMETRICS & FITNESS TELEMETRY",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.8.sp
                    )
                }

                Surface(
                    color = ConnectGreen.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "GATT Connected",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = ConnectGreen,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Primary Metrics Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Heart Rate
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = CardDefaults.outlinedCardBorder(enabled = true)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color(0xFFE53935),
                            modifier = Modifier
                                .size(24.dp)
                                .scale(if (device.heartRateBpm > 0) heartScale else 1f)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (device.heartRateBpm > 0) "${device.heartRateBpm}" else "--",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "BPM (Heart Rate)",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        val zone = when {
                            device.heartRateBpm < 70 -> "Resting Zone"
                            device.heartRateBpm < 95 -> "Fat Burn Zone"
                            device.heartRateBpm < 120 -> "Cardio Zone"
                            else -> "Peak Zone"
                        }
                        Text(
                            text = zone,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (device.heartRateBpm >= 95) Color(0xFFE53935) else ConnectGreen,
                            fontSize = 10.sp
                        )
                    }
                }

                // Calories Burned
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = CardDefaults.outlinedCardBorder(enabled = true)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Whatshot,
                            contentDescription = null,
                            tint = Color(0xFFFF6D00),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (device.caloriesBurned > 0) "${device.caloriesBurned}" else "0",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Calories Burned",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "kcal active burn",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFFF6D00),
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Steps & Distance Progress
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = CardDefaults.outlinedCardBorder(enabled = true)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.DirectionsWalk, contentDescription = null, tint = ConnectCyan, modifier = Modifier.size(20.dp))
                            Text(
                                text = "Daily Steps & Activity",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "${String.format("%,d", device.stepCount)} / 10,000",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val stepProgress = (device.stepCount.toFloat() / 10000f).coerceIn(0f, 1f)
                    LinearProgressIndicator(
                        progress = { stepProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.primaryContainer
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Distance: ${String.format("%.2f", device.distanceKm)} km (${String.format("%.2f", device.distanceKm * 0.621371f)} mi)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Active: ${device.activeMinutes} mins",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Fetch live data button
            Button(
                onClick = onFetch,
                enabled = !isFetching,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("fetch_fitness_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                if (isFetching) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Fetching Heart Rate & Calories from ${device.manufacturer}...")
                } else {
                    Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("⚡ Fetch Live Fitness Data from ${device.name}")
                }
            }
        }
    }
}

// ==========================================
// 2. SMART HUB & CHILD NODES CONTROLLER
// ==========================================
@Composable
private fun SmartHubNodesControlCard(
    device: DeviceEntity,
    onToggleNode: (nodeId: String) -> Unit,
    onSetAllNodes: (turnAllOn: Boolean) -> Unit,
    onSetScene: (sceneName: String, colorHex: String) -> Unit
) {
    val childNodes = remember(device.hubChildNodesJson) {
        val list = mutableListOf<JSONObject>()
        try {
            val arr = JSONArray(device.hubChildNodesJson.ifBlank { "[]" })
            for (i in 0 until arr.length()) {
                list.add(arr.getJSONObject(i))
            }
        } catch (e: Exception) {
            // Ignore
        }
        list
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        ),
        border = CardDefaults.outlinedCardBorder(enabled = true),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth().testTag("smart_hub_control_card")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Hub,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "CONNECTED CHILD NODES (${childNodes.size})",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.8.sp
                    )
                }

                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Zigbee / Matter Mesh",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Quick Hub Actions: Turn All On / Turn All Off
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onSetAllNodes(true) },
                    modifier = Modifier.weight(1f).testTag("hub_all_on_btn"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Turn All ON", style = MaterialTheme.typography.labelMedium)
                }
                OutlinedButton(
                    onClick = { onSetAllNodes(false) },
                    modifier = Modifier.weight(1f).testTag("hub_all_off_btn"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Turn All OFF", style = MaterialTheme.typography.labelMedium)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Hub Scene Presets
            Text(
                text = "Scene Presets across all nodes:",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                HubSceneChip(title = "Cozy Warm", colorHex = "#FFB74D", onClick = { onSetScene("Cozy Warm", "#FFB74D") }, modifier = Modifier.weight(1f))
                HubSceneChip(title = "Daylight", colorHex = "#E0F7FA", onClick = { onSetScene("Daylight", "#E0F7FA") }, modifier = Modifier.weight(1f))
                HubSceneChip(title = "Relax Amber", colorHex = "#FFA726", onClick = { onSetScene("Relax Amber", "#FFA726") }, modifier = Modifier.weight(1f))
                HubSceneChip(title = "Cyber Neon", colorHex = "#00E5FF", onClick = { onSetScene("Cyber Neon", "#00E5FF") }, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Child Nodes List
            Text(
                text = "Individual Sub-Devices Controlled via Hub:",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                childNodes.forEach { node ->
                    val nodeId = node.optString("id", "")
                    val name = node.optString("name", "Child Device")
                    val type = node.optString("type", "light")
                    val room = node.optString("room", "")
                    val isOn = node.optBoolean("isOn", false)
                    val brightness = node.optInt("brightness", 80)
                    val colorHex = node.optString("colorHex", "#FFD54F")

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = CardDefaults.outlinedCardBorder(enabled = true)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isOn) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (type == "light") Icons.Default.Lightbulb else Icons.Default.Bolt,
                                        contentDescription = null,
                                        tint = if (isOn) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }

                                Column {
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = if (isOn) "$room • $brightness% Brightness" else "$room • Standby",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Switch(
                                checked = isOn,
                                onCheckedChange = { onToggleNode(nodeId) },
                                modifier = Modifier.testTag("node_toggle_$nodeId")
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HubSceneChip(
    title: String,
    colorHex: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp)),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Color(android.graphics.Color.parseColor(colorHex)))
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                fontSize = 10.sp
            )
        }
    }
}

// ==========================================
// 3. LIGHTING CONTROLLER CARD
// ==========================================
@Composable
private fun LightingControlCard(
    device: DeviceEntity,
    localBrightness: Float,
    onBrightnessChange: (Float) -> Unit,
    onSceneSelected: (sceneName: String, hex: String) -> Unit,
    onTogglePower: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
        border = CardDefaults.outlinedCardBorder(enabled = true),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth().testTag("lighting_control_card")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Text(
                        text = "LIGHTING CONTROLS",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Switch(
                    checked = device.powerState,
                    onCheckedChange = { onTogglePower() },
                    modifier = Modifier.testTag("light_power_switch")
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Brightness Slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Brightness Level", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                Text("${localBrightness.toInt()}%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
            }

            Slider(
                value = localBrightness,
                onValueChange = onBrightnessChange,
                valueRange = 0f..100f,
                modifier = Modifier.fillMaxWidth().testTag("brightness_slider")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Color Presets
            Text("Color & Atmosphere Presets:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(6.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                HubSceneChip(title = "Warm 2700K", colorHex = "#FFE082", onClick = { onSceneSelected("Warm White", "#FFE082") }, modifier = Modifier.weight(1f))
                HubSceneChip(title = "Daylight", colorHex = "#E1F5FE", onClick = { onSceneSelected("Cool Daylight", "#E1F5FE") }, modifier = Modifier.weight(1f))
                HubSceneChip(title = "Crimson", colorHex = "#FF5252", onClick = { onSceneSelected("Sunset Crimson", "#FF5252") }, modifier = Modifier.weight(1f))
                HubSceneChip(title = "Cyber Neon", colorHex = "#00E5FF", onClick = { onSceneSelected("Cyber Neon", "#00E5FF") }, modifier = Modifier.weight(1f))
            }
        }
    }
}

// ==========================================
// 4. CLIMATE CONTROLLER CARD
// ==========================================
@Composable
private fun ClimateControlCard(
    device: DeviceEntity,
    localTemp: Float,
    localMode: String,
    onTempChange: (Float) -> Unit,
    onModeChange: (String) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
        border = CardDefaults.outlinedCardBorder(enabled = true),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth().testTag("climate_control_card")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Thermostat, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Text(
                        text = "THERMOSTAT & CLIMATE CONTROL",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(6.dp)) {
                    Text(
                        text = localMode,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Temperature adjustment buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onTempChange((localTemp - 0.5f).coerceAtLeast(55f)) },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
                        .testTag("temp_minus_btn")
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrease temp")
                }

                Spacer(modifier = Modifier.width(20.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${String.format("%.1f", localTemp)}°",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Target Setpoint (Current: 71.4°F)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                IconButton(
                    onClick = { onTempChange((localTemp + 0.5f).coerceAtMost(85f)) },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
                        .testTag("temp_plus_btn")
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Increase temp")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Mode Selector
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("HEAT", "COOL", "ECO", "OFF").forEach { mode ->
                    val isSel = localMode == mode
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onModeChange(mode) }
                            .border(
                                width = if (isSel) 2.dp else 1.dp,
                                color = if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        color = if (isSel) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = mode,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSel) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(vertical = 8.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

// ==========================================
// 5. AUDIO SPEAKER CONTROLLER CARD
// ==========================================
@Composable
private fun AudioControlCard(
    device: DeviceEntity,
    localVolume: Float,
    isPlaying: Boolean,
    onVolumeChange: (Float) -> Unit,
    onTogglePlay: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
        border = CardDefaults.outlinedCardBorder(enabled = true),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth().testTag("audio_control_card")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.MusicNote, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Text("AUDIO STREAM & PLAYBACK", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                }

                Surface(color = if (isPlaying) ConnectGreen.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(6.dp)) {
                    Text(
                        text = if (isPlaying) "Playing" else "Paused",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isPlaying) ConnectGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Playback Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) { Icon(Icons.Default.SkipPrevious, contentDescription = "Previous") }
                Spacer(modifier = Modifier.width(10.dp))
                IconButton(
                    onClick = onTogglePlay,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .testTag("audio_play_pause_btn")
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                IconButton(onClick = {}) { Icon(Icons.Default.SkipNext, contentDescription = "Next") }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Volume
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Speaker Volume", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                Text("${localVolume.toInt()}%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
            Slider(
                value = localVolume,
                onValueChange = onVolumeChange,
                valueRange = 0f..100f,
                modifier = Modifier.fillMaxWidth().testTag("audio_volume_slider")
            )
        }
    }
}

// ==========================================
// 6. SECURITY LOCK CARD
// ==========================================
@Composable
private fun SecurityControlCard(
    device: DeviceEntity,
    onToggleLock: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
        border = CardDefaults.outlinedCardBorder(enabled = true),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth().testTag("security_control_card")
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = if (device.isLocked) Icons.Default.Lock else Icons.Default.LockOpen,
                contentDescription = null,
                tint = if (device.isLocked) ConnectGreen else Color(0xFFE53935),
                modifier = Modifier.size(44.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (device.isLocked) "SECURE & LOCKED" else "UNLOCKED / OPEN",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = if (device.isLocked) ConnectGreen else Color(0xFFE53935)
            )

            Text(
                text = "Dual AES-128 Hardware Encryption Guard Active",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onToggleLock,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (device.isLocked) MaterialTheme.colorScheme.error else ConnectGreen
                ),
                modifier = Modifier.fillMaxWidth().testTag("lock_toggle_btn")
            ) {
                Text(if (device.isLocked) "Tap to Unlock 🔓" else "Tap to Lock & Secure 🔒", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ==========================================
// 7. POWER RELAY / PLUG CARD
// ==========================================
@Composable
private fun PowerRelayControlCard(
    device: DeviceEntity,
    onTogglePower: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
        border = CardDefaults.outlinedCardBorder(enabled = true),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth().testTag("power_relay_card")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Icon(Icons.Default.Bolt, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                Column {
                    Text("SMART RELAY POWER", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text(
                        text = if (device.powerState) "Active Relay ON" else "Standby OFF",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(text = "Live Draw: ${device.primaryMetricValue}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Switch(
                checked = device.powerState,
                onCheckedChange = { onTogglePower() },
                modifier = Modifier.testTag("relay_power_switch")
            )
        }
    }
}

// ==========================================
// 8. GENERAL QUICK CONTROLLER CARD
// ==========================================
@Composable
private fun GeneralQuickControlCard(
    device: DeviceEntity,
    onTogglePower: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
        border = CardDefaults.outlinedCardBorder(enabled = true),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Icon(Icons.Default.PowerSettingsNew, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                Column {
                    Text(text = "PRIMARY POWER STATE", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text(
                        text = if (device.powerState) "Power ON" else "Power OFF",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Switch(
                checked = device.powerState,
                onCheckedChange = { onTogglePower() },
                modifier = Modifier.testTag("general_power_switch")
            )
        }
    }
}

@Composable
private fun SpecRow(label: String, value: String, icon: ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
