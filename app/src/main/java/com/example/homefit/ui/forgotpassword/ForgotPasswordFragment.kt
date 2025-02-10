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
import com.example.homefit.ui.forgotpassword.ForgotPasswordViewModel


class ForgotPasswordFragment : Fragment() {

// Hanterar glömt lösen
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val forgotPasswordViewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // När användaren trycker på Forgot Password
        binding.btnForgotPassword.setOnClickListener {
            // Hämta och trimma den inmatade emailen
            val email = binding.etEmail.text.toString().trim()

            // Om e-postfältet är tomt visas ett felmeddelande
            if (email.isEmpty()) {
                Toast.makeText(context, "Please enter your email address", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // Anropa ViewModel för att återställa lösenordet
            forgotPasswordViewModel.resetPassword(email) { success, message ->
                if (success) {
                    // Om återställningen lyckas visas ett meddelande och navigerar till inloggningssidan
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_forgotPasswordFragment_to_signInFragment)
                } else {
                    // Om något gick fel visas ett felmeddelande för användaren
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // När användaren klickar på "Back to Sign In"-texten
        binding.tvBackToSignIn.setOnClickListener {
            // Navigera tillbaka till sign in fragmentet
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_signInFragment)
        }
    }

    // onDestroyView används för att frigöra resurser och undvika minnesläckor
    override fun onDestroyView() {
        super.onDestroyView()
        // Nollställ binding när vyn förstörs
        _binding = null
    }
}
