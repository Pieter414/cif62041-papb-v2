package com.example.profile_235150201111068_pieterchristyyanyudhistira

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout

import androidx.compose.ui.tooling.preview.Preview
import com.example.profile_235150201111068_pieterchristyyanyudhistira.ui.theme.Profile_235150201111068_PieterChristyYanYudhistiraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Profile_235150201111068_PieterChristyYanYudhistiraTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ProfileScreen()
                }
            }
        }
    }
}

var likes = mutableStateOf(120000)

@Composable()
fun ProfileImage() {
    Image(
        painter  = painterResource(id = R.drawable.profile),
        contentDescription = "Gambar Profil",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(4.dp, Color(0xFF25475F), CircleShape)
    )
    Spacer(modifier = Modifier.height(24.dp))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Mao Shimada",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "235150700111000",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 16.sp,
        )
    }
}

@Composable()
fun LikesCounter() {
    val formatter = remember {
        java.text.DecimalFormat("#,###").apply {
            decimalFormatSymbols = decimalFormatSymbols.apply {
                groupingSeparator = '.' // use dot as thousands separator
            }
        }
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 7.5.dp, vertical = 21.dp),
    ){
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Likes",
            tint = Color(0xFF8A38F5),
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "${formatter.format(likes.value)} Likes",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp,
        )
    }
}

@Composable()
fun FollowButton() {
    var isClicked by remember { mutableStateOf(false) }

    Button(
        onClick = {
            // Aksi ketika tombol di klik
            isClicked = !isClicked
        },
        modifier = Modifier
            .width(270.dp)
            .height(43.dp)
        ,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (!isClicked) Color(0xFF8A38F5) else Color.Transparent,
        ),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(16.dp),
        border = if (!isClicked) BorderStroke(0.dp, Color.Black) else BorderStroke(1.dp, Color(0xFF8A38F5))
    ){
        Text(
            text = if (!isClicked) "Follow" else "Unfollow",
            fontSize = 16.sp,
            color = if (!isClicked) Color.White else Color(0xFF8A38F5),
        )
    }
    Spacer(modifier = Modifier.height(25.dp))
    Text(
        text = if (!isClicked) "Ingin mengikuti akun ini?" else "Anda telah mengikuti akun ini",
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 16.sp,
    )
}

@Composable()
fun LikesButton(){
    Row(){
        Button(
            onClick = {
                // Aksi ketika tombol di klik
                likes.value += 1
            },
            modifier = Modifier
                .width(106.dp)
                .height(66.dp)
            ,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8A38F5),
            ),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(16.dp),
        ){
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Tambah",
                tint = Color.White,
                modifier = Modifier.size(size = 24.dp),
            )
        }
        Spacer(modifier = Modifier.width(38.dp))
        Button(
            onClick = {
                // Aksi ketika tombol di klik
                if (likes.value > 0){
                    likes.value -= 1
                }
            },
            modifier = Modifier
                .width(106.dp)
                .height(66.dp)
            ,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            ),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFF8A38F5))
        ){
            Icon(
                painter  = painterResource(id = R.drawable.tabler_minus),
                contentDescription = "Minus",
                tint = Color(0xFF8A38F5),
                modifier = Modifier.size(size = 24.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable()
fun ProfileScreen() {

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(76.dp))
        ProfileImage()

        Spacer(modifier = Modifier.height(25.dp))
        LikesCounter()

        Spacer(modifier = Modifier.height(46.dp))
        FollowButton()

        Spacer(modifier = Modifier.height(154.dp))
        Text(
            text = "Extension Haters (Hack Jumlah LIke)",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))
        LikesButton()
    }
}