package com.example.data.repository

import com.example.data.db.DeviceDao
import com.example.data.db.FeatureRequestDao
import com.example.data.model.DeviceCategory
import com.example.data.model.DeviceEntity
import com.example.data.model.FeatureRequestEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DeviceRepository(
    private val deviceDao: DeviceDao,
    private val featureRequestDao: FeatureRequestDao
) {

    val allDevices: Flow<List<DeviceEntity>> = deviceDao.getAllDevices()
    val favoriteDevices: Flow<List<DeviceEntity>> = deviceDao.getFavoriteDevices()
    val allFeatureRequests: Flow<List<FeatureRequestEntity>> = featureRequestDao.getAllRequests()

    fun getDeviceById(id: String): Flow<DeviceEntity?> = deviceDao.getDeviceById(id)

    fun getDevicesByCategory(category: DeviceCategory): Flow<List<DeviceEntity>> {
        return if (category == DeviceCategory.ALL) {
            deviceDao.getAllDevices()
        } else {
            deviceDao.getDevicesByCategory(category)
        }
    }

    suspend fun insertDevice(device: DeviceEntity) = withContext(Dispatchers.IO) {
        deviceDao.insertDevice(device)
    }

    suspend fun updateDevice(device: DeviceEntity) = withContext(Dispatchers.IO) {
        deviceDao.updateDevice(device)
    }

    suspend fun togglePower(id: String, currentState: Boolean) = withContext(Dispatchers.IO) {
        deviceDao.updatePowerState(id, !currentState)
    }

    suspend fun toggleFavorite(id: String, currentState: Boolean) = withContext(Dispatchers.IO) {
        deviceDao.updateFavorite(id, !currentState)
    }

    suspend fun updateMetrics(id: String, primaryValue: String, secondaryValue: String) = withContext(Dispatchers.IO) {
        deviceDao.updateMetrics(id, primaryValue, secondaryValue)
    }

    suspend fun updateDetails(id: String, name: String, room: String, notes: String) = withContext(Dispatchers.IO) {
        deviceDao.updateDetails(id, name, room, notes)
    }

    suspend fun updateFitnessTelemetry(
        id: String,
        bpm: Int,
        calories: Int,
        steps: Int,
        distance: Float,
        activeMins: Int,
        primaryVal: String,
        secondaryVal: String
    ) = withContext(Dispatchers.IO) {
        deviceDao.updateFitnessTelemetry(
            id = id,
            bpm = bpm,
            calories = calories,
            steps = steps,
            distance = distance,
            activeMins = activeMins,
            primaryVal = primaryVal,
            secondaryVal = secondaryVal
        )
    }

    suspend fun updateLightingControls(id: String, brightness: Int, colorHex: String, scene: String, powerState: Boolean) = withContext(Dispatchers.IO) {
        deviceDao.updateLightingControls(id, brightness, colorHex, scene, powerState)
    }

    suspend fun updateClimateControls(id: String, temperature: Float, mode: String, fanSpeed: String) = withContext(Dispatchers.IO) {
        deviceDao.updateClimateControls(id, temperature, mode, fanSpeed)
    }

    suspend fun updateAudioControls(id: String, volume: Int, playbackState: String, isMuted: Boolean) = withContext(Dispatchers.IO) {
        deviceDao.updateAudioControls(id, volume, playbackState, isMuted)
    }

    suspend fun updateLockState(id: String, isLocked: Boolean) = withContext(Dispatchers.IO) {
        deviceDao.updateLockState(id, isLocked)
    }

    suspend fun updateHubChildNodes(id: String, nodesJson: String) = withContext(Dispatchers.IO) {
        deviceDao.updateHubChildNodes(id, nodesJson)
    }

    suspend fun deleteDeviceById(id: String) = withContext(Dispatchers.IO) {
        deviceDao.deleteDeviceById(id)
    }

    suspend fun getDeviceCount(): Int = withContext(Dispatchers.IO) {
        deviceDao.getDeviceCount()
    }

    // Feature Requests / Feedback methods
    suspend fun insertFeatureRequest(request: FeatureRequestEntity) = withContext(Dispatchers.IO) {
        featureRequestDao.insert(request)
    }

    suspend fun insertAllFeatureRequests(requests: List<FeatureRequestEntity>) = withContext(Dispatchers.IO) {
        featureRequestDao.insertAll(requests)
    }

    suspend fun getFeatureRequestCount(): Int = withContext(Dispatchers.IO) {
        featureRequestDao.getRequestCount()
    }

    suspend fun toggleFeatureVote(id: String, currentHasVoted: Boolean) = withContext(Dispatchers.IO) {
        val increment = if (currentHasVoted) -1 else 1
        featureRequestDao.updateVote(id, increment, !currentHasVoted)
    }

    suspend fun setWinningFeature(winningId: String) = withContext(Dispatchers.IO) {
        featureRequestDao.resetWinningFeatures()
        featureRequestDao.setWinningFeature(winningId)
    }
}

