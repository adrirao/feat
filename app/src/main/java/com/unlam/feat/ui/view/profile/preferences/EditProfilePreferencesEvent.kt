package com.unlam.feat.ui.view.profile.preferences

sealed class EditProfilePreferencesEvent {
    data class EnteredMinAge(val value: String) : EditProfilePreferencesEvent()
    data class EnteredMaxAge(val value: String) : EditProfilePreferencesEvent()
    data class EnteredNotifications(val value: Boolean) : EditProfilePreferencesEvent()
    data class EnteredWillingDistance(val value: String) : EditProfilePreferencesEvent()
}