package com.example.data.db

import androidx.room.TypeConverter
import com.example.data.model.ConnectionProtocol
import com.example.data.model.DeviceCategory

class Converters {
    @TypeConverter
    fun fromCategory(category: DeviceCategory?): String {
        return category?.name ?: DeviceCategory.ALL.name
    }

    @TypeConverter
    fun toCategory(value: String?): DeviceCategory {
        return try {
            if (value != null) DeviceCategory.valueOf(value) else DeviceCategory.ALL
        } catch (e: Exception) {
            DeviceCategory.ALL
        }
    }

    @TypeConverter
    fun fromProtocol(protocol: ConnectionProtocol?): String {
        return protocol?.name ?: ConnectionProtocol.WIFI_LOCAL.name
    }

    @TypeConverter
    fun toProtocol(value: String?): ConnectionProtocol {
        return try {
            if (value != null) ConnectionProtocol.valueOf(value) else ConnectionProtocol.WIFI_LOCAL
        } catch (e: Exception) {
            ConnectionProtocol.WIFI_LOCAL
        }
    }

    @TypeConverter
    fun fromFeatureStatus(status: com.example.data.model.FeatureStatus?): String {
        return status?.name ?: com.example.data.model.FeatureStatus.GATHERING.name
    }

    @TypeConverter
    fun toFeatureStatus(value: String?): com.example.data.model.FeatureStatus {
        return try {
            if (value != null) com.example.data.model.FeatureStatus.valueOf(value) else com.example.data.model.FeatureStatus.GATHERING
        } catch (e: Exception) {
            com.example.data.model.FeatureStatus.GATHERING
        }
    }
}
