package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.ConnectCyan
import com.example.ui.theme.ConnectGreen

data class TroubleshootingArticle(
    val id: String,
    val category: String,
    val title: String,
    val symptom: String,
    val icon: ImageVector,
    val steps: List<String>,
    val proTip: String
)

val TroubleshootingKnowledge: List<TroubleshootingArticle> = listOf(
    TroubleshootingArticle(
        id = "fitbit_not_discovered",
        category = "Wearables & Fitness",
        title = "Fitbit / Balco Tracker Not Appearing in Radar Scan",
        symptom = "When scanning for nearby devices, your Fitbit Charge/Versa/Sense or Balco fitness tracker doesn't show up in the discovered list.",
        icon = Icons.Default.Watch,
        steps = listOf(
            "Wake the tracker display: Tap the screen or press the haptic side button to take the tracker out of low-power sleep mode.",
            "Disconnect from other phones: Bluetooth LE devices can only maintain one active GATT bonding. Ensure the tracker is not locked to an old phone or tablet.",
            "Toggle Phone Bluetooth: Turn off Bluetooth in Android Quick Settings, wait 3 seconds, and turn it back on to flush the HCI cache.",
            "Check Location Permission: Android BLE scanning requires Location & Nearby Devices permissions to detect advertising packets.",
            "Place tracker within 1 meter: Bring the tracker adjacent to your phone during initial pairing."
        ),
        proTip = "If your Balco or Fitbit was previously paired with the official OEM app, unpair it from that app so allConnect can establish a direct, low-latency telemetry bridge."
    ),
    TroubleshootingArticle(
        id = "fitbit_telemetry_stuck",
        category = "Wearables & Fitness",
        title = "Heart Rate or Step Count Not Updating Live",
        symptom = "The tracker shows connected, but tapping 'Fetch Live Fitness Data' fails or keeps showing stale telemetry.",
        icon = Icons.Default.Watch,
        steps = listOf(
            "Ensure tracker sensor contact: Wear the band snugly against your wrist so the optical photoplethysmography (PPG) sensor detects pulse waveforms.",
            "Tap '⚡ Fetch Live Fitness Data': This prompts an immediate BLE GATT poll to the Heart Rate (0x180D) and Pedometer (0x1814) GATT services.",
            "Clean rear sensor contacts: Sweat and dust can attenuate optical LED reflections; wipe the rear sensor glass with a microfiber cloth.",
            "Reboot Tracker: Plug into the charging cradle and hold the button for 8 seconds until the logo appears to reset the embedded BLE stack."
        ),
        proTip = "allConnect queries standard BLE Health GATT profiles directly on-device without routing through external cloud servers, giving you true offline privacy."
    ),
    TroubleshootingArticle(
        id = "hub_discovery_failed",
        category = "Smart Home Hubs",
        title = "Smart Home Hub (Philips Hue, SmartThings, Google Home) Not Found",
        symptom = "The app scans on Wi-Fi but cannot find your local smart hub or Zigbee bridge.",
        icon = Icons.Default.Hub,
        steps = listOf(
            "Verify Same Wi-Fi Network: Ensure your phone is connected to the same Wi-Fi SSID and IP subnet as the smart hub (e.g. 192.168.1.xxx).",
            "Disable Router AP Isolation: Check your router settings to ensure 'Client Isolation' or 'AP Isolation' is disabled, which otherwise blocks local mDNS traffic.",
            "Press Bridge Link Button: For Philips Hue Bridges, press the large central circle button on top of the bridge to allow local API authorization.",
            "Check Ethernet Cable: Ensure the hub's LAN LED is solid blue/green, confirming active connection to your gateway router."
        ),
        proTip = "allConnect uses zero-config mDNS / SSDP discovery on port 5353. If your hub has a static IP, you can also connect directly via its IP address."
    ),
    TroubleshootingArticle(
        id = "hub_child_nodes_unresponsive",
        category = "Smart Home Hubs",
        title = "Child Devices / Bulbs Connected to Hub Unresponsive",
        symptom = "The main hub connects, but individual Zigbee/Matter child lights or plugs fail to toggle.",
        icon = Icons.Default.Hub,
        steps = listOf(
            "Check AC Power: Ensure the wall switch for the smart light is turned ON so the internal Zigbee radio receives continuous power.",
            "Mesh Range & Repeaters: Zigbee operates on a mesh network. If a bulb is in the backyard or garage, place a smart plug halfway to act as a repeater node.",
            "Use 'Turn All ON' / 'Turn All OFF': In allConnect's Smart Hub controller card, tapping 'Turn All ON' broadcasts a single mesh group command to resynchronize all node states."
        ),
        proTip = "Zigbee channel 15 or 20 minimizes interference with 2.4GHz Wi-Fi routers."
    ),
    TroubleshootingArticle(
        id = "identify_model_manufacturer",
        category = "Device Identification",
        title = "How to Identify Your Device's Manufacturer & Model Number",
        symptom = "You have an unbranded, legacy, or unmarked smart device and don't know what model or manufacturer it is.",
        icon = Icons.Default.QrCodeScanner,
        steps = listOf(
            "Check Rear / Underside Engravings: Look on the backplate, base, or inside the battery compartment. Manufacturers laser-engrave the Model # (e.g., 'FB417', 'LST002', 'STH-ETH-300') and FCC ID.",
            "Use allConnect Radar Scanner: Open 'Radar Scan' in allConnect. Our BLE scanner reads the Bluetooth SIG Assigned Numbers database (e.g. Vendor 0x000A = Fitbit, 0x004C = Apple, 0x0075 = Samsung) to identify the brand automatically!",
            "Look for the 12-Digit MAC Address: Format `XX:XX:XX:XX:XX:XX`. The first 6 characters (OUI) identify the exact hardware manufacturer registered with the IEEE.",
            "Inspect the QR / Setup Code: Matter and HomeKit accessories have an 8 or 11-digit pairing code printed alongside a QR code on the housing.",
            "Check the Original Power Adapter: Often the power brick lists the parent company brand even if the device itself is minimalist."
        ),
        proTip = "Once you know the model number, allConnect's 'Device Specs' directory contains pre-indexed capabilities, power requirements, and instructions for hundreds of devices."
    )
)

