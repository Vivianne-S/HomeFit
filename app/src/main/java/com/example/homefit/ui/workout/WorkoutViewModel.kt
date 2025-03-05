package com.example.homefit.ui.workout

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homefit.ui.data.WorkoutData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WorkoutViewModel (application: Application): AndroidViewModel(application) {

    // LiveData för att skicka meddelande om spara-favorit-status
    private val _favoriteStatus = MutableLiveData<String>()
    val favoriteStatus: LiveData<String> get() = _favoriteStatus

    private val sharedPreferences = application.getSharedPreferences("WorkoutPrefs", Context.MODE_PRIVATE)

    private val _elapsedTime = MutableLiveData("00:00") // LiveData för tid
    val elapsedTime: LiveData<String> get() = _elapsedTime

    private var timer: CountDownTimer? = null

    // LiveData för att hålla koll på träningsstatus
    private val _isWorkoutActive = MutableLiveData(false)
    val isWorkoutActive: LiveData<Boolean> get() = _isWorkoutActive

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

    init {
        // Återställ starttid om träningen pågår vid appstart
        startTime = sharedPreferences.getLong("startTime", 0)
        _isWorkoutActive.value = startTime > 0
    }


    // Startar en träningssession genom att spara starttiden
    fun startWorkout() {
        startTime = System.currentTimeMillis()

        // Starta en timer som uppdaterar UI:t varje sekund
        timer?.cancel()
        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val elapsed = ((System.currentTimeMillis() - startTime) / 1000).toInt()
                _elapsedTime.postValue(formatDuration(elapsed))
            }

            override fun onFinish() {}
        }.start()
    }

    fun stopWorkout() {
        timer?.cancel()
        _isWorkoutActive.value = false  // Sätt status till false
        _elapsedTime.postValue("00:00") // Återställ timer till 00:00
    }

    // Avslutar träningspasset och räknar ut kaloriförbrukning
    fun endWorkout(weightKg: Double, metValue: Double): WorkoutSummary {
        timer?.cancel()
        endTime = System.currentTimeMillis()

        // Beräknar träningens längd i sekunder
        val durationInSeconds = ((endTime - startTime) / 1000).toInt()

        val caloriesBurned = calculateCalories(durationInSeconds, weightKg, metValue)

        val formattedDuration = formatDuration(durationInSeconds) // Formaterad tid

        // Nollställ starttid i SharedPreferences
        sharedPreferences.edit().remove("startTime").apply()
        _isWorkoutActive.value = false // Uppdatera UI

        // Återställ start- och sluttid
        startTime = 0
        endTime = 0

        return WorkoutSummary(formattedDuration, caloriesBurned)
    }

    /**
     * Räknar ut kaloriförbrukning baserat på MET-värde, vikt och tid
     * @param duration Träningstid i sekunder
     * @param weightKg Användarens vikt i kg
     * @param metValue MET-värde för övningen
     * @return Antal kalorier som förbränts
     */
    private fun calculateCalories(duration: Int, weightKg: Double, metValue: Double): Double {
        val durationInMinutes = duration / 60.0 // Omvandla sekunder till minuter
        return (metValue * weightKg * 3.5 / 200) * durationInMinutes
    }

    // Formaterar tiden
    private fun formatDuration(durationInSeconds: Int): String {
        val minutes = durationInSeconds / 60
        val seconds = durationInSeconds % 60

        return String.format("%02d:%02d", minutes, seconds)
    }

}

    // Data-klass för att hålla sammanfattning av träningspasset
    data class WorkoutSummary(val duration: String, val calories: Double)
