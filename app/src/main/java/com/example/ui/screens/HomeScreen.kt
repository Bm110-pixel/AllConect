package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.DeviceCategory
import com.example.ui.components.CatalogBrowserSheet
import com.example.ui.components.DeviceCard
import com.example.ui.components.DeviceDetailDialog
import com.example.ui.components.OnboardingDialog
import com.example.ui.components.PairingAssistantDialog
import com.example.ui.components.RadarScannerView
import com.example.ui.components.SettingsDialog
import com.example.ui.theme.ConnectCyan
import com.example.ui.theme.ConnectGreen
import com.example.ui.theme.ConnectIndigo
import com.example.ui.theme.ConnectPurple
import com.example.ui.theme.ConnectViolet
import com.example.ui.viewmodel.AllConnectViewModel
import com.example.ui.viewmodel.AppNavTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: AllConnectViewModel,
    modifier: Modifier = Modifier
) {
    val currentNavTab by viewModel.currentNavTab.collectAsStateWithLifecycle()
    val devices by viewModel.filteredDevices.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedDetailDevice by viewModel.selectedDeviceForDetail.collectAsStateWithLifecycle()
    val selectedCatalogItemForPairing by viewModel.selectedCatalogItemForPairing.collectAsStateWithLifecycle()
    val showRadarScanner by viewModel.showRadarScanner.collectAsStateWithLifecycle()
    val showCatalogBrowser by viewModel.showCatalogBrowser.collectAsStateWithLifecycle()
    val isScanning by viewModel.isScanning.collectAsStateWithLifecycle()
    val discoveredDevices by viewModel.discoveredDevices.collectAsStateWithLifecycle()
    val isSyncingAll by viewModel.isSyncingAll.collectAsStateWithLifecycle()
    val syncMessage by viewModel.syncMessage.collectAsStateWithLifecycle()
    val isFetchingFitnessData by viewModel.isFetchingFitnessData.collectAsStateWithLifecycle()

    // Preferences & Onboarding states
    val isOnboardingCompleted by viewModel.isOnboardingCompleted.collectAsStateWithLifecycle()
    val showSettingsDialog by viewModel.showSettingsDialog.collectAsStateWithLifecycle()
    val userName by viewModel.userName.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val unitSystem by viewModel.unitSystem.collectAsStateWithLifecycle()

    // Feedback states
    val featureRequests by viewModel.allFeatureRequests.collectAsStateWithLifecycle()
    val isProcessingWithGemini by viewModel.isProcessingWithGemini.collectAsStateWithLifecycle()
    val isSynthesizingCycle by viewModel.isSynthesizingCycle.collectAsStateWithLifecycle()
    val cycleSynthesisResult by viewModel.cycleSynthesisResult.collectAsStateWithLifecycle()

    var isSearchExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize().testTag("home_screen"),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primaryContainer)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Hub,
                                contentDescription = "allConnect Logo",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "all",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Light,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Connect",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = if (userName.isNotBlank()) "Welcome back, $userName" else if (currentNavTab == AppNavTab.FEEDBACK_ROADMAP) "Feedback & AI Roadmap" else "Universal Device Hub",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                letterSpacing = 0.5.sp,
                                maxLines = 1
                            )
                        }
                    }
                },
                actions = {
                    if (currentNavTab == AppNavTab.MY_HUB) {
                        IconButton(
                            onClick = { isSearchExpanded = !isSearchExpanded },
                            modifier = Modifier.testTag("toggle_search_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search devices",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        IconButton(
                            onClick = { viewModel.syncAll() },
                            enabled = !isSyncingAll,
                            modifier = Modifier.testTag("sync_all_btn")
                        ) {
                            if (isSyncingAll) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Sync,
                                    contentDescription = "Sync all devices",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    // Settings Dialog button
                    IconButton(
                        onClick = { viewModel.openSettings() },
                        modifier = Modifier.testTag("open_settings_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 4.dp
            ) {
                NavigationBarItem(
                    selected = currentNavTab == AppNavTab.MY_HUB,
                    onClick = { viewModel.selectNavTab(AppNavTab.MY_HUB) },
                    icon = { Icon(Icons.Default.Devices, contentDescription = "My Hub") },
                    label = { Text("My Hub", fontWeight = if (currentNavTab == AppNavTab.MY_HUB) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    modifier = Modifier.testTag("nav_hub_item")
                )
                NavigationBarItem(
                    selected = currentNavTab == AppNavTab.RADAR_SCAN,
                    onClick = { viewModel.openRadarScanner() },
                    icon = { Icon(Icons.Default.Radar, contentDescription = "Scan Radar") },
                    label = { Text("Radar Scan") },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_radar_item")
                )
                NavigationBarItem(
                    selected = currentNavTab == AppNavTab.SPECS_DIRECTORY,
                    onClick = { viewModel.openCatalogBrowser() },
                    icon = { Icon(Icons.Default.Explore, contentDescription = "Specs Directory") },
                    label = { Text("Specs") },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_specs_item")
                )
                NavigationBarItem(
                    selected = currentNavTab == AppNavTab.FEEDBACK_ROADMAP,
                    onClick = { viewModel.selectNavTab(AppNavTab.FEEDBACK_ROADMAP) },
                    icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "Feedback & AI Roadmap") },
                    label = { Text("Feedback AI", fontWeight = if (currentNavTab == AppNavTab.FEEDBACK_ROADMAP) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    modifier = Modifier.testTag("nav_feedback_item")
                )
            }
        },
        floatingActionButton = {
            if (currentNavTab == AppNavTab.MY_HUB) {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.openRadarScanner() },
                    icon = {
                        Icon(imageVector = Icons.Default.Radar, contentDescription = null)
                    },
                    text = {
                        Text("Scan Nearby", fontWeight = FontWeight.Bold)
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.testTag("scan_nearby_fab")
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Transient message alert / snackbar
            AnimatedVisibility(
                visible = syncMessage != null,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                syncMessage?.let { msg ->
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = msg,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { viewModel.clearSyncMessage() },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Dismiss",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            if (currentNavTab == AppNavTab.FEEDBACK_ROADMAP) {
                FeedbackRoadmapScreen(
                    featureRequests = featureRequests,
                    isProcessingWithGemini = isProcessingWithGemini,
                    isSynthesizingCycle = isSynthesizingCycle,
                    cycleSynthesisResult = cycleSynthesisResult,
                    onSubmitThought = { viewModel.submitFeedbackThought(it) },
                    onVote = { viewModel.voteOnFeature(it) },
                    onRun30DaySynthesis = { viewModel.run30DayCycleSynthesis() },
                    onDismissSynthesisDialog = { viewModel.dismissSynthesisDialog() },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Search Bar (Expanded or Collapsed)
                AnimatedVisibility(visible = isSearchExpanded) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { viewModel.setSearchQuery(it) },
                            placeholder = { Text("Search by name, model, manufacturer, room...") },
                            leadingIcon = {
                                Icon(Icons.Default.Search, contentDescription = null)
                            },
                            trailingIcon = {
                                if (searchQuery.isNotBlank()) {
                                    IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth().testTag("home_search_input")
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 88.dp, top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // HERO STATS & UNIFIED PROTOCOL BANNER
                    item {
                        HeroStatusCard(
                            userName = userName,
                            totalDevices = devices.size,
                            onScanRadarClick = { viewModel.openRadarScanner() },
                            onExploreCatalogClick = { viewModel.openCatalogBrowser() }
                        )
                    }

                    // CATEGORY FILTER CHIPS
                    item {
                        Column {
                            Text(
                                text = "DEVICE ECOSYSTEM",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                letterSpacing = 0.8.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(DeviceCategory.values()) { category ->
                                    FilterChip(
                                        selected = selectedCategory == category,
                                        onClick = { viewModel.selectCategory(category) },
                                        label = { Text(category.displayName, style = MaterialTheme.typography.labelSmall) },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = category.getIcon(),
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                                        ),
                                        modifier = Modifier.testTag("filter_chip_${category.name}")
                                    )
                                }
                            }
                        }
                    }

                    // SECTION HEADER
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "CONNECTED HARDWARE (${devices.size})",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                letterSpacing = 0.8.sp
                            )

                            Text(
                                text = "Tap card for controls & specs",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    // EMPTY STATE
                    if (devices.isEmpty()) {
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 24.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                ),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Devices,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "No devices match your filter",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Use the radar scanner or pick from the universal catalog to connect smart devices.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = { viewModel.openRadarScanner() },
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(Icons.Default.Radar, contentDescription = null)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Scan for Nearby Hardware")
                                    }
                                }
                            }
                        }
                    }

                    // CONNECTED DEVICE CARDS
                    items(devices, key = { it.id }) { device ->
                        DeviceCard(
                            device = device,
                            onCardClick = { viewModel.openDeviceDetail(device) },
                            onTogglePower = { viewModel.togglePower(device) },
                            onToggleFavorite = { viewModel.toggleFavorite(device) },
                            onSync = { viewModel.syncDevice(device) }
                        )
                    }
                }
            }
        }
    }

    // MODAL DIALOGS
    selectedDetailDevice?.let { device ->
        DeviceDetailDialog(
            device = device,
            isFetchingFitness = isFetchingFitnessData == device.id,
            onDismiss = { viewModel.closeDeviceDetail() },
            onSync = { viewModel.syncDevice(device) },
            onFetchFitnessData = { viewModel.fetchFitnessTrackerData(device) },
            onTogglePower = { viewModel.togglePower(device) },
            onRingFinder = { viewModel.ringDeviceFinder(device) },
            onUpdateLighting = { brightness, colorHex, scene, powerState ->
                viewModel.updateLighting(device, brightness, colorHex, scene, powerState)
            },
            onUpdateClimate = { temp, mode, fanSpeed ->
                viewModel.updateClimate(device, temp, mode, fanSpeed)
            },
            onUpdateAudio = { volume, playbackState, isMuted ->
                viewModel.updateAudio(device, volume, playbackState, isMuted)
            },
            onToggleLock = { viewModel.toggleLock(device) },
            onToggleHubNode = { nodeId -> viewModel.toggleHubNode(device, nodeId) },
            onSetAllHubNodes = { turnAllOn -> viewModel.setAllHubNodes(device, turnAllOn) },
            onSetHubScene = { scene, hex -> viewModel.setHubScene(device, scene, hex) },
            onUpdateDetails = { name, room, notes ->
                viewModel.updateDeviceDetails(device.id, name, room, notes)
            },
            onRemoveDevice = { viewModel.removeDevice(device) }
        )
    }

    if (showRadarScanner) {
        RadarScannerView(
            isScanning = isScanning,
            discoveredDevices = discoveredDevices,
            onDismiss = { viewModel.closeRadarScanner() },
            onRestartScan = { viewModel.startScanner() },
            onSelectDeviceToPair = { item ->
                viewModel.closeRadarScanner()
                viewModel.startPairing(item)
            },
            onOpenCatalog = {
                viewModel.closeRadarScanner()
                viewModel.openCatalogBrowser()
            }
        )
    }

    selectedCatalogItemForPairing?.let { item ->
        PairingAssistantDialog(
            item = item,
            onDismiss = { viewModel.closePairingAssistant() },
            onConfirmPair = { customName, customRoom ->
                viewModel.connectDiscoveredDevice(item, customName, customRoom)
            }
        )
    }

    if (showCatalogBrowser) {
        CatalogBrowserSheet(
            onDismiss = { viewModel.closeCatalogBrowser() },
            onSelectDeviceToPair = { item ->
                viewModel.closeCatalogBrowser()
                viewModel.startPairing(item)
            }
        )
    }

    // Onboarding Dialog Flow (First time launching app)
    if (!isOnboardingCompleted) {
        OnboardingDialog(
            onComplete = { name, theme, units ->
                viewModel.completeOnboarding(name, theme, units)
            }
        )
    }

    // Settings Dialog
    if (showSettingsDialog) {
        SettingsDialog(
            currentName = userName,
            currentTheme = themeMode,
            currentUnits = unitSystem,
            onDismiss = { viewModel.closeSettings() },
            onSave = { name, theme, units ->
                viewModel.updatePreferences(name, theme, units)
            }
        )
    }
}

@Composable
private fun HeroStatusCard(
    userName: String,
    totalDevices: Int,
    onScanRadarClick: () -> Unit,
    onExploreCatalogClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        border = CardDefaults.outlinedCardBorder(enabled = true),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(ConnectGreen)
                        )
                        Text(
                            text = "UNIVERSAL CONNECTION ACTIVE",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = ConnectGreen,
                            letterSpacing = 0.8.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (userName.isNotBlank()) "$userName's Smart Ecosystem" else "Unified Smart Hub",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = "$totalDevices Nodes",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Explanation box: Unified Protocol
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = CardDefaults.outlinedCardBorder(enabled = true),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Connect Fitbit, Balco trackers, Philips Hue, SmartThings hubs & IoT in one unified app.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 17.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onScanRadarClick,
                    modifier = Modifier.weight(1f).testTag("hero_radar_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(imageVector = Icons.Default.Radar, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Radar Scan", maxLines = 1, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = onExploreCatalogClick,
                    modifier = Modifier.weight(1f).testTag("hero_directory_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Icon(imageVector = Icons.Default.Explore, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Device Specs", maxLines = 1, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
