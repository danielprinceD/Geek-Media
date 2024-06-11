package com.example.geekmediaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.geekmediaapp.Retrofit.User
import com.example.geekmediaapp.databinding.FragmentRegisterPageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class register_page : Fragment() {
    private lateinit var binding : FragmentRegisterPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterPageBinding.inflate(layoutInflater)
        binding.apply {
            submitBtn.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                if(nameField.text.isNotEmpty() && desc.text.isNotEmpty() && passField.text.isNotEmpty() && desc.text == passField.text)
                    registerAction(User(nameField.text.toString() , desc.text.toString() ))
                }
            }
            loginBtn.setOnClickListener{findNavController().navigate(R.id.action_register_page2_to_login_page2)}
        }
        return binding.root
    }

    private suspend fun registerAction(user : User) {
        val viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        if(viewModel.registerUser(User( user.name,user.password )))
            Toaster.toast(requireActivity(), "Registered Successfully")
        else Toaster.toast(requireActivity() , "Registered not successful")
    }

}