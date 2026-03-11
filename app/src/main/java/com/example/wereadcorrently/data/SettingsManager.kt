package com.example.wereadcorrently.data

import android.content.Context

object SettingsManager {
    private lateinit var dataStorage: DataStorage

    fun init(context: Context) {
        dataStorage = DataStorage(context.applicationContext)
    }

    // Флаги первого запуска
    fun setOnboardingCompleted() {
        dataStorage.putBoolean("onboarding_completed", true)
    }

    fun isFirstLaunch(): Boolean {
        return true
    }

    // Сохранение всех настроек
    fun saveSettings(settings: Settings) {
        dataStorage.putFloat("minDistance", settings.minDistance)
        dataStorage.putFloat("maxAngle", settings.maxAngle)
        dataStorage.putBoolean("alertEnabled", settings.alertEnabled)
        dataStorage.putBoolean("soundEnabled", settings.soundEnabled)
        dataStorage.putBoolean("vibrationEnabled", settings.vibrationEnabled)
        dataStorage.putInt("cameraId", settings.cameraId)
    }

    // Загрузка настроек (с значениями по умолчанию)
    fun loadSettings(): Settings {
        return Settings(
            minDistance = dataStorage.getFloat("minDistance", 30f),
            maxAngle = dataStorage.getFloat("maxAngle", 15f),
            alertEnabled = dataStorage.getBoolean("alertEnabled", true),
            soundEnabled = dataStorage.getBoolean("soundEnabled", true),
            vibrationEnabled = dataStorage.getBoolean("vibrationEnabled", true),
            cameraId = dataStorage.getInt("cameraId", 0)
        )
    }
}