package com.example.homefit.ui.viewmodelauth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homefit.ui.repositoryauth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import java.net.UnknownHostException


class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    // LiveData för att observera autentiseringens status
    private val _isAuthenticated = MutableLiveData<Boolean>()
    val isAuthenticated: LiveData<Boolean> get() = _isAuthenticated

    // LiveData för att hantera felmeddelanden
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    // LiveData för att hantera toast-meddelanden
    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> get() = _toastMessage

    private fun handleError(exception: Exception?): String {
        return when (exception) {
            is UnknownHostException -> "Network error. Please check your internet connection and try again."
            else -> "An unknown error occurred: ${exception?.message}"
        }
    }

    // Logga in med e-post och lösenord
    fun signIn(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Please enter both email and password"
            return
        }

        authRepository.signIn(email, password) { success, message ->
            if (success) {
                _isAuthenticated.value = true
            } else {
                _errorMessage.value = message
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        authRepository.signInWithGoogle(idToken) { success, message ->
            if (success) {
                _isAuthenticated.value = true
            } else {
                _errorMessage.value = message
            }
        }
    }
    // Registrera en ny användare
    fun signUp(email: String, password: String, name: String, callback: (Boolean, String?) -> Unit) {
        if (email.isEmpty() || password.length < 6) {
            _toastMessage.value = "Email cannot be empty and password must be at least 6 characters long."
            callback(false, "Email cannot be empty and password must be at least 6 characters long.")
            return
        }

        authRepository.signUp(email, password, name) { success, message ->
            if (success) {
                _toastMessage.value = "Account created! Please sign in."
            } else {
                _toastMessage.value = message
            }
            callback(success, message)
        }
    }

    // Återställ lösenord
    fun resetPassword(email: String, callback: (Boolean, String) -> Unit) {
        if (email.isEmpty()) {
            callback(false, "Please enter your email address")
            return
        }

        authRepository.resetPassword(email) { success, message ->
            if (success) {
                _toastMessage.value = message
            } else {
                _errorMessage.value = message
            }
            callback(success, message)
        }
    }

    // Logga ut användaren
    fun signOut() {
        authRepository.signOut()
        _isAuthenticated.value = false
    }

    // Kolla om användaren är inloggad
    fun checkIfUserIsLoggedIn() {
        _isAuthenticated.value = authRepository.isUserLoggedIn()
    }
}