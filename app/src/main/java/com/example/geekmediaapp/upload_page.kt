package com.example.geekmediaapp

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.provider.CalendarContract.Colors
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.geekmediaapp.Retrofit.RetrofitInstance
import com.example.geekmediaapp.Retrofit.RetrofitService
import com.example.geekmediaapp.Retrofit.Subject
import com.example.geekmediaapp.databinding.FragmentUploadPageBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import java.io.File
import java.nio.file.FileSystem

class upload_page : Fragment() {

    private lateinit var binding: FragmentUploadPageBinding
    private lateinit var serviceInstance: RetrofitService
    private lateinit var model: MyViewModel
    private lateinit var selectedFile: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentUploadPageBinding.inflate(layoutInflater)
        serviceInstance = RetrofitInstance.getInstance().create(RetrofitService::class.java)
        var response: LiveData<Response<Subject>> = liveData {
            var res = serviceInstance.getSubject()
            emit(res)
        }

        Toaster.toast(binding.root.context, response.isInitialized.toString())


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUploadPageBinding.inflate(layoutInflater)
        model = ViewModelProvider.create(this).get(MyViewModel::class.java)
        binding.uploadText.text = ""
        binding.apply {
            uploadBtn.isEnabled = false
            var adapter = ArrayAdapter(
                binding.root.context,
                R.layout.simple_spinner_item,
                model.getSubjectList()
            )
            spinner.adapter = adapter
            uploadBtn.setOnClickListener {
                if (bookName.text.isNotEmpty() && spinner.selectedItemId.toString() != "0" && desc.text.isNotEmpty()) {
                    try {
                        uploadAction()
                    } catch (ex: Exception) {
                        Toaster.toast(root.context, "${ex}")
                    }
                } else
                    Toaster.toast(root.context, "Field is Empty")
            }
            selector.setOnClickListener { filePicker() }
        }

        return binding.root
    }

    private fun uploadAction() {
        if (selectedFile != null && binding.filename.text.isNotEmpty()) {
            val requestFile =
                RequestBody.create(MediaType.parse("application/pdf"), selectedFile )
            val pdfBody = MultipartBody.Part.createFormData("book", selectedFile.name, requestFile)
            val requestName =
                RequestBody.create(MediaType.parse("text/plain"), binding.bookName.text.toString())
            val requestDescription =
                RequestBody.create(MediaType.parse("text/plain"), binding.desc.text.toString())
            val requestSubject = RequestBody.create(
                MediaType.parse("text/plain"),
                binding.spinner.selectedItem.toString()
            )

            val req =
                serviceInstance.postBook(pdfBody, requestName, requestDescription, requestSubject)
            req.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toaster.toast(binding.root.context, "Uploaded Successful")
                    } else Toaster.toast(binding.root.context, "Upload Unsuccessful")
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toaster.toast(binding.root.context, "${t.message}")
                }
            })
        } else Toaster.toast(binding.root.context, "check the Input")
    }
    private val launcher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){it.let {
        selectedFile = File(it?.path)
        binding.filename.text = "${getFilePath(it!!)} is selected"
        Toaster.toast( binding.root.context, it.path!!)
        Toaster.toast(binding.root.context , selectedFile.path)
        binding.uploadBtn.isEnabled = true
    }
    }
    private fun filePicker() {

        launcher.launch("application/pdf")
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && data != null && resultCode == Activity.RESULT_OK && data.data != null) {
            var uri: Uri = data.data!!
        }
    }

    @SuppressLint("Range")
    private fun getFileName(uri: Uri): String {
        var result: String? = null
        activity?.contentResolver?.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))

            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != null && cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result ?: ""
    }


    private fun getFilePath(uri: Uri): String? {
        var filePath: String? = null
        val projection = arrayOf(MediaStore.Files.FileColumns.DISPLAY_NAME)
        activity?.contentResolver?.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
                filePath = cursor.getString(columnIndex)
            }
        }
        return filePath
    }

}