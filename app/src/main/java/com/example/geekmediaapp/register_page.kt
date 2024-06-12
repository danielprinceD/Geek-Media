package com.example.geekmediaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.geekmediaapp.Retrofit.User
import com.example.geekmediaapp.databinding.FragmentRegisterPageBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class register_page : Fragment() {
    private lateinit var binding : FragmentRegisterPageBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        dialog = Dialog(requireActivity())
        binding = FragmentRegisterPageBinding.inflate(layoutInflater)
        binding.apply {
            submitBtn.setOnClickListener {
                if(nameField.text.toString() != "" && passField.text.toString()!="" && desc.text.toString()!="" && desc.text.toString() == passField.text.toString())
                {
                    dialog.startDialog()
                    regiserUser(nameField.text.toString() , desc.text.toString())
                }
                else
                    Toaster.toast(requireContext() , "Enter Valid Input")
            }
            loginBtn.setOnClickListener{findNavController().navigate(R.id.action_register_page2_to_login_page2)}
        }
        return binding.root
    }

    private fun regiserUser(email : String , password : String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    dialog.dismiss()
                    Toaster.toast(requireContext(), "Register Success")
                    findNavController().navigate(R.id.action_register_page2_to_login_page2)
                } else {
                    dialog.dismiss()
                    Toaster.toast(requireContext(), "Register Failed")
                }
            }
    }



}