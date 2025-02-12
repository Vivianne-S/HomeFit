package com.example.homefit.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.homefit.R
import com.example.homefit.databinding.FragmentSignInBinding
import com.example.homefit.ui.viewmodelauth.AuthViewModel

class SignInFragment : Fragment() {

    // Binding för att hantera fragmentets layout från xml
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    // AuthViewModel för logiken / felmedelanden
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Om inloggningen lyckades
        authViewModel.isAuthenticated.observe(viewLifecycleOwner) { isAuthenticated ->
            if (isAuthenticated) {
                // TODO: Navigera till HomeFragment när autentiseringen är lyckad
                // FindNavController kommer att navigera till hemsidan när användaren loggar in
                // findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
            }
        }

        // Observera felmeddelanden från AuthViewModel och visa dem som Toast
        authViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        // Klicklyssnare för registreringsknappen som hämtar e-post och lösenord
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Anropa signIn funktionen från AuthViewModel
            authViewModel.signIn(email, password)
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.btnForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        }
    }

    // Rensar upp bindingen för att förhindra minnesläckor när vyn förstörs
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
