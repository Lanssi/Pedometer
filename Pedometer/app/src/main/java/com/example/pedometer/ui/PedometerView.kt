package com.example.pedometer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pedometer.R


/**
 * Created by lanssi on 2025/04.
 */
@Composable
fun PedometerView(pedometerViewModel: PedometerViewModel) {
    val pedometerUiState by pedometerViewModel.uiState.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .safeDrawingPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.current_steps, pedometerUiState.currentSteps),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = (if (pedometerUiState.isSensorAvailable)
                    stringResource(R.string.status_counting_steps)
                    else stringResource(R.string.status_sensor_unavailable)),
            fontSize = 16.sp,
            color = if (pedometerUiState.isSensorAvailable) Color.Green else Color.Red
        )
    }
}

@Preview
@Composable
fun PedometerViewPreview() {
    PedometerView(pedometerViewModel = viewModel())
}