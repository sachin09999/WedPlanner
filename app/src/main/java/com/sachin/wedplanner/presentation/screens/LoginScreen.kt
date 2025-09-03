package com.sachin.wedplanner.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sachin.wedplanner.R
import com.sachin.wedplanner.presentation.AppViewModel
import com.sachin.wedplanner.presentation.navigation.Routes
import com.sachin.wedplanner.presentation.utils.CustomTextField
import com.sachin.wedplanner.presentation.utils.SuccessAlertDialog
import com.sachin.wedplanner.ui.theme.orange

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AppViewModel = hiltViewModel()
) {
    val state by viewModel.loginScreenState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // State for inputs
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        // Pink wave and bubble background - Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Draw wavy pink shape at the top
            drawPath(
                 path = Path().apply {
                     moveTo(0f, 0f)     // start at top-left
                     lineTo(w , 0f)   // go right
                     lineTo(w,h*0.35f)
                     // --- wavy bottom going back to left ---
                     val waveCount = 5                // number of bumps
                     val waveWidth = w / waveCount     // each bump width
                     val waveHeight = h * 0.03f        // height of bumps
                     val baseY = h * 0.39f             // baseline for waves

                     for (i in 0 until waveCount) {
                         val startX = w - i * waveWidth
                         val endX = w - (i + 1) * waveWidth

                         // one bump using cubic curve
                         cubicTo(
                             startX - waveWidth / 4, baseY - waveHeight,   // control point up
                             startX - 3 * waveWidth / 4, baseY + waveHeight, // control point down
                             endX, baseY                                   // end point
                         )
                     }
                     close()
                },
                color = Color(0xFFBF4991)  // slightly deeper pink
            )

            // Draw bubble circles above the wave
            val bubbleColor = Color(0xFFFFD1DC).copy(alpha = 0.7f)
            drawCircle(bubbleColor, radius = w * 0.07f, center = Offset(w * 0.15f, h * 0.12f))
            drawCircle(bubbleColor, radius = w * 0.04f, center = Offset(w * 0.7f, h * 0.07f))
            drawCircle(bubbleColor, radius = w * 0.05f, center = Offset(w * 0.45f, h * 0.17f))
            drawCircle(bubbleColor, radius = w * 0.025f, center = Offset(w * 0.85f, h * 0.12f))
        }

        Image(
            painter = painterResource(id = R.drawable.wed),
            contentDescription = null,
            modifier = Modifier.align(alignment = Alignment.TopEnd)
                .padding(top = 142.dp)
                .size(200.dp)
        )

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = state.error!!,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
            state.userData != null -> {
                SuccessAlertDialog(
                    onClick = {
                        navController.navigate(Routes.HomeScreen)
                    }
                )
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp, vertical = 22.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Welcome "+"\n" +"Back!",
                        fontSize = 40.sp,
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(vertical = 71.dp)
                            .align(Alignment.Start),
                        color = Color(0xFFFFFFFF)
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = "Sign In !",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF000000),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 24.dp)
                    )

                    CustomTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        modifier = Modifier.fillMaxWidth(),
                        isSingleLine = true,
                        leadingIcon = Icons.Default.Email,
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions.Default,
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        modifier = Modifier.fillMaxWidth(),
                        isSingleLine = true,
                        leadingIcon = Icons.Default.Lock,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions.Default,
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(
                        onClick = { /* TODO: Forgot Password */ },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Forgot Password?", color = Color(0xFFAA336A))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (email.isNotEmpty() && password.isNotEmpty()) {
                                viewModel.loginUser(email, password)
                                Toast.makeText(context, "Sign In Success", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(orange),
                        shape = RoundedCornerShape(24.dp),
                    ) {
                        Text(
                            "Sign In",
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Don't have an account?")
                        TextButton(
                            onClick = { navController.navigate(Routes.RegisterScreen) }
                        ) {
                            Text("Sign Up", color = orange)
                        }
                    }
                }
            }
        }
    }
}
