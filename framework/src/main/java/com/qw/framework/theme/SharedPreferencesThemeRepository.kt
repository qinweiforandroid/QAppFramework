package com.qw.framework.theme

import android.content.Context

class SharedPreferencesThemeRepository(context: Context) : ThemeRepository {
    private val preferences = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun getThemeMode(): ThemeMode {
        val value = preferences.getString(KEY_THEME_MODE, ThemeMode.FOLLOW_SYSTEM.name)
        return value?.runCatching { ThemeMode.valueOf(this) }?.getOrDefault(ThemeMode.FOLLOW_SYSTEM)
            ?: ThemeMode.FOLLOW_SYSTEM
    }

    override fun setThemeMode(mode: ThemeMode) {
        preferences.edit()
            .putString(KEY_THEME_MODE, mode.name)
            .apply()
    }

    override fun getBrandThemeId(): String {
        return preferences.getString(KEY_BRAND_THEME_ID, "").orEmpty()
    }

    override fun setBrandThemeId(themeId: String) {
        preferences.edit()
            .putString(KEY_BRAND_THEME_ID, themeId)
            .apply()
    }

    companion object {
        private const val PREF_NAME = "theme_manager"
        private const val KEY_THEME_MODE = "theme_mode"
        private const val KEY_BRAND_THEME_ID = "brand_theme_id"
    }
}
