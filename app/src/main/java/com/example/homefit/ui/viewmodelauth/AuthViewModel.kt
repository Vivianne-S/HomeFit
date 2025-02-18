package com.example.homefit.ui.viewmodelauth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import java.net.UnknownHostException

class AuthViewModel : ViewModel() {

    // Firebase instanser för autentisering och Firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // LiveData för autentisering
    private val _isAuthenticated = MutableLiveData<Boolean>()
    val isAuthenticated: LiveData<Boolean> get() = _isAuthenticated

    // LiveData för felmeddelanden
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    // LiveData för toast-meddelanden
    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> get() = _toastMessage

    private fun handleError(exception: Exception?): String {
        return when (exception) {
            is FirebaseAuthInvalidCredentialsException -> "Invalid credentials. Please check your email and password."
            is FirebaseAuthInvalidUserException -> "User not found. Please sign up."
            is FirebaseAuthUserCollisionException -> "Account already exists with this email. Please use a different email."
            is UnknownHostException -> "Network error. Please check your internet connection and try again."
            else -> "An unknown error occurred: ${exception?.message}"
        }
    }

    // Registrera en ny användare och spara namn i Firestore
    fun signUp(email: String, password: String, name: String, callback: (Boolean, String?) -> Unit) {
        if (email.isEmpty() || password.length < 6) {
            _toastMessage.value = "Email cannot be empty and password must be at least 6 characters long."
            callback(false, "Email cannot be empty and password must be at least 6 characters long.")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        // Spara användarens namn i Firestore
                        val userData = mapOf("name" to name, "email" to email)
                        firestore.collection("users").document(user.uid)
                            .set(userData)
                            .addOnSuccessListener {
                                Log.d("AuthViewModel", "User created and name saved in Firestore.")
                                _toastMessage.value = "Account created! Please sign in."
                                callback(true, null)
                            }
                            .addOnFailureListener { e ->
                                Log.e("AuthViewModel", "Failed to save name in Firestore: ${e.message}")
                                callback(false, "Failed to save name in Firestore.")
                            }
                    }
                } else {
                    val exceptionMessage = handleError(task.exception)
                    Log.e("AuthViewModel", "Signup failed: $exceptionMessage")
                    _toastMessage.value = "Signup failed: $exceptionMessage"
                    callback(false, exceptionMessage)
                }
            }
    }

    // Logga in användaren
    fun signIn(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _toastMessage.value = "Email and password cannot be empty"
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        // Användaren är inloggad
                        _isAuthenticated.value = true
                        _toastMessage.value = "Login successful"
                    }
                } else {
                    val exceptionMessage = handleError(task.exception)
                    _toastMessage.value = exceptionMessage
                }
            }
    }

    // Hantera lösenordsåterställning
    fun resetPassword(email: String, callback: (Boolean, String) -> Unit) {
        if (email.isEmpty()) {
            callback(false, "Please enter your email address")
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "Password reset email sent. Please check your inbox.")
                } else {
                    callback(false, "Failed to send reset email. Please try again.")
                }
            }
    }

    // Logga ut användaren
    fun signOut() {
        auth.signOut()
        _isAuthenticated.value = false
    }

    // Kolla om användaren är redan inloggad
    fun checkIfUserIsLoggedIn() {
        val user = auth.currentUser
        if (user != null) {
            _isAuthenticated.value = true
        } else {
            _isAuthenticated.value = false
        }
    }
}
