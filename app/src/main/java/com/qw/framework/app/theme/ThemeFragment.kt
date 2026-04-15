package com.qw.framework.app.theme

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.qw.framework.app.R
import com.qw.framework.theme.BrandTheme
import com.qw.framework.theme.ThemeManager
import com.qw.framework.theme.ThemeMode
import com.qw.framework.ui.BaseFragment

/**
 * author : qinwei@agora.io
 * date : 2026/4/15 15:23
 * description :
 */
class ThemeFragment : BaseFragment(R.layout.fragment_theme_test) {
    private lateinit var lightModeButton: MaterialButton
    private lateinit var darkModeButton: MaterialButton
    private lateinit var followSystemButton: MaterialButton
    private lateinit var themeListContainer: LinearLayout

    override fun initView(view: View) {
        lightModeButton = view.findViewById(R.id.btnLightMode)
        darkModeButton = view.findViewById(R.id.btnDarkMode)
        followSystemButton = view.findViewById(R.id.btnFollowSystemMode)
        themeListContainer = view.findViewById(R.id.themeListContainer)
        bindThemeModeActions()
    }

    override fun initData() {
        renderThemeModes()
        renderThemeOptions()
    }

    private fun bindThemeModeActions() {
        lightModeButton.setOnClickListener {
            updateThemeMode(ThemeMode.LIGHT)
        }
        darkModeButton.setOnClickListener {
            updateThemeMode(ThemeMode.DARK)
        }
        followSystemButton.setOnClickListener {
            updateThemeMode(ThemeMode.FOLLOW_SYSTEM)
        }
    }

    private fun renderThemeModes() {
        val currentMode = ThemeManager.getThemeMode()
        bindThemeModeButton(lightModeButton, currentMode == ThemeMode.LIGHT)
        bindThemeModeButton(darkModeButton, currentMode == ThemeMode.DARK)
        bindThemeModeButton(followSystemButton, currentMode == ThemeMode.FOLLOW_SYSTEM)
    }

    private fun renderThemeOptions() {
        val inflater = LayoutInflater.from(requireContext())
        val themes = ThemeManager.getThemes()
        val currentThemeId = ThemeManager.getCurrentTheme()?.id
        themeListContainer.removeAllViews()
        themes.forEachIndexed { index, brandTheme ->
            val itemView = inflater.inflate(R.layout.item_theme_option, themeListContainer, false)
            bindThemeItem(itemView, brandTheme, brandTheme.id == currentThemeId)
            themeListContainer.addView(itemView)
            if (index < themes.lastIndex) {
                val spacingView = View(requireContext())
                spacingView.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    resources.getDimensionPixelSize(R.dimen.theme_option_spacing)
                )
                themeListContainer.addView(spacingView)
            }
        }
    }

    private fun bindThemeModeButton(button: MaterialButton, isSelected: Boolean) {
        button.isChecked = isSelected
    }

    private fun updateThemeMode(mode: ThemeMode) {
        if (ThemeManager.getThemeMode() == mode) {
            return
        }
        ThemeManager.setThemeMode(mode)
        requireActivity().recreate()
    }

    private fun bindThemeItem(itemView: View, brandTheme: BrandTheme, isSelected: Boolean) {
        val cardView = itemView as MaterialCardView
        val swatchView = itemView.findViewById<View>(R.id.themeColorSwatch)
        val nameView = itemView.findViewById<TextView>(R.id.themeName)
        val descView = itemView.findViewById<TextView>(R.id.themeStyleName)
        val checkView = itemView.findViewById<ImageView>(R.id.themeCheck)

        val primaryColor = resolveColor(brandTheme.styleRes, com.google.android.material.R.attr.colorPrimary)
        val outlineColor = resolveColor(brandTheme.styleRes, com.google.android.material.R.attr.colorOutline)
        val surfaceColor = resolveColor(brandTheme.styleRes, com.google.android.material.R.attr.colorSurface)

        nameView.text = brandTheme.name
        descView.text = brandTheme.id
        applySwatchStyle(swatchView, primaryColor, outlineColor)
        cardView.setCardBackgroundColor(surfaceColor)
        cardView.strokeWidth = if (isSelected) dp(2) else dp(1)
        cardView.strokeColor = if (isSelected) primaryColor else outlineColor
        checkView.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE

        itemView.setOnClickListener {
            if (ThemeManager.setCurrentTheme(brandTheme.id)) {
                requireActivity().recreate()
            }
        }
    }

    private fun applySwatchStyle(target: View, fillColor: Int, strokeColor: Int) {
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = dp(10).toFloat()
            setColor(fillColor)
            setStroke(dp(1), strokeColor)
        }
        target.background = drawable
    }

    private fun resolveColor(themeResId: Int, attrResId: Int): Int {
        val themeContext = ContextThemeWrapper(requireContext(), themeResId)
        return resolveColor(themeContext, attrResId)
    }

    private fun resolveColor(context: Context, attrResId: Int): Int {
        val typedValue = TypedValue()
        return if (context.theme.resolveAttribute(attrResId, typedValue, true)) {
            if (typedValue.resourceId != 0) {
                ContextCompat.getColor(context, typedValue.resourceId)
            } else {
                typedValue.data
            }
        } else {
            ContextCompat.getColor(context, android.R.color.darker_gray)
        }
    }

    private fun dp(value: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
}
