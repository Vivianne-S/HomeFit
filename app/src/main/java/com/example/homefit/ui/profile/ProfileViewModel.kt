package com.example.homefit.ui.profile

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

    fun loadProfile() {
        userId?.let { uid ->
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        _name.value = document.getString("name") ?: ""
                        _age.value = document.getString("age") ?: ""
                        _gender.value = document.getString("gender") ?: ""
                        _weight.value = document.getString("weight") ?: ""
                        _goal.value = document.getString("goal") ?: ""
                        _length.value = document.getString("length") ?: ""
                    }
                }
        }
    }

    fun updateProfile(name: String, age: String, gender: String, weight: String, goal: String, length: String) {
        userId?.let { uid ->
            val userProfile = hashMapOf(
                "name" to name,
                "age" to age,
                "gender" to gender,
                "weight" to weight,
                "goal" to goal,
                "length" to length
            )

            db.collection("users").document(uid).set(userProfile)
        }
    }
}