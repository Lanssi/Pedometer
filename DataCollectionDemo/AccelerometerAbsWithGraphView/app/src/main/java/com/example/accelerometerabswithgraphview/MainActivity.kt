package com.example.accelerometerabswithgraphview

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.snap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.accelerometerabswithgraphview.ui.theme.AccelerometerAbsWithGraphViewTheme
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.AutoScrollCondition
import com.patrykandpatrick.vico.core.cartesian.Scroll
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import kotlin.math.sqrt

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    private var acceleration by mutableFloatStateOf(0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        initSensorService()

        setContent {
            AccelerometerAbsWithGraphViewTheme {
                SensorLineChart(acceleration)
            }
        }
    }

    fun initSensorService() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
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
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            this.acceleration = sqrt(x * x + y * y + z * z)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }
}

@Composable
fun SensorLineChart(
    newValue: Float,
    modifier: Modifier = Modifier
) {
    val modelProducer = remember { CartesianChartModelProducer() }
    val values = remember { mutableListOf<Float>() }

    LaunchedEffect(newValue) {
        values += newValue
        if (values.size % 5 == 0)
            modelProducer.runTransaction {
                lineSeries { series(values) }
            }
    }

    CartesianChartHost(
        chart =
            rememberCartesianChart(
                rememberLineCartesianLayer(
                    LineCartesianLayer.LineProvider.series(
                        LineCartesianLayer.rememberLine(LineCartesianLayer.LineFill.single(fill(
                            Color.Black)))
                    )
                ),
                startAxis = VerticalAxis.rememberStart(),
                bottomAxis = HorizontalAxis.rememberBottom()
            ),
        modelProducer = modelProducer,
        modifier = modifier,
        scrollState =
            rememberVicoScrollState(
                autoScrollCondition = AutoScrollCondition.OnModelGrowth,
                autoScroll = Scroll.Absolute.End,
                autoScrollAnimationSpec = snap(),
            ),
        zoomState = rememberVicoZoomState(),
        animationSpec = null,
        animateIn = true,
    )
}