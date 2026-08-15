package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.DeviceCategory
import com.example.data.model.DeviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {
    @Query("SELECT * FROM devices ORDER BY isFavorite DESC, lastSyncTimestamp DESC")
    fun getAllDevices(): Flow<List<DeviceEntity>>

    @Query("SELECT * FROM devices WHERE id = :id")
    fun getDeviceById(id: String): Flow<DeviceEntity?>

    @Query("SELECT * FROM devices WHERE category = :category ORDER BY isFavorite DESC, name ASC")
    fun getDevicesByCategory(category: DeviceCategory): Flow<List<DeviceEntity>>

    @Query("SELECT * FROM devices WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoriteDevices(): Flow<List<DeviceEntity>>

    @Query("SELECT COUNT(*) FROM devices")
    suspend fun getDeviceCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(device: DeviceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(devices: List<DeviceEntity>)

    @Update
    suspend fun updateDevice(device: DeviceEntity)

    @Query("UPDATE devices SET powerState = :powerState, lastSyncTimestamp = :timestamp WHERE id = :id")
    suspend fun updatePowerState(id: String, powerState: Boolean, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE devices SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)

    @Query("UPDATE devices SET primaryMetricValue = :primaryValue, secondaryMetricValue = :secondaryValue, lastSyncTimestamp = :timestamp WHERE id = :id")
    suspend fun updateMetrics(id: String, primaryValue: String, secondaryValue: String, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE devices SET room = :room, name = :name, customNotes = :notes WHERE id = :id")
    suspend fun updateDetails(id: String, name: String, room: String, notes: String)

    @Query("UPDATE devices SET heartRateBpm = :bpm, caloriesBurned = :calories, stepCount = :steps, distanceKm = :distance, activeMinutes = :activeMins, primaryMetricValue = :primaryVal, secondaryMetricValue = :secondaryVal, lastSyncTimestamp = :timestamp WHERE id = :id")
    suspend fun updateFitnessTelemetry(
        id: String,
        bpm: Int,
        calories: Int,
        steps: Int,
        distance: Float,
        activeMins: Int,
        primaryVal: String,
        secondaryVal: String,
        timestamp: Long = System.currentTimeMillis()
    )

    @Query("UPDATE devices SET brightnessPercent = :brightness, colorHex = :colorHex, activeScene = :scene, powerState = :powerState WHERE id = :id")
    suspend fun updateLightingControls(id: String, brightness: Int, colorHex: String, scene: String, powerState: Boolean)

    @Query("UPDATE devices SET targetTemperature = :temperature, climateMode = :mode, fanSpeed = :fanSpeed WHERE id = :id")
    suspend fun updateClimateControls(id: String, temperature: Float, mode: String, fanSpeed: String)

    @Query("UPDATE devices SET volumePercent = :volume, playbackState = :playbackState, isMuted = :isMuted WHERE id = :id")
    suspend fun updateAudioControls(id: String, volume: Int, playbackState: String, isMuted: Boolean)

    @Query("UPDATE devices SET isLocked = :isLocked WHERE id = :id")
    suspend fun updateLockState(id: String, isLocked: Boolean)

    @Query("UPDATE devices SET hubChildNodesJson = :nodesJson WHERE id = :id")
    suspend fun updateHubChildNodes(id: String, nodesJson: String)

    @Delete
    suspend fun deleteDevice(device: DeviceEntity)

    @Query("DELETE FROM devices WHERE id = :id")
    suspend fun deleteDeviceById(id: String)
}
