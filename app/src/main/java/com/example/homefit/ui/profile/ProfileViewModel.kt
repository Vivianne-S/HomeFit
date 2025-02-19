package com.example.homefit.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _age = MutableLiveData<String>()
    val age: LiveData<String> = _age

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> = _gender

    private val _weight = MutableLiveData<String>()
    val weight: LiveData<String> = _weight

    private val _goal = MutableLiveData<String>()
    val goal: LiveData<String> = _goal

    private val _length = MutableLiveData<String>()
    val length: LiveData<String> = _length

    // Ladda användarens profil från Firestore
    fun loadProfile() {
        if (userId == null) {
            Log.e("ProfileViewModel", "Användaren är inte inloggad!")
            return
        }

        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    _name.value = document.getString("name") ?: ""
                    _age.value = document.getString("age") ?: ""
                    _gender.value = document.getString("gender") ?: ""
                    _weight.value = document.getString("weight") ?: ""
                    _goal.value = document.getString("goal") ?: ""
                    _length.value = document.getString("length") ?: ""

                    Log.d("ProfileViewModel", "Profil laddad: ${document.data}")
                } else {
                    Log.d("ProfileViewModel", "Ingen profil hittades för $userId")
                }
            }
            .addOnFailureListener { e ->
                Log.e("ProfileViewModel", "Fel vid inläsning av profil", e)
            }
    }

    //Uppdatera Firestore med användarens profil
    fun updateProfile(name: String, age: String, gender: String, weight: String, goal: String, length: String) {
        if (userId == null) {
            Log.e("ProfileViewModel", "Användaren är inte inloggad!")
            return
        }

        val userProfile = hashMapOf(
            "name" to name,
            "age" to age,
            "gender" to gender,
            "weight" to weight,
            "goal" to goal,
            "length" to length
        )

        db.collection("users").document(userId)
            .set(userProfile)
            .addOnSuccessListener {
                Log.d("ProfileViewModel", "Profil sparad för userId: $userId med data: $userProfile")
            }
            .addOnFailureListener { e ->
                Log.e("ProfileViewModel", "Fel vid uppdatering", e)
            }
    }
}
