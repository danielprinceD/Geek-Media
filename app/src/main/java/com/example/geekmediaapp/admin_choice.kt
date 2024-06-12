package com.example.geekmediaapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.geekmediaapp.databinding.FragmentAdminChoiceBinding
import com.example.geekmediaapp.databinding.FragmentLoginPageBinding

class admin_choice : Fragment() {
    private lateinit var binding: FragmentAdminChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminChoiceBinding.inflate(layoutInflater)
        binding.apply {
            uploadBook.setOnClickListener{navUpload()}
            bookList.setOnClickListener{nav(R.id.action_admin_choice_to_book_list)}
            logout.setOnClickListener{
                val shared = requireContext().getSharedPreferences("Shared" , Context.MODE_PRIVATE)
                val editor = shared.edit()
                editor.apply{
                    putString("email" , "")
                    commit()
                }
                Toaster.toast(requireContext() , "You've been logged out")
                nav(R.id.action_admin_choice_to_login_page2)
            }
        }

        return binding.root
    }
    fun nav(id : Int ){
        findNavController().navigate(id)
    }
    private fun navUpload(){
        nav(R.id.action_admin_choice_to_upload_page)
    }
}