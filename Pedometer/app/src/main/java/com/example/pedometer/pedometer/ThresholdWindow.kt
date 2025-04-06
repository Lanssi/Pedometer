package com.example.pedometer.pedometer

import com.example.pedometer.config.AppConfig

/**
 * Created by lanssi on 2025/04.
 */
class ThresholdWindow(private val capacity: Int) {
    private val window = FloatArray(capacity)
    private var size = 0
    private var head = 0
    private var tail = 0
    private var sum = 0.0f

    fun updateWindow(peak: Float) {
        if (size == capacity) {
            sum -= window[tail]
            tail = (tail + 1) % capacity
            size--
        }
        window[head] = peak
        head = (head + 1) % capacity
        size++
        sum += peak
    }

    fun getThreshold(): Float {
        val average: Float = if (size == 0) {0.0f} else {sum / size}
        return (average - AppConfig.BASE_ACCELERATION) * AppConfig.THRESHOLD_ALPHA + AppConfig.BASE_ACCELERATION
    }

    fun resetWindow() {
        size = 0
        head = 0
        tail = 0
        sum = 0.0f
    }
}