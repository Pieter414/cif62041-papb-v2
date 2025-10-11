package com.example.votingapp_235150201111068pieterchristyyanyudhistira

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.tooling.preview.Preview
import com.example.votingapp_235150201111068pieterchristyyanyudhistira.ui.theme.VotingApp_235150201111068PieterChristyYanYudhistiraTheme

enum class Vote {
    EMPTY, UNCHOSEN, CHOSEN
}

var counter = mutableStateOf(1)
var isEmptyVote = mutableStateOf(Vote.EMPTY)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VotingApp_235150201111068PieterChristyYanYudhistiraTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    VoteAppPreview()
                }
            }
        }
    }
}

@Composable()
fun Title() {
    Text(
        text = "Voting Mawapres 2025",
        style = MaterialTheme.typography.headlineMedium,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Pilih Jagoanmu Untuk Maju!✨",
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 16.sp,
    )
    Spacer(modifier = Modifier.height(49.dp))
}

@Composable
fun PilihButton(voter: Int){
    var isVoted by remember { mutableStateOf(true) }

    Button(
        onClick = {
            if (counter.value > 0 || isEmptyVote.value == Vote.UNCHOSEN){
                counter.value = 0
                isEmptyVote.value = Vote.CHOSEN
                isVoted = false
            }
        },
        modifier = Modifier
            .width(270.dp)
            .height(43.dp)
        ,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isVoted) Color(0xFF8A38F5) else Color(0xFF6b747c),
        ),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(16.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Pilih",
                fontSize = 16.sp,
                color = Color.White,
            )
        }
    }
    Spacer(modifier = Modifier.height(30.dp))
    Text(
        text = "Sisa Vote: $voter",
        style = MaterialTheme.typography.bodySmall,
        fontSize = 20.sp,
    )
}

@Composable
fun VoterButton(nama: String, nim: String){
    var isClicked by remember { mutableStateOf(false) }

    Button(
        onClick = {
            if (isEmptyVote.value == Vote.EMPTY){
                isEmptyVote.value = Vote.UNCHOSEN
                isClicked = !isClicked
            }
            else if (isEmptyVote.value == Vote.UNCHOSEN && isClicked){
                isEmptyVote.value = Vote.EMPTY
                isClicked = !isClicked
            }
        },
        modifier = Modifier
            .width(303.dp)
            .height(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isClicked) Color(0xFF8A38F5) else Color.Transparent,
        ),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "$nama",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = if (isClicked) Color.White else Color.Black,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "$nim",
                fontSize = 20.sp,
                color = if (isClicked) Color.White else Color.Black,
            )
        }
    }
}

@Composable
fun VoteApp(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Title()

        VoterButton("Andi", "235150000001")

        Spacer(modifier = Modifier.height(40.dp))

        VoterButton("Budi", "235150000002")
        Spacer(modifier = Modifier.height(40.dp))

        VoterButton("Cindi", "235150000003")
        Spacer(modifier = Modifier.height(88.dp))

        PilihButton(counter.value)
    }
}

@Preview(showBackground = true)
@Composable
fun VoteAppPreview() {
    VotingApp_235150201111068PieterChristyYanYudhistiraTheme {
        VoteApp()
    }
}