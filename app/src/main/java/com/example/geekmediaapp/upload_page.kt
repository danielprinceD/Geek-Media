package com.example.geekmediaapp

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.geekmediaapp.Retrofit.RetrofitInstance
import com.example.geekmediaapp.Retrofit.RetrofitService
import com.example.geekmediaapp.Retrofit.Subject
import com.example.geekmediaapp.databinding.FragmentUploadPageBinding
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.InputStream


class upload_page : Fragment() {

    private lateinit var binding: FragmentUploadPageBinding
    private lateinit var serviceInstance: RetrofitService
    private lateinit var model: MyViewModel
    private lateinit var selectedFile: File
    private lateinit var uri : Uri
    private lateinit var uploadingDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentUploadPageBinding.inflate(layoutInflater)
        serviceInstance = RetrofitInstance.getInstance().create(RetrofitService::class.java)
        var response: LiveData<Response<Subject>> = liveData {
            var res = serviceInstance.getSubject()
            emit(res)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        uploadingDialog = Dialog(requireActivity())
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
            listView.setOnClickListener{
                it.findNavController().navigate(com.example.geekmediaapp.R.id.action_upload_page_to_book_list)
            }
        }

        return binding.root
    }

    private fun uploadAction() {
        if (selectedFile != null && binding.filename.text.isNotEmpty() && binding.spinner.selectedItemId.toString() != "0") {
            uploadingDialog.startDialog()
            val requestBody = RequestBody.create("application/pdf".toMediaTypeOrNull() , selectedFile)

            val pdfBody = MultipartBody.Part.createFormData("book", selectedFile.name, requestBody)
            val requestName =
                RequestBody.create("text/plain".toMediaTypeOrNull(), binding.bookName.text.toString())
            val requestDescription =
                RequestBody.create("text/plain".toMediaTypeOrNull(), binding.desc.text.toString())
            val requestSubject = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                binding.spinner.selectedItem.toString()
            )

            val req =
                serviceInstance.postBook(pdfBody, requestName, requestDescription, requestSubject)
            req.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toaster.toast(binding.root.context, "Uploaded Successful")

                    } else Toaster.toast(binding.root.context, "Upload Unsuccessful")
                        uploadingDialog.dismiss()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    uploadingDialog.dismiss()
                    Toaster.toast(binding.root.context, "${t.message} is error")
                }
            })
        } else Toaster.toast(binding.root.context, "check the Input")

    }

    @SuppressLint("Range")
    private fun getFileName(uri: Uri): String? {
        var name: String? = null
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                name = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
        return name
    }



    private val PICK_PDF_REQUEST = 1
    private fun filePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }
        startActivityForResult(Intent.createChooser( intent,  "Select a file") ,PICK_PDF_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK) {
            data?.data?.also { it ->
                uri = it

                copyFileToExternalStorage(uri ,getFileName(uri).toString())
                selectedFile = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),getFilePath(uri!!))
                binding.filename.text = "${selectedFile.name} is selected"
//        Toaster.toast( binding.root.context, selectedFile.name)
                binding.uploadBtn.isEnabled = true
            }
        }
    }

    private fun copyFileToExternalStorage(uri: Uri , name:String) {
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        val fileName = name
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val outputFile = File(storageDir, fileName)
        inputStream?.use { input ->
            outputFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
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