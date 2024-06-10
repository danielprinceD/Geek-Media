package com.example.geekmediaapp

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.geekmediaapp.Retrofit.RetrofitInstance
import com.example.geekmediaapp.databinding.FragmentBooksPageBinding
import com.rajat.pdfviewer.HeaderData
import okhttp3.internal.wait
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class books_page : Fragment() {
     private lateinit var binding : FragmentBooksPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dialog : Dialog = Dialog(requireActivity())
        dialog.startDialog()
        val map : Map<String , String> = mapOf("ngrok-skip-browser-warning" to  "69420")
        binding = FragmentBooksPageBinding.inflate(layoutInflater)
        val bundledData = requireArguments().getString("location")
        binding.pdfViewer.initWithUrl(
            url = RetrofitInstance.BASEURL+"/"+bundledData,
            headers = HeaderData(map),
            lifecycleCoroutineScope = lifecycleScope,
            lifecycle = lifecycle
        )
        dialog.dismiss()
        return binding.root
    }


}