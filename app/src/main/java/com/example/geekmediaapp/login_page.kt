package com.example.geekmediaapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.geekmediaapp.databinding.ActivityMainBinding
import com.example.geekmediaapp.databinding.FragmentLoginPageBinding
import com.google.firebase.auth.FirebaseAuth

class login_page : Fragment() {
    private lateinit var binding: FragmentLoginPageBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dialog: Dialog
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog = Dialog(requireActivity())
        auth = FirebaseAuth.getInstance()
        binding = FragmentLoginPageBinding.inflate(layoutInflater)
        sharedPreferences = requireContext().getSharedPreferences("Shared" , Context.MODE_PRIVATE)
        if(checkShared()){
            nav(R.id.action_login_page2_to_admin_choice)
        }
        binding.apply {
            register.setOnClickListener{routeRegister()}
            loginBtn.setOnClickListener{
                if(nameField.text.toString() != "" && passField.text.toString() != "")
                {
                    dialog.startDialog()
                    routeLogin(nameField.text.toString() , passField.text.toString())
                }
                else Toaster.toast(requireContext() , "Enter Valid Input")
            }
        }
        return binding.root
    }
    private fun checkShared() : Boolean{
        return sharedPreferences.getString("email" , "") != ""
    }
    private fun routeLogin(email : String, password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val editor : SharedPreferences.Editor = sharedPreferences.edit()
                    editor.apply{
                        putString("email" , binding.nameField.text.toString())
                        commit()
                    }
                    Toaster.toast(requireContext() , "Login Successful")
                    dialog.dismiss()
                    nav(R.id.action_login_page2_to_admin_choice)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    dialog.dismiss()
                }
            }
    }
    fun nav(id : Int ){
        findNavController().navigate(id)
    }
    private fun routeRegister() {
        nav(R.id.action_login_page2_to_register_page2)
    }

}