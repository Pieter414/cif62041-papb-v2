package com.example.tugas23

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.tooling.preview.Preview
import com.example.tugas23.ui.theme.Tugas23Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tugas23Theme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ProfileScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreen() {
    val namaMahasiswa = "Mao Shimada"

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "Gambar Profil",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Halo, $namaMahasiswa!",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Selamat datang di aplikasi saya!",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}