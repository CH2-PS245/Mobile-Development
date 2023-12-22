package com.example.dishdash.ui.Jetpack

import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomRoundedHorizontalProgressIndicator(
    modifier: Modifier = Modifier,
    initialValue: Int,
    primaryColor: Color,
    secondaryColor: Color,
    minValue: Int = 0,
    maxValue: Int = 100,
    cornerRadiusDp: Float = 4f, // Ubah menjadi Float
    onPositionChange: (Int) -> Unit
) {
    val density = LocalDensity.current.density
    val cornerRadiusPx = with(LocalDensity.current) { cornerRadiusDp.dp.toPx() }

    var positionValue by remember {
        mutableStateOf(initialValue)
    }

    Box(modifier = modifier) {
        Canvas(modifier = modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val barHeight = height / 4f

            drawRoundRect(
                color = secondaryColor,
                size = Size(width, barHeight),
                cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx),
                topLeft = Offset(x = 0f, y = (height - barHeight) / 2f)
            )

            val progressWidth = (width * positionValue.toFloat() / maxValue).coerceAtMost(width)
            drawRoundRect(
                color = primaryColor,
                size = Size(progressWidth, barHeight),
                cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx),
                topLeft = Offset(x = 0f, y = (height - barHeight) / 2f)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RoundedPreview() {
    CustomRoundedHorizontalProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp),
        initialValue = 20,
        primaryColor = Color(0xFFFFA500),
        secondaryColor = Color.Gray,
        onPositionChange = {}
    )
}
