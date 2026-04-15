package com.qw.framework.theme

import androidx.annotation.StyleRes

data class BrandTheme(
    val id: String,
    val name: String,
    @StyleRes val styleRes: Int
)
