package com.example.newproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newproject.repository.SettingRepoImpl
import com.example.newproject.repository.SettingRepository

class SettingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SettingRepository = SettingRepoImpl(application) // Pass context correctly

    private val _darkModeEnabled = MutableLiveData<Boolean>()
    val darkModeEnabled: LiveData<Boolean> get() = _darkModeEnabled

    private val _notificationsEnabled = MutableLiveData<Boolean>()
    val notificationsEnabled: LiveData<Boolean> get() = _notificationsEnabled

    fun loadSettings() {
        _darkModeEnabled.value = repository.isDarkModeEnabled()
        _notificationsEnabled.value = repository.isNotificationsEnabled()
    }

    fun toggleDarkMode(enabled: Boolean) {
        repository.setDarkMode(enabled)
        _darkModeEnabled.value = enabled
    }

    fun toggleNotifications(enabled: Boolean) {
        repository.setNotificationsEnabled(enabled)
        _notificationsEnabled.value = enabled
    }
}
