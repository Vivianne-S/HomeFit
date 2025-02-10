package com.example.homefit.ui.forgotpassword

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun resetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("ForgotPasswordViewModel", "Password reset email sent to $email")
                    callback(true, "Success")
                } else {
                    Log.e("ForgotPasswordViewModel", "Failed to send password reset email", task.exception)
                    callback(false, task.exception?.message ?: "Unknown error")
                }
            }
    }
}