package com.example.pedometer.config

/**
 * Created by lanssi on 2025/04.
 */
object AppConfig {
    // For AccelerometerStepDetector
    const val COMMIT_LINE: Float = 9.0f             // require fine-tune
    const val INITIAL_THRESHOLD: Float = 11.5f      // require fine-tune
    const val MAX_INTERVAL: Long = 3000L
    const val MIN_INTERVAL: Long = 300L             // require fine-tune: ok
    const val BASE_ACCELERATION = 9.8f              // Note: substitute INITIAL_THRESHOLD in ThresholdWindow.getThreshold
    const val THRESHOLD_ALPHA: Float = 0.8f         // require fine-tune
    const val WINDOW_SIZE: Int = 4

    // For WalkingIdentifier
    const val BUFFER_BOUND: Int = 10

    // For UI Update
    const val UI_UPDATE_INTERVAL: Long = 3000L
}

/**
 * Combination 1
 */
//object AppConfig {
//    // For AccelerometerStepDetector
//    const val COMMIT_LINE: Float = 9.0f             // require fine-tune
//    const val INITIAL_THRESHOLD: Float = 11.5f      // require fine-tune
//    const val MAX_INTERVAL: Long = 3000L
//    const val MIN_INTERVAL: Long = 300L             // require fine-tune: ok
//    const val THRESHOLD_ALPHA: Float = 0.5f         // require fine-tune
//    const val WINDOW_SIZE: Int = 4
//
//    // For WalkingIdentifier
//    const val BUFFER_BOUND: Int = 10
//
//    // For UI Update
//    const val UI_UPDATE_INTERVAL: Long = 3000L
//}

/**
 * Combination 2
 */
//object AppConfig {
//    // For AccelerometerStepDetector
//    const val COMMIT_LINE: Float = 9.0f             // require fine-tune
//    const val INITIAL_THRESHOLD: Float = 11.5f      // require fine-tune
//    const val MAX_INTERVAL: Long = 3000L
//    const val MIN_INTERVAL: Long = 300L             // require fine-tune: ok
//    const val BASE_ACCELERATION = 9.8f
//    const val THRESHOLD_ALPHA: Float = 0.8f         // require fine-tune
//    const val WINDOW_SIZE: Int = 4
//
//    // For WalkingIdentifier
//    const val BUFFER_BOUND: Int = 10
//
//    // For UI Update
//    const val UI_UPDATE_INTERVAL: Long = 3000L
//}