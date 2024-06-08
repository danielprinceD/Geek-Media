package com.example.geekmediaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.geekmediaapp.databinding.ActivityMainBinding
import com.example.geekmediaapp.databinding.FragmentLoginPageBinding

class login_page : Fragment() {
    private lateinit var binding: FragmentLoginPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginPageBinding.inflate(layoutInflater)
        binding.apply {
            register.setOnClickListener{routeRegister()}
            loginBtn.setOnClickListener{routeLogin()}
        }
        return binding.root
    }
    fun nav(id : Int ){
        findNavController().navigate(id)
    }
    private fun routeLogin(){
        nav(R.id.action_login_page2_to_admin_choice)
    }
    private fun routeRegister() {
        nav(R.id.action_login_page2_to_register_page2)
    }

}