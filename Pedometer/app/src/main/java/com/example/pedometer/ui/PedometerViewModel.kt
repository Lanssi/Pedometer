package com.example.pedometer.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pedometer.config.AppConfig
import com.example.pedometer.pedometer.WalkingDetectorListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by lanssi on 2025/04.
 */
class PedometerViewModel: ViewModel(),  WalkingDetectorListener{
    private val _uiState = MutableStateFlow(PedometerUiState())
    val uiState: StateFlow<PedometerUiState> = _uiState.asStateFlow()

    // For UI update
    private var currentSteps: Int = 0
    private var lastUpdateTime: Long = 0L

    override fun onNewSteps(steps: Int) {
        currentSteps += steps
        updateUiState()
//        // delay UI update. Alternatively, use a timer
//        val thisUpdateTime = System.currentTimeMillis()
//        if (thisUpdateTime - lastUpdateTime > AppConfig.UI_UPDATE_INTERVAL) {
//            updateUiState()
//            lastUpdateTime = thisUpdateTime
//        }
//        Log.d("ViewModel", "onNewSteps Called")
    }

    private fun updateUiState() {
        _uiState.update { currentState ->
            currentState.copy(currentSteps = currentSteps)
        }
    }

    fun onSensorUnavailable() {
        _uiState.update { currentState ->
            currentState.copy(isSensorAvailable = false)
        }
    }
}