package com.example.data.preferences

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}

enum class UnitSystem {
    IMPERIAL, // °F, miles
    METRIC    // °C, km
}

class UserPreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("allconnect_prefs", Context.MODE_PRIVATE)

    private val _userName = MutableStateFlow(prefs.getString("user_name", "") ?: "")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _themeMode = MutableStateFlow(
        try {
            ThemeMode.valueOf(prefs.getString("theme_mode", ThemeMode.SYSTEM.name) ?: ThemeMode.SYSTEM.name)
        } catch (e: Exception) {
            ThemeMode.SYSTEM
        }
    )
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _unitSystem = MutableStateFlow(
        try {
            UnitSystem.valueOf(prefs.getString("unit_system", UnitSystem.IMPERIAL.name) ?: UnitSystem.IMPERIAL.name)
        } catch (e: Exception) {
            UnitSystem.IMPERIAL
        }
    )
    val unitSystem: StateFlow<UnitSystem> = _unitSystem.asStateFlow()

    private val _isOnboardingCompleted = MutableStateFlow(prefs.getBoolean("onboarding_completed", false))
    val isOnboardingCompleted: StateFlow<Boolean> = _isOnboardingCompleted.asStateFlow()

    fun setUserName(name: String) {
        prefs.edit().putString("user_name", name.trim()).apply()
        _userName.value = name.trim()
    }

    fun setThemeMode(mode: ThemeMode) {
        prefs.edit().putString("theme_mode", mode.name).apply()
        _themeMode.value = mode
    }

    fun setUnitSystem(unit: UnitSystem) {
        prefs.edit().putString("unit_system", unit.name).apply()
        _unitSystem.value = unit
    }

    fun completeOnboarding(name: String, mode: ThemeMode, unit: UnitSystem = UnitSystem.IMPERIAL) {
        prefs.edit()
            .putString("user_name", name.trim())
            .putString("theme_mode", mode.name)
            .putString("unit_system", unit.name)
            .putBoolean("onboarding_completed", true)
            .apply()
        _userName.value = name.trim()
        _themeMode.value = mode
        _unitSystem.value = unit
        _isOnboardingCompleted.value = true
    }

    fun resetOnboarding() {
        prefs.edit().putBoolean("onboarding_completed", false).apply()
        _isOnboardingCompleted.value = false
    }
}
