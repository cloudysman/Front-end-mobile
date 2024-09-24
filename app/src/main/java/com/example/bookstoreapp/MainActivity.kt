package com.example.bookstoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookstoreapp.ui.auth.LoginScreen
import com.example.bookstoreapp.ui.auth.RegisterScreen
import com.example.bookstoreapp.ui.home.HomeScreen
import com.example.bookstoreapp.ui.theme.BookStoreAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookStoreAppTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginClick = { email, password ->
                    // Logic xử lý đăng nhập
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                onNavigateToHome = {
                    navController.navigate("home") // Điều hướng tới HomeScreen
                }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterClick = { email, password, confirmPassword ->
                    // Logic xử lý đăng ký
                },
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }
        composable("home") {
            HomeScreen(
                onLogoutClick = {
                    navController.navigate("login") // Điều hướng về đăng nhập khi đăng xuất
                }
            )
        }
    }
}
