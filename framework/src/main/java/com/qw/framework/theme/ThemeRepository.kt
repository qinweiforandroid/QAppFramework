package com.qw.framework.theme

interface ThemeRepository {
    fun getThemeMode(): ThemeMode
    fun setThemeMode(mode: ThemeMode)

    fun getBrandThemeId(): String
    fun setBrandThemeId(themeId: String)
}
