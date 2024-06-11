package com.example.geekmediaapp

import android.content.Context
import android.content.SharedPreferences
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
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginPageBinding.inflate(layoutInflater)
        sharedPreferences = requireContext().getSharedPreferences("Shared" , Context.MODE_PRIVATE)
        if(checkShared()){
            nav(R.id.action_login_page2_to_admin_choice)
        }
        binding.apply {
            register.setOnClickListener{routeRegister()}
            loginBtn.setOnClickListener{routeLogin()}
        }
        return binding.root
    }
    private fun checkShared() : Boolean{
        return sharedPreferences.getString("name" , "") != ""
    }
    private fun routeLogin(){
        if (checkLoginStatus())
            nav(R.id.action_login_page2_to_admin_choice)
    }
    fun checkLoginStatus() : Boolean{
        return true
    }
    fun nav(id : Int ){
        findNavController().navigate(id)
    }
    private fun routeRegister() {
        nav(R.id.action_login_page2_to_register_page2)
    }

}