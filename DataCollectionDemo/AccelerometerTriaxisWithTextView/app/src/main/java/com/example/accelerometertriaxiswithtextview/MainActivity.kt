package com.example.accelerometertriaxiswithtextview

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.accelerometertriaxiswithtextview.ui.theme.AccelerometerTriaxisWithTextViewTheme

class MainActivity : ComponentActivity(), SensorEventListener {
    private var acceleration3D by mutableStateOf(Triple(0f, 0f, 0f))
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AccelerometerTriaxisWithTextViewTheme {
                AccelerationCard(acceleration3D)
            }
        }

        initSensorService()
    }

    override fun onResume() {
        super.onResume()
        sensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensor?.also {
            sensorManager.unregisterListener(this)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            acceleration3D = Triple(event.values[0], event.values[1], event.values[2])
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

    private fun initSensorService() {
        this.sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        this.sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }
}

@Composable
fun AccelerationCard(
    acceleration3D: Triple<Float, Float, Float>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(Color.White)
        .wrapContentSize(align = Alignment.Center)
    ) {
        Column (
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text (
                text = "Accelerometer",
                fontSize = 36.sp
            )
            Spacer(modifier = Modifier.padding(12.dp))
            Text(
                text = "X axis: %.2f".format(acceleration3D.first),
                fontSize = 24.sp,
                color = Color.Red
            )
            Text(
                text = "Y axis: %.2f".format(acceleration3D.second),
                fontSize = 24.sp,
                color = Color.Green
            )
            Text(
                text = "Z axis: %.2f".format(acceleration3D.third),
                fontSize = 24.sp,
                color = Color.Blue
            )

        }
    }
}