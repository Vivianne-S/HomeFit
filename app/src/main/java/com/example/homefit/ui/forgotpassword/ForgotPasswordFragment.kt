package com.example.homefit.ui.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.homefit.R
import com.example.homefit.databinding.FragmentForgotPasswordBinding
import com.example.homefit.ui.viewmodelauth.AuthViewModel

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    // ViewModel för autentisering
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observera eventuella felmeddelanden från ViewModel
        authViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        // Klicklyssnare för att återställa lösenord
        binding.btnForgotPassword.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()

            // Observera felmeddelanden från AuthViewModel och visa dem som Toast
            authViewModel.resetPassword(email) { success, message ->
                if (success) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_forgotPasswordFragment_to_signInFragment)
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvBackToSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_signInFragment)
        }
    }
    // Rensar upp bindingen för att förhindra minnesläckor när vyn förstörs
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
