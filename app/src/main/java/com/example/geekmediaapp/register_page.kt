package com.example.geekmediaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.geekmediaapp.databinding.FragmentRegisterPageBinding

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
            loginBtn.setOnClickListener{findNavController().navigate(R.id.action_register_page2_to_login_page2)}
        }
        return binding.root
    }

}