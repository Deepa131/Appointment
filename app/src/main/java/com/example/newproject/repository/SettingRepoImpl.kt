package com.example.newproject.repository

import android.content.Context
import android.content.SharedPreferences

class SettingRepoImpl(context: Context) : SettingRepository {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

    override fun setDarkMode(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("DarkMode", enabled).apply()
    }

    override fun isDarkModeEnabled(): Boolean {
        return sharedPreferences.getBoolean("DarkMode", false)
    }

    override fun setNotificationsEnabled(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("NotificationsEnabled", enabled).apply()
    }

    override fun isNotificationsEnabled(): Boolean {
        return sharedPreferences.getBoolean("NotificationsEnabled", true)
    }
}
