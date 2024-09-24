package com.example.bookstoreapp.ui.auth

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.bookstoreapp.network.ApiResponse
import com.example.bookstoreapp.network.RetrofitClient
import com.example.bookstoreapp.ui.theme.BookStoreAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RegisterScreen(
    onRegisterClick: (String, String, String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isRegistering by remember { mutableStateOf(false) }
    var isButtonPressed by remember { mutableStateOf(false) }

    // State để lưu trạng thái focus của email, password, và confirmPassword
    var isEmailFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    var isConfirmPasswordFocused by remember { mutableStateOf(false) }

    val context = LocalContext.current

    fun handleRegister(email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            Toast.makeText(context, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show()
            return
        }

        isRegistering = true
        val call = RetrofitClient.instance.registerUser(email, password)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                isRegistering = false
                val apiResponse = response.body()
                if (apiResponse?.status == "success") {
                    Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                    onNavigateToLogin()
                } else {
                    Toast.makeText(context, apiResponse?.message ?: "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                isRegistering = false
                Toast.makeText(context, "Có lỗi xảy ra: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tiêu đề "Đăng ký" ở giữa và đậm
            Text(
                text = "Đăng ký",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                ),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // TextField cho email với hiệu ứng focus
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .onFocusChanged { focusState -> isEmailFocused = focusState.isFocused }
                    .animateContentSize(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Gray,
                    focusedTextColor = Color.Black, // Màu văn bản đen khi nhập
                    unfocusedTextColor = Color.Gray  // Màu văn bản xám khi không nhập
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // TextField cho password với hiệu ứng focus
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mật khẩu") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .onFocusChanged { focusState -> isPasswordFocused = focusState.isFocused }
                    .animateContentSize(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Gray,
                    focusedTextColor = Color.Black, // Màu văn bản đen khi nhập
                    unfocusedTextColor = Color.Gray  // Màu văn bản xám khi không nhập
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // TextField cho confirmPassword với hiệu ứng focus
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Xác nhận mật khẩu") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .onFocusChanged { focusState -> isConfirmPasswordFocused = focusState.isFocused }
                    .animateContentSize(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Gray,
                    focusedTextColor = Color.Black, // Màu văn bản đen khi nhập
                    unfocusedTextColor = Color.Gray  // Màu văn bản xám khi không nhập
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Hiển thị hiệu ứng CircularProgressIndicator khi đăng ký
            AnimatedVisibility(visible = isRegistering) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(16.dp)
                        .scale(if (isRegistering) 1.2f else 1f)
                )
            }

            // Nút đăng ký với hiệu ứng mượt mà và phản hồi nhấn
            AnimatedVisibility(visible = !isRegistering) {
                Button(
                    onClick = {
                        isButtonPressed = true
                        handleRegister(email, password, confirmPassword)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .animateContentSize(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(text = "Đăng ký", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nút chuyển sang đăng nhập
            TextButton(
                onClick = onNavigateToLogin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Đã có tài khoản? Đăng nhập",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    BookStoreAppTheme {
        RegisterScreen(onRegisterClick = { _, _, _ -> }, onNavigateToLogin = {})
    }
}
