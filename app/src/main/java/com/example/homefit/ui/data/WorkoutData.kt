package com.example.homefit.ui.data

import android.view.View
import com.example.homefit.R

data class WorkoutData(
    val name: String = "",
    val description: String = "",
    val caloriesBurned: Int = 0,
    val imageResId: Int = R.drawable.default_workout_image, // Standardbild som fallback
    val metValue: Double = 1.0, // Lägg till MET-värde, standardvärde 1.0
)