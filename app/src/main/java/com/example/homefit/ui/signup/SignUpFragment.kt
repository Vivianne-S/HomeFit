package com.example.homefit.ui.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.homefit.R
import com.example.homefit.databinding.FragmentSignUpBinding



class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModels()  // Se till att rätt ViewModel används

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("SignUpFragment", "Fragment loaded successfully")

        binding.btnSignUp.setOnClickListener {
            Log.d("SignUpFragment", "Sign Up button clicked")

            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Kollar om något av fälten är tomt
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Anropar signUp funktionen i ViewModel och hanterar resultatet
            viewModel.signUp(username, email, password) { success, errorMessage ->
                if (success) {
                    Toast.makeText(requireContext(), "Account created! Please sign in.", Toast.LENGTH_SHORT).show()
                    Log.d("SignUpFragment", "Navigating to SignInFragment")
                    findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
                } else {
                    // Visar specifikt meddelande om lösenordet är för kort
                    if (errorMessage == "Password must be at least 6 characters long") {
                        Toast.makeText(requireContext(), "Password must be at least 6 characters long", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(requireContext(), "Signup failed: $errorMessage", Toast.LENGTH_LONG).show()
                    }
                    Log.e("SignUpFragment", "Signup failed: $errorMessage")
                }
            }
        }

        // Navigerar till inloggningssidan när användaren klickar på "Sign In"
        binding.tvSignIn.setOnClickListener {
            Log.d("SignUpFragment", "Navigating to SignInFragment")
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}