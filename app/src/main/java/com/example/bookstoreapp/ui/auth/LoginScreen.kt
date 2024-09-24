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
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoggingIn by remember { mutableStateOf(false) }
    var isButtonPressed by remember { mutableStateOf(false) }

    // State để lưu trạng thái focus của email và password
    var isEmailFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }

    val context = LocalContext.current

    fun handleLogin() {
        isLoggingIn = true
        val call = RetrofitClient.instance.loginUser(email, password)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                isLoggingIn = false
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.status == "success") {
                        Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                        onNavigateToHome()
                    } else {
                        Toast.makeText(context, apiResponse?.message ?: "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                isLoggingIn = false
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
            // Tiêu đề "Đăng nhập" ở giữa và đậm
            Text(
                text = "Đăng nhập",
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

            // TextField cho email với màu văn bản tương phản
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .onFocusChanged { focusState ->
                        isEmailFocused = focusState.isFocused
                    }
                    .animateContentSize(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedTextColor = Color.Black, // Màu văn bản đen khi nhập
                    unfocusedTextColor = Color.Gray, // Màu văn bản xám khi không nhập
                    focusedLabelColor = Color.Black, // Màu đen khi được focus
                    unfocusedLabelColor = Color.Gray // Màu xám khi không được focus
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // TextField cho password với màu văn bản tương phản
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mật khẩu") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .onFocusChanged { focusState ->
                        isPasswordFocused = focusState.isFocused
                    }
                    .animateContentSize(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedTextColor = Color.Black, // Màu văn bản đen khi nhập
                    unfocusedTextColor = Color.Gray, // Màu văn bản xám khi không nhập
                    focusedLabelColor = Color.Black, // Màu đen khi được focus
                    unfocusedLabelColor = Color.Gray // Màu xám khi không được focus
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Hiển thị hiệu ứng CircularProgressIndicator khi đăng nhập
            AnimatedVisibility(visible = isLoggingIn) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(16.dp)
                        .scale(if (isLoggingIn) 1.2f else 1f)
                )
            }

            // Nút đăng nhập với hiệu ứng mượt mà và phản hồi nhấn
            AnimatedVisibility(visible = !isLoggingIn) {
                Button(
                    onClick = {
                        isButtonPressed = true
                        handleLogin()
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
                    Text(text = "Đăng nhập", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nút chuyển sang đăng ký
            TextButton(
                onClick = onNavigateToRegister,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    "Chưa có tài khoản? Đăng ký",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    BookStoreAppTheme {
        LoginScreen(
            onLoginClick = { _, _ -> },
            onNavigateToRegister = {},
            onNavigateToHome = {}
        )
    }
}
