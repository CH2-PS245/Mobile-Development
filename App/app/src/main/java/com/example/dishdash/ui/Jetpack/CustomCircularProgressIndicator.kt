import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dishdash.ui.Jetpack.darkGray
import com.example.dishdash.ui.Jetpack.gray
import com.example.dishdash.ui.Jetpack.navy
import com.example.dishdash.ui.Jetpack.orange


@Composable
fun CustomCircularProgressIndicator(
    modifier: Modifier = Modifier,
    initialValue : Int,
    primaryColor : Color,
    secondaryColor : Color,
    minValue : Int = 0,
    maxValue: Int = 100,
    circleRadius : Float,
    type : String,
    onPositionChange:(Int)-> Unit
){ 
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }
    var positionValue by remember {
        mutableStateOf(initialValue)
    }

    Box(modifier = modifier){
        Canvas(modifier = modifier
            .fillMaxSize()
        ){
            val width = size.width
            val height = size.height
            val circleThickness = width / 25f
            circleCenter = Offset(x = width/2f, y = height/2f)


            drawCircle(
                style = Stroke(
                    width = circleThickness
                ),
                color = secondaryColor,
                radius = circleRadius,
                center = circleCenter,
            )


            drawArc(
                color = primaryColor,
                startAngle = 90f,
                sweepAngle = (360f/maxValue)   * positionValue.toFloat(),
                style = Stroke(
                    width = circleThickness,
                    cap = StrokeCap.Round
                ),
                useCenter = false,
                size = Size(
                    width = circleRadius  * 2f,
                    height = circleRadius * 2f,
                ),
                topLeft = Offset(
                    (width - circleRadius * 2f)/2f,
                    (height - circleRadius * 2f)/2f
                )
            )

            drawContext.canvas.nativeCanvas.apply {
                drawIntoCanvas {
                    drawText(
                        "$positionValue %",
                        circleCenter.x,
                        circleCenter.y + 5.dp.toPx()/3f,
                        Paint().apply {
                            textSize = 18.sp.toPx()
                            textAlign = Paint.Align.CENTER
                            color = navy.toArgb()
                            isFakeBoldText = true
                        }
                    )
                    drawText(
                        "$type",
                        circleCenter.x,
                        circleCenter.y + circleRadius + circleThickness / 2f - 25.dp.toPx(),
                        Paint().apply {
                            textSize = 12.dp.toPx()
                            textAlign = Paint.Align.CENTER
                            color = navy.toArgb()
                            isFakeBoldText = true
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    CustomCircularProgressIndicator(
        modifier = Modifier
            .background(darkGray),
        initialValue = 50,
        primaryColor = navy,
        secondaryColor = gray,
        circleRadius = 150f,
        type = "Protein",
        onPositionChange = {

        }
    )
}