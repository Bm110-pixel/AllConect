package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.knowledge.DeviceKnowledgeBase
import com.example.data.model.DeviceCatalogItem
import com.example.data.model.DeviceCategory
import com.example.data.model.DeviceEntity
import com.example.data.model.FeatureRequestEntity
import com.example.data.model.FeatureStatus
import com.example.data.network.GeminiCycleSynthesisResult
import com.example.data.network.GeminiFeedbackService
import com.example.data.network.GeminiThoughtResult
import com.example.data.preferences.ThemeMode
import com.example.data.preferences.UnitSystem
import com.example.data.preferences.UserPreferencesManager
import com.example.data.repository.DeviceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

enum class AppNavTab {
    MY_HUB,
    RADAR_SCAN,
    SPECS_DIRECTORY,
    FEEDBACK_ROADMAP
}

class AllConnectViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DeviceRepository
    private val geminiService = GeminiFeedbackService()
    private val prefsManager = UserPreferencesManager(application)

    // User Preferences & Settings States
    val userName: StateFlow<String> = prefsManager.userName
    val themeMode: StateFlow<ThemeMode> = prefsManager.themeMode
    val unitSystem: StateFlow<UnitSystem> = prefsManager.unitSystem
    val isOnboardingCompleted: StateFlow<Boolean> = prefsManager.isOnboardingCompleted

    private val _showSettingsDialog = MutableStateFlow(false)
    val showSettingsDialog: StateFlow<Boolean> = _showSettingsDialog.asStateFlow()

    private val _currentNavTab = MutableStateFlow(AppNavTab.MY_HUB)
    val currentNavTab: StateFlow<AppNavTab> = _currentNavTab.asStateFlow()

    private val _selectedCategory = MutableStateFlow(DeviceCategory.ALL)
    val selectedCategory: StateFlow<DeviceCategory> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val _discoveredDevices = MutableStateFlow<List<DeviceCatalogItem>>(emptyList())
    val discoveredDevices: StateFlow<List<DeviceCatalogItem>> = _discoveredDevices.asStateFlow()

    private val _selectedDeviceForDetail = MutableStateFlow<DeviceEntity?>(null)
    val selectedDeviceForDetail: StateFlow<DeviceEntity?> = _selectedDeviceForDetail.asStateFlow()

    private val _selectedCatalogItemForPairing = MutableStateFlow<DeviceCatalogItem?>(null)
    val selectedCatalogItemForPairing: StateFlow<DeviceCatalogItem?> = _selectedCatalogItemForPairing.asStateFlow()

    private val _showRadarScanner = MutableStateFlow(false)
    val showRadarScanner: StateFlow<Boolean> = _showRadarScanner.asStateFlow()

    private val _showCatalogBrowser = MutableStateFlow(false)
    val showCatalogBrowser: StateFlow<Boolean> = _showCatalogBrowser.asStateFlow()

    private val _syncMessage = MutableStateFlow<String?>(null)
    val syncMessage: StateFlow<String?> = _syncMessage.asStateFlow()

    private val _isSyncingAll = MutableStateFlow(false)
    val isSyncingAll: StateFlow<Boolean> = _isSyncingAll.asStateFlow()

    private val _isFetchingFitnessData = MutableStateFlow<String?>(null) // Device ID being fetched
    val isFetchingFitnessData: StateFlow<String?> = _isFetchingFitnessData.asStateFlow()

    // Feedback & Gemini Processing State
    val allFeatureRequests: StateFlow<List<FeatureRequestEntity>>

    private val _isProcessingWithGemini = MutableStateFlow(false)
    val isProcessingWithGemini: StateFlow<Boolean> = _isProcessingWithGemini.asStateFlow()

    private val _isSynthesizingCycle = MutableStateFlow(false)
    val isSynthesizingCycle: StateFlow<Boolean> = _isSynthesizingCycle.asStateFlow()

    private val _cycleSynthesisResult = MutableStateFlow<GeminiCycleSynthesisResult?>(null)
    val cycleSynthesisResult: StateFlow<GeminiCycleSynthesisResult?> = _cycleSynthesisResult.asStateFlow()

    private val _lastProcessedThought = MutableStateFlow<GeminiThoughtResult?>(null)
    val lastProcessedThought: StateFlow<GeminiThoughtResult?> = _lastProcessedThought.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        repository = DeviceRepository(database.deviceDao(), database.featureRequestDao())

        allFeatureRequests = repository.allFeatureRequests.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DeviceKnowledgeBase.getInitialFeatureRequests()
        )

        // Check if database is empty on fresh start and populate
        viewModelScope.launch {
            if (repository.getDeviceCount() == 0) {
                val initialList = DeviceKnowledgeBase.getInitialDevices()
                for (dev in initialList) {
                    repository.insertDevice(dev)
                }
            }
            if (repository.getFeatureRequestCount() == 0) {
                val initialRequests = DeviceKnowledgeBase.getInitialFeatureRequests()
                repository.insertAllFeatureRequests(initialRequests)
            }
        }
    }

    val filteredDevices: StateFlow<List<DeviceEntity>> = combine(
        repository.allDevices,
        _selectedCategory,
        _searchQuery
    ) { devices, category, query ->
        devices.filter { device ->
            val matchesCategory = (category == DeviceCategory.ALL || device.category == category)
            val matchesQuery = query.isBlank() ||
                    device.name.contains(query, ignoreCase = true) ||
                    device.modelName.contains(query, ignoreCase = true) ||
                    device.manufacturer.contains(query, ignoreCase = true) ||
                    device.room.contains(query, ignoreCase = true) ||
                    device.devicePurpose.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // ==========================================
    // Onboarding & Preferences Methods
    // ==========================================

    fun completeOnboarding(name: String, theme: ThemeMode, unit: UnitSystem) {
        prefsManager.completeOnboarding(name, theme, unit)
        showTransientMessage("Welcome to allConnect, ${name.ifBlank { "User" }}!")
    }

    fun setUserName(name: String) {
        prefsManager.setUserName(name)
    }

    fun setThemeMode(mode: ThemeMode) {
        prefsManager.setThemeMode(mode)
        showTransientMessage("Theme set to ${mode.name.lowercase().replaceFirstChar { it.uppercase() }}")
    }

    fun setUnitSystem(unit: UnitSystem) {
        prefsManager.setUnitSystem(unit)
    }

    fun updatePreferences(name: String, theme: ThemeMode, unit: UnitSystem) {
        prefsManager.setUserName(name)
        prefsManager.setThemeMode(theme)
        prefsManager.setUnitSystem(unit)
        showTransientMessage("✓ Preferences updated")
    }

    fun openSettings() {
        _showSettingsDialog.value = true
    }

    fun closeSettings() {
        _showSettingsDialog.value = false
    }

    // ==========================================
    // Navigation & View Actions
    // ==========================================

    fun selectNavTab(tab: AppNavTab) {
        _currentNavTab.value = tab
        if (tab == AppNavTab.RADAR_SCAN) {
            openRadarScanner()
        } else if (tab == AppNavTab.SPECS_DIRECTORY) {
            openCatalogBrowser()
        }
    }

    fun selectCategory(category: DeviceCategory) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun openDeviceDetail(device: DeviceEntity) {
        _selectedDeviceForDetail.value = device
    }

    fun closeDeviceDetail() {
        _selectedDeviceForDetail.value = null
    }

    fun openRadarScanner() {
        _showRadarScanner.value = true
        startScanner()
    }

    fun closeRadarScanner() {
        _showRadarScanner.value = false
        _isScanning.value = false
    }

    fun openCatalogBrowser() {
        _showCatalogBrowser.value = true
    }

    fun closeCatalogBrowser() {
        _showCatalogBrowser.value = false
    }

    fun startPairing(catalogItem: DeviceCatalogItem) {
        _selectedCatalogItemForPairing.value = catalogItem
    }

    fun closePairingAssistant() {
        _selectedCatalogItemForPairing.value = null
    }

    // ==========================================
    // Fitness Tracker Data Fetching (Fitbit / Balco)
    // ==========================================

    fun fetchFitnessTrackerData(device: DeviceEntity) {
        viewModelScope.launch {
            _isFetchingFitnessData.value = device.id
            showTransientMessage("⚡ Querying ${device.name} via BLE GATT (0x180D Heart Rate & 0x1814 Pedometer)...")
            delay(1100)

            // Calculate updated telemetry
            val baseSteps = if (device.stepCount > 0) device.stepCount else 7400
            val addedSteps = (45..190).random()
            val newSteps = baseSteps + addedSteps

            val newBpm = (68..115).random()
            val baseCalories = if (device.caloriesBurned > 0) device.caloriesBurned else 510
            val newCalories = baseCalories + (12..35).random()

            val newDistance = (newSteps * 0.00075f)
            val newActiveMins = device.activeMinutes + (1..3).random()

            val primaryVal = "${String.format("%,d", newSteps)} steps"
            val secondaryVal = "$newBpm bpm • $newCalories kcal"

            repository.updateFitnessTelemetry(
                id = device.id,
                bpm = newBpm,
                calories = newCalories,
                steps = newSteps,
                distance = newDistance,
                activeMins = newActiveMins,
                primaryVal = primaryVal,
                secondaryVal = secondaryVal
            )

            // Update in-memory dialog state if open
            _selectedDeviceForDetail.value?.let { current ->
                if (current.id == device.id) {
                    _selectedDeviceForDetail.value = current.copy(
                        heartRateBpm = newBpm,
                        caloriesBurned = newCalories,
                        stepCount = newSteps,
                        distanceKm = newDistance,
                        activeMinutes = newActiveMins,
                        primaryMetricValue = primaryVal,
                        secondaryMetricValue = secondaryVal,
                        lastSyncTimestamp = System.currentTimeMillis()
                    )
                }
            }

            _isFetchingFitnessData.value = null
            showTransientMessage("✓ Fetched from ${device.name}: $newBpm BPM • $newCalories kcal • ${String.format("%,d", newSteps)} steps")
        }
    }

    // ==========================================
    // Device Controls (Smart Hubs, Lighting, Climate, Audio, Power, Security)
    // ==========================================

    fun togglePower(device: DeviceEntity) {
        viewModelScope.launch {
            val newState = !device.powerState
            repository.togglePower(device.id, device.powerState)
            _selectedDeviceForDetail.value?.let { current ->
                if (current.id == device.id) {
                    _selectedDeviceForDetail.value = current.copy(powerState = newState)
                }
            }
            showTransientMessage("${device.name} switched ${if (newState) "ON" else "OFF"}")
        }
    }

    fun toggleFavorite(device: DeviceEntity) {
        viewModelScope.launch {
            repository.toggleFavorite(device.id, device.isFavorite)
        }
    }

    fun updateLighting(device: DeviceEntity, brightness: Int, colorHex: String, scene: String, powerState: Boolean = true) {
        viewModelScope.launch {
            repository.updateLightingControls(device.id, brightness, colorHex, scene, powerState)
            _selectedDeviceForDetail.value?.let { current ->
                if (current.id == device.id) {
                    _selectedDeviceForDetail.value = current.copy(
                        brightnessPercent = brightness,
                        colorHex = colorHex,
                        activeScene = scene,
                        powerState = powerState
                    )
                }
            }
            showTransientMessage("${device.name}: Set to $brightness% • $scene")
        }
    }

    fun updateClimate(device: DeviceEntity, targetTemp: Float, mode: String, fanSpeed: String = "AUTO") {
        viewModelScope.launch {
            repository.updateClimateControls(device.id, targetTemp, mode, fanSpeed)
            _selectedDeviceForDetail.value?.let { current ->
                if (current.id == device.id) {
                    _selectedDeviceForDetail.value = current.copy(
                        targetTemperature = targetTemp,
                        climateMode = mode,
                        fanSpeed = fanSpeed,
                        primaryMetricValue = String.format("%.1f°F (%s)", targetTemp, mode)
                    )
                }
            }
            showTransientMessage("${device.name}: Target set to ${String.format("%.1f", targetTemp)}°F ($mode)")
        }
    }

    fun updateAudio(device: DeviceEntity, volume: Int, playbackState: String, isMuted: Boolean = false) {
        viewModelScope.launch {
            repository.updateAudioControls(device.id, volume, playbackState, isMuted)
            _selectedDeviceForDetail.value?.let { current ->
                if (current.id == device.id) {
                    _selectedDeviceForDetail.value = current.copy(
                        volumePercent = volume,
                        playbackState = playbackState,
                        isMuted = isMuted,
                        primaryMetricValue = if (isMuted) "Muted" else "$volume% Volume"
                    )
                }
            }
        }
    }

    fun toggleLock(device: DeviceEntity) {
        viewModelScope.launch {
            val newLocked = !device.isLocked
            repository.updateLockState(device.id, newLocked)
            _selectedDeviceForDetail.value?.let { current ->
                if (current.id == device.id) {
                    _selectedDeviceForDetail.value = current.copy(isLocked = newLocked)
                }
            }
            showTransientMessage("${device.name}: ${if (newLocked) "Locked & Secured 🔒" else "Unlocked 🔓"}")
        }
    }

    // Hub Sub-Nodes Control
    fun toggleHubNode(hubDevice: DeviceEntity, nodeId: String) {
        viewModelScope.launch {
            try {
                val jsonArray = JSONArray(hubDevice.hubChildNodesJson.ifBlank { "[]" })
                var updatedNodeName = "Sub-device"
                var newNodeState = false
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    if (obj.optString("id") == nodeId) {
                        val currentOn = obj.optBoolean("isOn", false)
                        newNodeState = !currentOn
                        obj.put("isOn", newNodeState)
                        updatedNodeName = obj.optString("name", "Device")
                        break
                    }
                }
                val newJson = jsonArray.toString()
                repository.updateHubChildNodes(hubDevice.id, newJson)
                _selectedDeviceForDetail.value?.let { current ->
                    if (current.id == hubDevice.id) {
                        _selectedDeviceForDetail.value = current.copy(hubChildNodesJson = newJson)
                    }
                }
                showTransientMessage("$updatedNodeName turned ${if (newNodeState) "ON" else "OFF"}")
            } catch (e: Exception) {
                // Ignore parse errors
            }
        }
    }

    fun setAllHubNodes(hubDevice: DeviceEntity, turnAllOn: Boolean) {
        viewModelScope.launch {
            try {
                val jsonArray = JSONArray(hubDevice.hubChildNodesJson.ifBlank { "[]" })
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    if (obj.optString("type") == "light" || obj.optString("type") == "plug") {
                        obj.put("isOn", turnAllOn)
                    }
                }
                val newJson = jsonArray.toString()
                repository.updateHubChildNodes(hubDevice.id, newJson)
                _selectedDeviceForDetail.value?.let { current ->
                    if (current.id == hubDevice.id) {
                        _selectedDeviceForDetail.value = current.copy(hubChildNodesJson = newJson)
                    }
                }
                showTransientMessage("All lights connected to ${hubDevice.name} turned ${if (turnAllOn) "ON" else "OFF"}")
            } catch (e: Exception) {
                // Ignore parse errors
            }
        }
    }

    fun setHubScene(hubDevice: DeviceEntity, sceneName: String, colorHex: String, brightness: Int = 80) {
        viewModelScope.launch {
            try {
                val jsonArray = JSONArray(hubDevice.hubChildNodesJson.ifBlank { "[]" })
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    if (obj.optString("type") == "light") {
                        obj.put("isOn", true)
                        obj.put("brightness", brightness)
                        obj.put("colorHex", colorHex)
                    }
                }
                val newJson = jsonArray.toString()
                repository.updateHubChildNodes(hubDevice.id, newJson)
                _selectedDeviceForDetail.value?.let { current ->
                    if (current.id == hubDevice.id) {
                        _selectedDeviceForDetail.value = current.copy(
                            hubChildNodesJson = newJson,
                            activeScene = sceneName,
                            colorHex = colorHex
                        )
                    }
                }
                showTransientMessage("${hubDevice.name}: Activated scene \"$sceneName\" across all lights")
            } catch (e: Exception) {
                // Ignore
            }
        }
    }

    fun syncDevice(device: DeviceEntity) {
        viewModelScope.launch {
            if (device.category == DeviceCategory.WEARABLE) {
                fetchFitnessTrackerData(device)
                return@launch
            }

            showTransientMessage("Connecting & synchronizing ${device.name}...")
            delay(800)

            when (device.category) {
                DeviceCategory.SMART_HUB -> {
                    val nodes = (12..18).random()
                    repository.updateMetrics(
                        device.id,
                        "$nodes Bulbs Active",
                        "Zigbee Mesh (99.8% Reliability)"
                    )
                }
                DeviceCategory.CLIMATE -> {
                    val temp = (69..73).random()
                    val rh = (40..50).random()
                    repository.updateMetrics(
                        device.id,
                        "$temp.0°F (Auto)",
                        "$temp.2°F • $rh% RH"
                    )
                }
                DeviceCategory.POWER_ENERGY -> {
                    val watts = (110..160).random() + (0..9).random() / 10.0
                    val kwh = 0.75 + (1..20).random() / 100.0
                    repository.updateMetrics(
                        device.id,
                        "$watts W (Live)",
                        String.format("%.2f kWh ($%.2f)", kwh, kwh * 0.13)
                    )
                }
                else -> {
                    repository.updateMetrics(device.id, device.primaryMetricValue, "Refreshed via BLE/LAN")
                }
            }
            showTransientMessage("✓ ${device.name} synchronized successfully!")
        }
    }

    fun syncAll() {
        viewModelScope.launch {
            _isSyncingAll.value = true
            showTransientMessage("Syncing all devices across BLE & Local Network...")
            delay(1200)
            val currentList = filteredDevices.value
            for (dev in currentList) {
                if (dev.category == DeviceCategory.WEARABLE) {
                    val newSteps = dev.stepCount + (20..80).random()
                    val newBpm = (68..95).random()
                    val newCal = dev.caloriesBurned + (5..20).random()
                    repository.updateFitnessTelemetry(
                        id = dev.id,
                        bpm = newBpm,
                        calories = newCal,
                        steps = newSteps,
                        distance = newSteps * 0.00075f,
                        activeMins = dev.activeMinutes + 1,
                        primaryVal = "${String.format("%,d", newSteps)} steps",
                        secondaryVal = "$newBpm bpm • $newCal kcal"
                    )
                } else {
                    syncDevice(dev)
                }
            }
            _isSyncingAll.value = false
            showTransientMessage("✓ All ${currentList.size} devices synchronized!")
        }
    }

    fun ringDeviceFinder(device: DeviceEntity) {
        viewModelScope.launch {
            showTransientMessage("🔊 Beeping ${device.name} speaker / haptic buzzer...")
        }
    }

    fun startScanner() {
        viewModelScope.launch {
            _isScanning.value = true
            _discoveredDevices.value = emptyList()

            val candidates = DeviceKnowledgeBase.getDiscoveredCandidates()
            for (candidate in candidates) {
                delay(800)
                if (!_isScanning.value) break
                val current = _discoveredDevices.value.toMutableList()
                if (!current.contains(candidate)) {
                    current.add(candidate)
                    _discoveredDevices.value = current
                }
            }
            _isScanning.value = false
        }
    }

    fun connectDiscoveredDevice(item: DeviceCatalogItem, customName: String = "", customRoom: String = "") {
        viewModelScope.launch {
            showTransientMessage("Connecting & identifying ${item.name} (${item.manufacturer})...")
            delay(800)

            val randomMac = String.format(
                "%02X:%02X:%02X:%02X:%02X:%02X",
                (0..255).random(), (0..255).random(), (0..255).random(),
                (0..255).random(), (0..255).random(), (0..255).random()
            )
            val randomIp = "192.168.1." + (100..240).random()

            val newEntity = DeviceEntity(
                id = "dev-" + UUID.randomUUID().toString().take(8),
                name = customName.ifBlank { item.name },
                modelName = item.modelName,
                manufacturer = item.manufacturer,
                manufacturerCountry = item.manufacturerCountry,
                category = item.category,
                devicePurpose = item.devicePurpose,
                keyCapabilities = item.keyCapabilities.joinToString(", "),
                howItWorks = item.howItWorks,
                connectionProtocol = item.defaultProtocol,
                macAddress = randomMac,
                ipAddress = randomIp,
                room = customRoom.ifBlank { item.defaultRoom },
                isOnline = true,
                isFavorite = false,
                signalRssi = -1 * (45..75).random(),
                batteryPercent = if (item.defaultBatteryPowered) item.typicalBatteryPct else -1,
                firmwareVersion = "v" + (1..3).random() + "." + (0..9).random() + "." + (0..9).random(),
                lastSyncTimestamp = System.currentTimeMillis(),
                powerState = true,
                primaryMetricLabel = item.samplePrimaryLabel,
                primaryMetricValue = item.samplePrimaryValue,
                secondaryMetricLabel = item.sampleSecondaryLabel,
                secondaryMetricValue = item.sampleSecondaryValue,
                customNotes = "Connected via allConnect Universal Protocol Engine.",
                heartRateBpm = if (item.category == DeviceCategory.WEARABLE) (70..85).random() else 0,
                caloriesBurned = if (item.category == DeviceCategory.WEARABLE) (400..700).random() else 0,
                stepCount = if (item.category == DeviceCategory.WEARABLE) (6000..9500).random() else 0,
                distanceKm = if (item.category == DeviceCategory.WEARABLE) 5.2f else 0f
            )

            repository.insertDevice(newEntity)

            // Remove from discovered list
            val updated = _discoveredDevices.value.filter { it.id != item.id }
            _discoveredDevices.value = updated

            closePairingAssistant()
            showTransientMessage("✓ Added ${newEntity.name} to your unified dashboard!")
        }
    }

    fun updateDeviceDetails(id: String, name: String, room: String, notes: String) {
        viewModelScope.launch {
            repository.updateDetails(id, name, room, notes)
            // Update the selected detail object too
            _selectedDeviceForDetail.value?.let { current ->
                if (current.id == id) {
                    _selectedDeviceForDetail.value = current.copy(name = name, room = room, customNotes = notes)
                }
            }
            showTransientMessage("Device details updated.")
        }
    }

    fun removeDevice(device: DeviceEntity) {
        viewModelScope.launch {
            repository.deleteDeviceById(device.id)
            closeDeviceDetail()
            showTransientMessage("Disconnected and removed ${device.name}")
        }
    }

    // ==========================================
    // Gemini Feedback & Community Roadmap Engine
    // ==========================================

    fun submitFeedbackThought(thought: String, onComplete: () -> Unit = {}) {
        if (thought.isBlank()) return
        viewModelScope.launch {
            _isProcessingWithGemini.value = true
            showTransientMessage("Gemini is analyzing your feature thought & architecture...")

            val result = geminiService.processUserThought(thought)
            _lastProcessedThought.value = result

            val newEntity = FeatureRequestEntity(
                id = "req-" + UUID.randomUUID().toString().take(8),
                title = result.title,
                rawUserThought = thought,
                aiSynthesis = result.aiSynthesis,
                technicalFeasibility = result.technicalFeasibility,
                protocolsInvolved = result.protocolsInvolved,
                category = result.category,
                requestVotes = 1,
                userHasVoted = true,
                status = FeatureStatus.PROCESSING_BY_GEMINI,
                gatherCycleMonth = "Cycle 8 (Current 30-Day Window)",
                submittedTimestamp = System.currentTimeMillis(),
                isWinningFeature = false,
                engineeringNotes = "Estimated effort: ${result.estimatedEffort}. Queued into active 30-day community poll."
            )

            repository.insertFeatureRequest(newEntity)
            _isProcessingWithGemini.value = false
            showTransientMessage("✓ Thought processed by Gemini and added to 30-day gathering cycle!")
            onComplete()
        }
    }

    fun voteOnFeature(request: FeatureRequestEntity) {
        viewModelScope.launch {
            repository.toggleFeatureVote(request.id, request.userHasVoted)
            val action = if (!request.userHasVoted) "Upvoted" else "Removed vote from"
            showTransientMessage("$action \"${request.title}\"")
        }
    }

    fun run30DayCycleSynthesis() {
        viewModelScope.launch {
            _isSynthesizingCycle.value = true
            showTransientMessage("Processing 30 days of community thoughts with Gemini...")

            val currentRequests = allFeatureRequests.value
            val result = geminiService.synthesize30DayCycle(currentRequests)
            _cycleSynthesisResult.value = result

            if (result.winningFeatureId.isNotBlank()) {
                repository.setWinningFeature(result.winningFeatureId)
            }

            _isSynthesizingCycle.value = false
            showTransientMessage("🏆 30-Day Cycle Complete! \"${result.winningFeatureTitle}\" has been ADDED to AllConnect!")
        }
    }

    fun dismissSynthesisDialog() {
        _cycleSynthesisResult.value = null
    }

    private fun showTransientMessage(msg: String) {
        _syncMessage.value = msg
        viewModelScope.launch {
            delay(3500)
            if (_syncMessage.value == msg) {
                _syncMessage.value = null
            }
        }
    }

    fun clearSyncMessage() {
        _syncMessage.value = null
    }
}

