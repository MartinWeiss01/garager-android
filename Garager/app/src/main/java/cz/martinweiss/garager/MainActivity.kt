package cz.martinweiss.garager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import cz.martinweiss.garager.navigation.Destination
import cz.martinweiss.garager.navigation.NavGraph
import cz.martinweiss.garager.ui.theme.GaragerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        /*
        var keepSplashScreenVisible = true
        splashScreen.setKeepOnScreenCondition { keepSplashScreenVisible }
        lifecycleScope.launch {
            delay(3000)
            keepSplashScreenVisible = false
        }
        */

        setContent {
            GaragerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(startDestination = Destination.VehicleListScreen.route)
                }
            }
        }
    }
}