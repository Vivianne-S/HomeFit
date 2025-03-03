package com.example.homefit.ui.workout

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homefit.ui.data.WorkoutData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WorkoutViewModel : ViewModel() {

    // LiveData för att skicka meddelande om spara-favorit-status
    private val _favoriteStatus = MutableLiveData<String>()
    val favoriteStatus: LiveData<String> get() = _favoriteStatus

    // Variabler för att hålla koll på träningspassets tid
    private var startTime: Long = 0
    private var endTime: Long = 0


    // Metod för att spara favorit
    fun saveFavorite(workoutData: WorkoutData) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            _favoriteStatus.value = "You must be logged in to save favorites."
            return
        }

        val db = FirebaseFirestore.getInstance()

        // Lägg till övningen i Firestore under användarens "favorites"-samling
        db.collection("users").document(userId)
            .collection("favorites")
            .document(workoutData.name)  // Använd övningens namn som dokument-ID
            .set(workoutData)  // Spara hela WorkoutData-objektet
           /* .addOnSuccessListener {
                _favoriteStatus.value = "Exercise saved to favorites"
            }
            .addOnFailureListener { e ->
                _favoriteStatus.value = "Failed to save exercise: ${e.message}"
            }*/
    }

    // beräkna i sekunder/minuter
    private fun formatDuration(durationInSeconds: Int): String {
        val minutes = durationInSeconds / 60
        val seconds = durationInSeconds % 60

        return if (minutes > 0) {
            "$minutes min och $seconds sec"
        } else {
            "$seconds sec"
        }
    }

    // Startar en träningssession genom att spara starttiden
    fun startWorkout() {
        startTime = System.currentTimeMillis()
    }

    // Avslutar träningspasset och räknar ut kaloriförbrukning
    fun endWorkout(weightKg: Double, metValue: Double): WorkoutSummary {
        endTime = System.currentTimeMillis()

        // Beräknar träningens längd i sekunder
        val durationInSeconds = ((endTime - startTime) / 1000).toInt() // Omvandla till sekunder

        val caloriesBurned = calculateCalories(durationInSeconds, weightKg, metValue)

        val formattedDuration = formatDuration(durationInSeconds) // Formaterad tid

        return WorkoutSummary(formattedDuration, caloriesBurned)
    }

    /**
     * Räknar ut kaloriförbrukning baserat på MET-värde, vikt och tid
     * @param duration Träningstid i minuter
     * @param weightKg Användarens vikt i kg
     * @param metValue MET-värde för övningen
     * @return Antal kalorier som förbränts
     */
    private fun calculateCalories(duration: Int, weightKg: Double, metValue: Double): Double {
        return (metValue * weightKg * 3.5 / 200) * (duration / 60.0)
    }

}

    // Data-klass för att hålla sammanfattning av träningspasset
    data class WorkoutSummary(val duration: String, val calories: Double)
