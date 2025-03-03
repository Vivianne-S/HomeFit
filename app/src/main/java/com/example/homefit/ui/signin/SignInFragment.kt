package com.example.homefit.ui.signin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.homefit.R
import com.example.homefit.databinding.FragmentSignInBinding
import com.example.homefit.ui.viewmodelauth.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider

class SignInFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel
    private var binding: FragmentSignInBinding? = null
    private lateinit var googleSignInClient: GoogleSignInClient

    private val RC_SIGN_IN = 9001  // Request code för Google Sign-In

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialisera ViewModel
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        // Google Sign-In setup
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("164673616717-bvlt3utj96gufkaqbbkk2q09t4peh7re.apps.googleusercontent.com")  // Replace with your actual client ID
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)

        // Kolla om användaren redan är inloggad
        authViewModel.checkIfUserIsLoggedIn()

        // Lyssna på autentiseringens status
        authViewModel.isAuthenticated.observe(viewLifecycleOwner, Observer { isAuthenticated ->
            if (isAuthenticated) {
                // Navigera till CategoriesFragment om användaren redan är inloggad
                findNavController().navigate(R.id.action_signInFragment_to_categoriesFragment)
            }
        })

        // Klicklyssnare för inloggningsknappen
        binding?.btnSignIn?.setOnClickListener {
            val email = binding?.etEmail?.text.toString()
            val password = binding?.etPassword?.text.toString()

            // Kontrollera att fält inte är tomma
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Please enter both email and password", Toast.LENGTH_SHORT).show()
            } else {
                // Försök logga in användaren
                authViewModel.signIn(email, password)
            }
        }

        // Klicklyssnare för Google Sign-In
        binding?.btnSignInGoogle?.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        // Navigera till signUpFragment
        binding?.btnSignUp?.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        // Navigera till forgotPasswordFragment
        binding?.btnForgotPassword?.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        }

        // Hantera felmeddelanden från ViewModel
        authViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })

        // Hantera toast-meddelanden
        authViewModel.toastMessage.observe(viewLifecycleOwner, Observer { toastMessage ->
            toastMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        return binding?.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { idToken ->
                    authViewModel.signInWithGoogle(idToken)
                }
            } catch (e: ApiException) {
                Log.e("SignInFragment", "Google sign-in failed", e)
                Toast.makeText(requireContext(), "Google sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Hantera rensning av binding när fragmentet förstörs och förhindra minnesläckor
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}