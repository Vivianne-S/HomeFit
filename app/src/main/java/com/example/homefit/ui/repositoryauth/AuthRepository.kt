package com.example.homefit.ui.repositoryauth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Logga in med Google
    fun signInWithGoogle(idToken: String, callback: (Boolean, String?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.localizedMessage ?: "Unknown error")
                }
            }
    }

    // Logga in med e-post och lösenord
    fun signIn(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.localizedMessage ?: "Unknown error")
                }
            }
    }

    // Registrera en ny användare
    fun signUp(email: String, password: String, name: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        // Skapa användardokument i Firestore
                        val user = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "age" to "",
                            "gender" to "",
                            "weight" to "",
                            "goal" to "",
                            "length" to ""
                        )

                        db.collection("users").document(userId)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d("AuthRepository", "User created with name: $name")
                                callback(true, null)
                            }
                            .addOnFailureListener { e ->
                                Log.e("AuthRepository", "Error creating user document", e)
                                callback(false, e.message)
                            }
                    }
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    // Återställ lösenord
    fun resetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "Password reset email sent successfully.")
                } else {
                    callback(false, task.exception?.message ?: "Unknown error")
                }
            }
    }

    // Logga ut användaren
    fun signOut() {
        auth.signOut()
    }

    // Kolla om användaren är inloggad
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}