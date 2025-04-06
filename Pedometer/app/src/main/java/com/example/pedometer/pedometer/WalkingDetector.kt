package com.example.pedometer.pedometer

import android.util.Log
import com.example.pedometer.config.AppConfig

/**
 * Created by lanssi on 2025/04.
 */
class WalkingDetector: AccelerometerStepDetectorListener {
    // For step counting buffer
    private var continuousStepCount: Int = 0
    private var lastStepTime: Long = 0L

    // For listener
    private lateinit var listener: WalkingDetectorListener

    fun setListener(walkingDetectorListener: WalkingDetectorListener) {
        this.listener = walkingDetectorListener
    }

    override fun onNewStep() {
        Log.d("WalkingDetector", "onNewStep Called")
        val thisStepTime = System.currentTimeMillis()
        if (thisStepTime - lastStepTime <= AppConfig.MAX_INTERVAL) {
            if (continuousStepCount < AppConfig.BUFFER_BOUND - 1) {
                continuousStepCount++
            } else if (continuousStepCount == AppConfig.BUFFER_BOUND - 1) {
                continuousStepCount++
                listener.onNewSteps(AppConfig.BUFFER_BOUND)
            } else {
                listener.onNewSteps(1)
            }
        } else {    // timeout
            continuousStepCount = 1
        }
        lastStepTime = thisStepTime
    }
}