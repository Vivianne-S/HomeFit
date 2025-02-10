package com.example.homefit.ui.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signUp(username: String, email: String, password: String, callback: (Boolean, String?) -> Unit) {
        Log.d("SignUpViewModel", "signUp() called with email: $email")

        // Logga för att se exakt vad som skickas
        Log.d("SignUpViewModel", "username: $username, email: $email, password: $password")

        // Kontrollera om alla fält är giltiga
        if (username.isNotEmpty()) {
            Log.d("SignUpViewModel", "Username is valid")
        } else {
            Log.e("SignUpViewModel", "Username is empty")
        }

        if (email.contains("@")) {
            Log.d("SignUpViewModel", "Email contains '@'")
        } else {
            Log.e("SignUpViewModel", "Email doesn't contain '@'")
        }

        if (password.length >= 6) {
            Log.d("SignUpViewModel", "Password is long enough")
        } else {
            Log.e("SignUpViewModel", "Password is too short")
        }

        // Validering av inmatning
        if (username.isNotEmpty() && email.contains("@") && password.length >= 6) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("SignUpViewModel", "User created successfully: ${auth.currentUser?.uid}")
                        callback(true, null)
                    } else {
                        Log.e("SignUpViewModel", "Signup failed", task.exception)
                        callback(false, task.exception?.message ?: "Unknown error")
                    }
                }
        } else {
            Log.e("SignUpViewModel", "Invalid input - check email and password")
            if (password.length < 6) {
                callback(false, "Password must be at least 6 characters long")
            } else {
                callback(false, "Invalid input. Check email and password.")
            }
        }
    }
}
