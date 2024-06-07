package com.example.geekmediaapp

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.geekmediaapp.databinding.FragmentBlankBinding

class BlankFragment : Fragment() {
    private lateinit var binding : FragmentBlankBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlankBinding.inflate(layoutInflater)
        Handler().postDelayed({
                              binding.root.findNavController().navigate(R.id.action_blankFragment2_to_login_page2)
        } , 2000)
        return binding.root
    }


}