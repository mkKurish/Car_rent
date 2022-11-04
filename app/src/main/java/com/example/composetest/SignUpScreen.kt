package com.example.composetest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetest.ui.theme.*

class SignUpScreen : ComponentActivity() {
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
                        .background(CRATheme.colors.canvasBackground),
                    contentAlignment = Alignment.TopCenter
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
                    Button(
                        onClick = {
                            if ((loginVal.text == "") || (passwordVal.text == "")){
                                Toast.makeText(
                                    this@SignUpScreen,
                                    "Error: write password and login", Toast.LENGTH_SHORT
                                ).show()
                            }
                            else{
                                startActivity(Intent(this@SignUpScreen, HomeScreen::class.java))
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
                            text = "Sign Up",
                            style = TextStyle(
                                color = CRATheme.colors.buttonText,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Box(modifier = Modifier.padding(bottom = spacingLarge)) {
                        TextWithReference(
                            "Already have an account?",
                            "Sign In",
                            this@SignUpScreen,
                            SignInScreen::class.java,
                            interactionSource
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacingMedium)
                            .padding(bottom = 300.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 290.dp),
                                text = "Create an account!",
                                style = TextStyle(color = CRATheme.colors.primary, fontSize = 25.sp)
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
                    }
                }
            }
        }
    }
}
