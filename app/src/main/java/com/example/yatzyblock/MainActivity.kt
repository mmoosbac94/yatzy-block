package com.example.yatzyblock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Scaffold(topBar = { MyTopAppBar() }) {}
            }
        }

    }

}


@Composable
fun MyTopAppBar() {
    TopAppBar(
        title = { Text("Yatzy-Block") },
        backgroundColor = androidx.compose.ui.graphics.Color.Green,
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Text("+")
            }
        }
    )
}