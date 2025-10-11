package com.example.halostudent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

// import compose function
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tugas1.ui.theme.Tugas1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tugas1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    GreetingApp()
                }
            }
        }
    }
}

@Preview()
@Composable
fun GreetingApp() {
    var text by remember { mutableStateOf("") }
    val namaMahasiswa = "Pieter Christy Yan Yudhistira"

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                text = if (text.isEmpty()) {
                    "Halo $namaMahasiswa"
                } else {
                    ""
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Click Here")
        }

        if (text.isNotEmpty()) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}