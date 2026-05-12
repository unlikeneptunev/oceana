package com.kelompok3.oceana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kelompok3.oceana.navigation.OceanaNavGraph
import com.kelompok3.oceana.ui.theme.OceanaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OceanaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    OceanaNavGraph()
                }
            }
        }
    }
}