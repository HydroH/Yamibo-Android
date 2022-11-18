package com.hydroh.yamibo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hydroh.yamibo.network.OkHttpSingleton
import com.hydroh.yamibo.ui.screen.LoginScreen
import com.hydroh.yamibo.ui.theme.YamiboTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        OkHttpSingleton.init(this.applicationContext)

        setContent {
            YamiboTheme {
                // A surface container using the 'background' color from the theme
                YamiboApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun YamiboApp() {
    Scaffold (
        content = {
            LoginScreen()
        }
    )
}
