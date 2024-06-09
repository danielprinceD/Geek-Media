package com.example.geekmediaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geekmediaapp.databinding.FragmentBooksPageBinding

class books_page : Fragment() {
    private lateinit var binding : FragmentBooksPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBooksPageBinding.inflate(layoutInflater)

        return binding.root
    }


}