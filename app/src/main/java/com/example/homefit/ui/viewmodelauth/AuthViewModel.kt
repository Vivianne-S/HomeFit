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

    // FirebaseAuth instans för att hantera autentisering
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Firebase Firestore instans för att hantera användardata
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

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
            is FirebaseAuthInvalidCredentialsException -> "Invalid credentials. Please check your email and password."
            is FirebaseAuthInvalidUserException -> "User not found. Please sign up."
            is FirebaseAuthUserCollisionException -> "Account already exists with this email. Please use a different email."
            is UnknownHostException -> "Network error. Please check your internet connection and try again."
            else -> "An unknown error occurred: ${exception?.message}"
        }
    }

    // Logga in användaren med email och lösenord
    fun signIn(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Please enter both email and password"
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AuthViewModel", "Login successful: ${auth.currentUser?.uid}")
                    _isAuthenticated.value = true
                } else {
                    val exceptionMessage = handleError(task.exception)
                    Log.e("AuthViewModel", "Login failed: $exceptionMessage")
                    _isAuthenticated.value = false
                    _errorMessage.value = exceptionMessage
                }
            }
    }

    // Registrera en ny användare
    fun signUp(email: String, password: String, name: String, callback: (Boolean, String?) -> Unit) {
        if (email.isEmpty() || password.length < 6) {
            _toastMessage.value =
                "Email cannot be empty and password must be at least 6 characters long."
            callback(
                false,
                "Email cannot be empty and password must be at least 6 characters long."
            )
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Skapa användardokument i Firestore med användarnamn
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        val user = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "age" to "",
                            "gender" to "",
                            "weight" to "",
                            "goal" to "",
                            "length" to ""
                        )

                        // Lägger till användardokument i Firestore
                        db.collection("users").document(userId)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d("AuthViewModel", "User created with name: $name")
                                _toastMessage.value = "Account created! Please sign in."
                                callback(true, null)
                            }
                            .addOnFailureListener { e ->
                                Log.e("AuthViewModel", "Error creating user document", e)
                                callback(false, e.message)
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

    // Återställa lösen
    fun resetPassword(email: String, callback: (Boolean, String) -> Unit) {
        if (email.isEmpty()) {
            callback(false, "Please enter your email address")
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AuthViewModel", "Password reset email sent to $email")
                    callback(
                        true,
                        "Password reset email sent successfully. Please check your inbox, including the spam folder."
                    )
                } else {
                    val exceptionMessage = handleError(task.exception)
                    Log.e("AuthViewModel", "Failed to send password reset email: $exceptionMessage")
                    callback(false, exceptionMessage)
                }
            }
    }

    // Logga ut användaren
    fun signOut() {
        auth.signOut()
        _isAuthenticated.value = false
    }

    // Kollar om användaren är redan inloggad
    fun checkIfUserIsLoggedIn() {
        val user = auth.currentUser
        if (user != null) {
            _isAuthenticated.value = true
        } else {
            _isAuthenticated.value = false
        }
    }
}
