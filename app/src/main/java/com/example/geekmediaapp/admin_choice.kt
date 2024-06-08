package com.example.geekmediaapp

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
            bookList.setOnClickListener{}
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