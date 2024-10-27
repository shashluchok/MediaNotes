package com.shashluchok.medianotes.presentation.modifiers.shadow

import android.graphics.BlurMaskFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.toArgb

private const val shadowPaintAlpha = 0.02f

internal actual fun createShadowPaint(radius: Float): Paint {
    return Paint().apply {
        val frameworkPaint = asFrameworkPaint()
        frameworkPaint.setShadowLayer(radius, 0f, 0f, Color.Black.copy(alpha = shadowPaintAlpha).toArgb())
        frameworkPaint.maskFilter =
            (BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL))
    }
}
