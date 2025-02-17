package com.example.homefit.ui.signin

import android.os.Bundle
import android.text.TextUtils
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


class SignInFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel
    private var binding: FragmentSignInBinding? = null // Ändra till nullable binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialisera ViewModel
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        // Lyssna på autentiseringens status
        authViewModel.isAuthenticated.observe(viewLifecycleOwner, Observer { isAuthenticated ->
            if (isAuthenticated) {
                // Navigera till CategoriesFragment när inloggningen lyckas
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

    // Hantera rensning av binding när fragmentet förstörs och förhindra minnesläckor
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

