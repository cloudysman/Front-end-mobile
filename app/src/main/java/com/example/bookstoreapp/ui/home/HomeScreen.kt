package com.example.bookstoreapp.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.example.bookstoreapp.network.Book
import com.example.bookstoreapp.ui.theme.BookStoreAppTheme
import com.example.bookstoreapp.ui.viewmodel.BookViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image

@Composable
fun HomeScreen(onLogoutClick: () -> Unit, viewModel: BookViewModel = viewModel()) {
    var isButtonPressed by remember { mutableStateOf(false) }

    // Sử dụng collectAsState để quan sát danh sách sách từ ViewModel
    val bookList by viewModel.bookList.collectAsState()

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
            // Tiêu đề chào mừng
            Text(
                text = "Chào mừng đến với hệ thống bán sách",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nút Đăng xuất với hiệu ứng nhấn
            Button(
                onClick = {
                    isButtonPressed = true
                    onLogoutClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .animateContentSize()
                    .scale(if (isButtonPressed) 0.98f else 1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                contentPadding = PaddingValues(16.dp)
            ) {
                Text(text = "Đăng xuất", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Hiển thị danh sách sách
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(bookList) { book ->
                    BookItem(book = book)
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(text = book.title, style = MaterialTheme.typography.titleMedium)
        Text(text = "Tác giả: ${book.author}")
        Text(text = "Giá: \$${book.price}")

        if (book.imageUrl.isNotEmpty()) {
            Image(
                painter = rememberImagePainter(book.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BookStoreAppTheme {
        HomeScreen(onLogoutClick = {})
    }
}
