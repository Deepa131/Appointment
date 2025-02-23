package com.example.newproject.repository

interface SettingRepository {
    fun setDarkMode(enabled: Boolean)
    fun isDarkModeEnabled(): Boolean
    fun setNotificationsEnabled(enabled: Boolean)
    fun isNotificationsEnabled(): Boolean
}
