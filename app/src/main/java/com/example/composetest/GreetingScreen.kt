package com.example.composetest

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.composetest.ui.theme.*
import kotlinx.coroutines.delay
import java.lang.Math.abs


class GreetingScreen : ComponentActivity() {
    private val viewModel: MainModelView by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        splashScreenDelay = 0L
        setContent {
            MainTheme() {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = CRATheme.colors.canvasBackground)
                )
                val constraints = ConstraintSet {
                    val carPic = createRefFor("carPic")
                    val headline = createRefFor("headline")
                    val underLine = createRefFor("underLine")
                    val button1 = createRefFor("button1")

                    constrain(carPic) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end, spacingSmall)
                        width = Dimension.fillToConstraints
                        bottom.linkTo(headline.top)
                    }
                    constrain(headline) {
                        start.linkTo(parent.start, spacingMedium)
                        end.linkTo(parent.end, spacingMedium)
                        bottom.linkTo(underLine.top, spacingSmall)
                        width = Dimension.fillToConstraints
                    }
                    constrain(underLine) {
                        start.linkTo(parent.start, spacingMedium)
                        end.linkTo(parent.end, spacingMedium)
                        bottom.linkTo(button1.top, spacingLarge)
                        width = Dimension.fillToConstraints
                    }
                    constrain(button1) {
                        start.linkTo(parent.start, spacingMedium)
                        end.linkTo(parent.end, spacingMedium)
                        bottom.linkTo(parent.bottom, spacingMedium)
                        width = Dimension.fillToConstraints
                    }
                }
                ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = CRATheme.images.homeScreenImg),
                        contentDescription = "car pic",
                        modifier = Modifier.layoutId("carPic"),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Premium cars.\nEnjoy the luxury",
                        modifier = Modifier.layoutId("headline"),
                        style = TextStyle(
                            color = CRATheme.colors.primary,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Premium and prestige car daily rental.\n" +
                                "Experience the thrill at a lower price",
                        modifier = Modifier.layoutId("underLine"),
                        style = TextStyle(color = CRATheme.colors.secondary, fontSize = 16.sp)
                    )
                    Button(
                        onClick = {
                            val navigate = Intent(this@GreetingScreen, SignInScreen::class.java)
                            startActivity(navigate)
                        },
                        modifier = Modifier.layoutId("button1"),
                        colors = ButtonDefaults.buttonColors(backgroundColor = CRATheme.colors.buttonBackground),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Let's Go",
                            style = TextStyle(
                                color = CRATheme.colors.buttonText,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                var hfangleState = rememberInfiniteTransition()
                var hsangleState = rememberInfiniteTransition()
                val hfangle by hfangleState.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 2000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                ))
                val hsangle by hsangleState.animateFloat(
                    initialValue = 45f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 4000, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ))
                val fknColor = CRATheme.colors.canvasBackground
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier
                        .size(100.dp)
                        .padding(10.dp)
                        .drawBehind() {
                            drawArc(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFdf0541),
                                        Color(0xFF0059a9)
                                    )
                                ),
                                startAngle = hfangle,
                                sweepAngle = hsangle,
                                useCenter = false,
                                size = Size(size.width / 4, size.height),
                                style = Stroke(5.dp.toPx()),
                                topLeft = Offset(3 * size.width / 8, 0f)
                            )
                            drawArc(
                                color = fknColor,
                                startAngle = 270f,
                                sweepAngle = 180f,
                                useCenter = false,
                                size = Size(size.width, size.height / 4),
                                style = Fill,
                                topLeft = Offset(0f, 3 * size.height / 8)
                            )
                            drawArc(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF01b4ec), Color(0xFF0059a9))
                                ),
                                startAngle = hfangle,
                                sweepAngle = hsangle,
                                useCenter = false,
                                size = Size(size.width, size.height / 4),
                                style = Stroke(5.dp.toPx()),
                                topLeft = Offset(0f, 3 * size.height / 8)
                            )
                            drawArc(
                                color = fknColor,
                                startAngle = 180f,
                                sweepAngle = 180f,
                                useCenter = false,
                                size = Size(size.width * 0.18f, size.height * 0.8f),
                                style = Fill,
                                topLeft = Offset(size.width * 0.41f, size.height * 0.02f)
                            )
                        })
                }
            }
        }
    }
}

