package com.example.pedometer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pedometer.pedometer.Pedometer
import com.example.pedometer.ui.PedometerView
import com.example.pedometer.ui.PedometerViewModel
import com.example.pedometer.ui.theme.PedometerTheme

class MainActivity : ComponentActivity() {
    private lateinit var pedometerViewModel: PedometerViewModel
    private lateinit var pedometer: Pedometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize
        pedometerViewModel = PedometerViewModel()
        pedometer = Pedometer(this, pedometerViewModel)

        enableEdgeToEdge()
        setContent {
            PedometerTheme {
                PedometerView(pedometerViewModel)
            }
        }

        if (!pedometer.isSensorExist()) {
            pedometerViewModel.onSensorUnavailable()
        }
    }

    override fun onStart() {
        super.onStart()
        pedometer.start()
    }

    override fun onStop() {
        super.onStop()
        pedometer.stop()
    }
}
