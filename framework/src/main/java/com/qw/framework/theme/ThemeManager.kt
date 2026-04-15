package com.qw.framework.theme

import androidx.appcompat.app.AppCompatDelegate
import java.util.LinkedHashMap

object ThemeManager {
    private var repository: ThemeRepository? = null
    private val themes = LinkedHashMap<String, BrandTheme>()

    @JvmStatic
    fun init(repository: ThemeRepository) {
        this.repository = repository
    }

    @JvmStatic
    fun registerTheme(theme: BrandTheme) {
        themes[theme.id] = theme
    }

    @JvmStatic
    fun registerThemes(themes: Collection<BrandTheme>) {
        themes.forEach(::registerTheme)
    }

    @JvmStatic
    fun getThemes(): List<BrandTheme> {
        return themes.values.toList()
    }

    @JvmStatic
    fun getTheme(themeId: String): BrandTheme? {
        return themes[themeId]
    }

    @JvmStatic
    fun getCurrentTheme(): BrandTheme? {
        val currentThemeId = repository?.getBrandThemeId().orEmpty()
        return themes[currentThemeId] ?: themes.values.firstOrNull()
    }

    @JvmStatic
    fun getCurrentThemeStyleRes(): Int? {
        return getCurrentTheme()?.styleRes
    }

    @JvmStatic
    fun setCurrentTheme(themeId: String): Boolean {
        val theme = themes[themeId] ?: return false
        repository?.setBrandThemeId(theme.id)
        return true
    }

    @JvmStatic
    fun setCurrentThemeIfAbsent(themeId: String): Boolean {
        val currentThemeId = repository?.getBrandThemeId().orEmpty()
        if (currentThemeId.isNotEmpty()) {
            return themes.containsKey(currentThemeId)
        }
        return setCurrentTheme(themeId)
    }

    @JvmStatic
    fun getThemeMode(): ThemeMode {
        return repository?.getThemeMode() ?: ThemeMode.FOLLOW_SYSTEM
    }

    @JvmStatic
    fun setThemeMode(mode: ThemeMode) {
        repository?.setThemeMode(mode)
        applyNightMode(mode)
    }

    @JvmStatic
    fun applyNightMode() {
        applyNightMode(getThemeMode())
    }

    private fun applyNightMode(mode: ThemeMode) {
        val nightMode = when (mode) {
            ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            ThemeMode.FOLLOW_SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}
