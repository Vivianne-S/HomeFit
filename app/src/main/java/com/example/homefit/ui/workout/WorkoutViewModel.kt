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

    // Metod för att spara favorit
    fun saveFavorite(workoutData: WorkoutData) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            _favoriteStatus.value = "You must be logged in to save favorites."
            return
        }

        val db = FirebaseFirestore.getInstance()

        // Lägg till övningen i Firestore under användarens "favorites"-samling
        db.collection("users").document(userId)  // Använd userId för att skapa en användarspecifik samling
            .collection("favorites")  // Samlingen för favoriter
            .document(workoutData.name)  // Använd övningens namn som dokument-ID
            .set(workoutData)  // Sätt datan för övningen i Firestore
            .addOnSuccessListener {
                _favoriteStatus.value = "Exercise is saved to favorites"
            }
            .addOnFailureListener { e ->
                _favoriteStatus.value = "Faild to save exercise to favorites: ${e.message}"
            }
    }
}
