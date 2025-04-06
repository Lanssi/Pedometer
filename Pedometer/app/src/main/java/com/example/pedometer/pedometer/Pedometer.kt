package com.example.pedometer.pedometer

import android.content.Context
import android.hardware.SensorManager
import com.example.pedometer.ui.PedometerViewModel

/**
 * Created by lanssi on 2025/04.
 */
class Pedometer(
    context: Context,
    viewModel: PedometerViewModel
) {
    private val walkingDetector = WalkingDetector()
    private val accelerometerStepDetector = AccelerometerStepDetector(context)

    init {
        accelerometerStepDetector.setListener(walkingDetector)
        walkingDetector.setListener(viewModel)
    }

    fun isSensorExist(): Boolean {
        return accelerometerStepDetector.sensor != null
    }

    fun start(): Unit {
        accelerometerStepDetector.sensor?.let {
            accelerometerStepDetector.sensorManager.registerListener(
                accelerometerStepDetector,
                it,
                SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stop(): Unit {
        accelerometerStepDetector.sensor?.let {
            accelerometerStepDetector.sensorManager.unregisterListener(accelerometerStepDetector)
        }
    }
}