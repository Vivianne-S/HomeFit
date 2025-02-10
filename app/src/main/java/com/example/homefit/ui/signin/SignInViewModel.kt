package com.example.homefit.ui.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isAuthenticated = MutableLiveData<Boolean>()
    val isAuthenticated: LiveData<Boolean> get() = _isAuthenticated

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    // Försök att logga in med användarens email och lösen
    fun signIn(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            // Om e-post eller lösenord är tomt syns felmeddelande
            _errorMessage.value = "Please enter both email and password"
            return
        }

        // Försök att logga in med Firebase
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Om inloggning lyckas loggas UID och sätter autentisering som true
                    Log.d("SignInViewModel", "Login successful, UID: ${auth.currentUser?.uid}")
                    _isAuthenticated.value = true
                } else {
                    // Om inloggning misslyckas sätts autentisering som false och visa felmeddelande
                    Log.e("SignInViewModel", "Login failed", task.exception)
                    _isAuthenticated.value = false
                    _errorMessage.value = task.exception?.message ?: "Unknown error"
                }
            }
    }
}