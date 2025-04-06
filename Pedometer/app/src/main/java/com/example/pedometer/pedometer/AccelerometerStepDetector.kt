package com.example.pedometer.pedometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.pedometer.config.AppConfig
import kotlin.math.sqrt

/**
 * Created by lanssi on 2025/04.
 */
class AccelerometerStepDetector(context: Context): SensorEventListener {
    // For sensor management
    val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // For detectAndHandlePeak
    private var maxInThisPeak: Float = AppConfig.COMMIT_LINE

    // For detectAndHandleStep
    private var dynamicThreshold: Float = AppConfig.INITIAL_THRESHOLD
    private var lastStepTime: Long = 0L
    private val thresholdWindow: ThresholdWindow = ThresholdWindow(AppConfig.WINDOW_SIZE)

    // For listener
    private lateinit var listener: AccelerometerStepDetectorListener

    fun setListener(accelerometerStepDetectorListener: AccelerometerStepDetectorListener) {
        this.listener = accelerometerStepDetectorListener
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) {return}
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        val acceleration = sqrt(x * x + y * y + z * z)
        detectAndHandlePeak(acceleration)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

    private fun detectAndHandlePeak(acceleration: Float) {
        if (acceleration > AppConfig.COMMIT_LINE) {
            maxInThisPeak = if (acceleration > maxInThisPeak) acceleration else maxInThisPeak
        } else {
            detectAndHandleStep(maxInThisPeak)
            maxInThisPeak = AppConfig.COMMIT_LINE    // reset the value
        }
    }

    private fun detectAndHandleStep(peak: Float) {
        val thisStepTime = System.currentTimeMillis()
        val timeInterval = thisStepTime - lastStepTime

        // timeout, reset dynamic threshold and window
        if (timeInterval > AppConfig.MAX_INTERVAL) {
            dynamicThreshold = AppConfig.INITIAL_THRESHOLD
            thresholdWindow.resetWindow()
        }

        // if it is a step, then callback the listener, update threshold window and peak time
        if (timeInterval >= AppConfig.MIN_INTERVAL && peak >= dynamicThreshold) {
            listener.onNewStep()
            thresholdWindow.updateWindow(peak)
//            dynamicThreshold = thresholdWindow.getThreshold()
            lastStepTime = thisStepTime
        }
    }
}