@Composable
fun TroubleshootingGuideDialog(
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "Wearables & Fitness", "Smart Home Hubs", "Device Identification")

    val filteredArticles = remember(searchQuery, selectedCategory) {
        TroubleshootingKnowledge.filter { article ->
            val matchesCat = selectedCategory == "All" || article.category == selectedCategory
            val matchesQuery = searchQuery.isBlank() ||
                    article.title.contains(searchQuery, ignoreCase = true) ||
                    article.symptom.contains(searchQuery, ignoreCase = true) ||
                    article.steps.any { it.contains(searchQuery, ignoreCase = true) } ||
                    article.proTip.contains(searchQuery, ignoreCase = true)
            matchesCat && matchesQuery
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .testTag("troubleshooting_guide_dialog"),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, start = 18.dp, end = 18.dp, bottom = 16.dp)
            ) {
                // Header
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
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Build,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "Troubleshooting & Guide",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "Solutions for Fitbit, Smart Hubs, & Device ID",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_troubleshoot_btn")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search troubleshooting issues (e.g. Fitbit, Hub, MAC)...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("troubleshoot_search_input")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Category Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, style = MaterialTheme.typography.labelSmall) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Articles list
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(filteredArticles, key = { it.id }) { article ->
                        TroubleshootingArticleCard(article = article)
                    }
                }
            }
        }
    }
}

@Composable
private fun TroubleshootingArticleCard(
    article: TroubleshootingArticle
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        ),
        border = CardDefaults.outlinedCardBorder(enabled = true),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = article.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = article.category.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            fontSize = 9.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Symptom Description
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface,
                border = CardDefaults.outlinedCardBorder(enabled = true),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "SYMPTOM & CAUSE:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = article.symptom,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Step-by-Step Solutions
            Text(
                text = "STEP-BY-STEP SOLUTION:",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                article.steps.forEachIndexed { index, step ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(20.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${index + 1}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Text(
                            text = step,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f),
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Pro Tip
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = ConnectCyan.copy(alpha = 0.15f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Pro Tip: ${article.proTip}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}
