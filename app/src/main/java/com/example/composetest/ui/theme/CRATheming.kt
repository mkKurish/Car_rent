package com.example.composetest.ui.theme

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composetest.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val LightGrey = Color(0xFFF3F3F3)
val lightGrey50 = Color(0xFFCCCCCC)
val DeepDarkBlue = Color(0xFF2C2B34)
val DeepDarkBlue800 = Color(0xFF4e4d57)
val DeepDarkBlue600 = Color(0xFF83818c)

val spacingExtraSmall: Dp = 4.dp
val spacingSmall: Dp = 8.dp
val spacingMedium: Dp = 16.dp
val spacingLarge: Dp = 32.dp
val spacingExtraLarge: Dp = 64.dp

data class CRAColors(
    val primary: Color,
    val secondary: Color,
    val canvasBackground: Color,
    val buttonText: Color,
    val cardBackground: Color,
    val buttonBackground: Color,
    val editTextBackground: Color,
    val innerShadowColor: Color
)

data class CRAImages(
    val homeScreenImg: Int,
    val signInUpImg: Int
)

fun craDarkColors(
    primary: Color = Color(0xFFFFFFFF),
    secondary: Color = Color(0xFFF5F5F5),
    canvasBackground: Color = Color(0xFF4E4D57),
    buttonText: Color = Color(0xFF534D57),
    cardBackground: Color = Color(0xFF2C2B34),
    buttonBackground: Color = Color(0xFFF8FDFF),
    editTextBackground: Color = Color(0xFF2C2B34),
    innerShadowColor: Color = Color(0xFF0A0A0A)
): CRAColors = CRAColors(
    primary,
    secondary,
    canvasBackground,
    buttonText,
    cardBackground,
    buttonBackground,
    editTextBackground,
    innerShadowColor
)

fun craLightColors(
    primary: Color = Color(0xFF000000),
    secondary: Color = Color(0xFF969696),
    canvasBackground: Color = Color(0xFFFCFBFF),
    buttonText: Color = Color(0xFFFCFBFF),
    cardBackground: Color = Color(0xFFF3F3F3),
    buttonBackground: Color = Color(0xFF534D57),
    editTextBackground: Color = Color(0xFFF8FDFF),
    innerShadowColor: Color = Color(0xFF858585)
): CRAColors = CRAColors(
    primary,
    secondary,
    canvasBackground,
    buttonText,
    cardBackground,
    buttonBackground,
    editTextBackground,
    innerShadowColor
)

fun craLightImages(
    homeScreenImg: Int = R.drawable.toyota_fortuner_fullface_white,
    signInUpImg: Int = R.drawable.bentley_sign_in_up
): CRAImages = CRAImages(
    homeScreenImg,
    signInUpImg
)

fun craDarkImages(
    homeScreenImg: Int = R.drawable.toyota_fortuner_fullface_black,
    signInUpImg: Int = R.drawable.mercedes_sign_in_up
): CRAImages = CRAImages(
    homeScreenImg,
    signInUpImg
)

object CRATheme {
    val colors: CRAColors
        @Composable
        get() = LocalCRAColors.current
    val images: CRAImages
        @Composable
        get() = LocalCRAImages.current
}

val LocalCRAColors = staticCompositionLocalOf<CRAColors> {
    error("No colors provided")
}

val LocalCRAImages = staticCompositionLocalOf<CRAImages> {
    error("No images provided")
}

@Composable
fun MainTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    val colors = if (isSystemInDarkTheme()) craDarkColors() else craLightColors()
    val images = if (isSystemInDarkTheme()) craDarkImages() else craLightImages()
    systemUiController.setSystemBarsColor(
        color = colors.canvasBackground,
        darkIcons = !darkTheme
    )
    CompositionLocalProvider(
        LocalCRAColors provides colors,
        LocalCRAImages provides images,
        content = content
    )
}

@Composable
public fun getEditTextColors(clrs: CRAColors): TextFieldColors {
    return TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = clrs.secondary,
        cursorColor = clrs.secondary,
        textColor = clrs.primary,
        placeholderColor = clrs.secondary,
        backgroundColor = clrs.editTextBackground
    )
}

public fun Modifier.innerShadow(
    color: Color = Color.Red,
    cornerRadius: Dp = 50.dp,
    spread: Dp = 2.dp,
    blur: Dp = 4.dp,
) = drawWithContent{
    drawContent()
    val rect = Rect(Offset.Zero, size)
    val paint = Paint()
    drawIntoCanvas {
        paint.color = color
        paint.isAntiAlias = true
        it.saveLayer(rect, paint)
        it.drawRoundRect(
            left = rect.left,
            top = rect.top,
            right = rect.right,
            bottom = rect.bottom,
            cornerRadius.toPx(),
            cornerRadius.toPx(),
            paint
        )
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        if (blur.toPx() > 0) frameworkPaint.maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        paint.color = Color.Black
        it.drawRoundRect(
            left = rect.left + spread.toPx()/2,
            top = rect.top + spread.toPx()/2,
            right = rect.right - spread.toPx()/2,
            bottom = rect.bottom - spread.toPx()/2,
            cornerRadius.toPx(),
            cornerRadius.toPx(),
            paint
        )
        frameworkPaint.xfermode = null
        frameworkPaint.maskFilter = null
    }
}