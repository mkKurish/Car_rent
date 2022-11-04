package com.example.composetest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.core.content.ContextCompat.startActivity
import com.example.composetest.ui.theme.*

class SignInScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val interactionSource = remember { MutableInteractionSource() }
            var loginVal by remember { mutableStateOf(TextFieldValue("")) }
            var passwordVal by remember { mutableStateOf(TextFieldValue("")) }
            MainTheme {
                val editTextColors = getEditTextColors(clrs = CRATheme.colors)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CRATheme.colors.canvasBackground)
                ) {
                    Image(
                        painter = painterResource(id = CRATheme.images.signInUpImg),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = spacingLarge),
                        contentScale = ContentScale.Crop
                    )
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacingMedium)
                            .padding(bottom = 270.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .innerShadow(CRATheme.colors.innerShadowColor),
                            shape = RoundedCornerShape(50)
                        ) {
                            TextField(
                                value = loginVal,
                                onValueChange = { newText -> loginVal = newText },
                                placeholder = { Text("Login") }, colors = editTextColors,
                                textStyle = TextStyle(fontSize = 15.sp), maxLines = 1,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                            )
                        }
                        Spacer(modifier = Modifier.height(spacingMedium))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .innerShadow(CRATheme.colors.innerShadowColor),
                            shape = RoundedCornerShape(50)
                        ) {
                            TextField(
                                modifier = Modifier, value = passwordVal,
                                onValueChange = { newText -> passwordVal = newText },
                                placeholder = { Text("Password") }, colors = editTextColors,
                                textStyle = TextStyle(fontSize = 15.sp), maxLines = 1,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                            )
                        }
                        Spacer(modifier = Modifier.height(spacingMedium))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .innerShadow(CRATheme.colors.innerShadowColor)
                                .clickable {
                                    startActivity(
                                        Intent(
                                            this@SignInScreen,
                                            HomeScreen::class.java
                                        )
                                    )
                                },
                            shape = RoundedCornerShape(50),
                            backgroundColor = CRATheme.colors.editTextBackground
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                IconValueBlock(
                                    painter = painterResource(id = R.drawable.ic_yandex),
                                    iconTint = Color.Red,
                                    picDescr = "",
                                    value = "Sign in with Yandex",
                                    sizeOfText = 20,
                                    colorOfText = CRATheme.colors.primary,
                                    weightOfFont = FontWeight.Bold
                                )
                            }
                        }
                    }
                    Button(
                        onClick = {
                            if ((loginVal.text == "") || (passwordVal.text == "")){
                                Toast.makeText(
                                    this@SignInScreen,
                                    "Error: write password and login", Toast.LENGTH_SHORT
                                ).show()
                            }
                            else{
                                startActivity(Intent(this@SignInScreen, HomeScreen::class.java))
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = spacingMedium)
                            .padding(bottom = 60.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = CRATheme.colors.buttonBackground),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Sign In",
                            style = TextStyle(
                                color = CRATheme.colors.buttonText,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Box(modifier = Modifier.padding(bottom = spacingLarge)) {
                        TextWithReference(
                            "Don’t have an account?",
                            "Sign up",
                            this@SignInScreen,
                            SignUpScreen::class.java,
                            interactionSource
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun <T> TextWithReference(
    mainText: String = "",
    refText: String,
    from: ComponentActivity,
    refClass: Class<T>,
    interactionSource: MutableInteractionSource
) {
    val constraints = ConstraintSet {
        val mainElement = createRefFor("mainE")
        val refElement = createRefFor("refE")
        constrain(refElement) {
            start.linkTo(mainElement.end, 4.dp)
        }
    }

    ConstraintLayout(constraints) {
        Text(
            text = mainText, modifier = Modifier.layoutId("mainE"),
            style = TextStyle(color = CRATheme.colors.secondary, fontSize = 15.sp)
        )
        Text(
            text = refText, modifier = Modifier
                .layoutId("refE")
                .clickable(interactionSource = interactionSource, indication = null) {
                    startActivity(from, Intent(from, refClass), null)
                }, style = TextStyle(
                color = CRATheme.colors.primary, fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}