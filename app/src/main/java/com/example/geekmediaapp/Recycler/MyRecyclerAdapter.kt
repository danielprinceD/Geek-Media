package com.example.geekmediaapp.Recycler

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore.Downloads
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Button
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.geekmediaapp.R
import com.example.geekmediaapp.Retrofit.LibraryItem
import com.example.geekmediaapp.Retrofit.RetrofitInstance
import com.example.geekmediaapp.Retrofit.RetrofitService
import com.example.geekmediaapp.Toaster
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions

class MyRecyclerAdapter(private val bookList : MutableList<LibraryItem>) : RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder,parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(bookList[position])
    }
}

class MyViewHolder(private val item : View) : RecyclerView.ViewHolder(item){
    fun bind(data : LibraryItem){

        val book = item.findViewById<TextView>(R.id.bookname)
        val bookDesc = item.findViewById<TextView>(R.id.description)
        val read = item.findViewById<Button>(R.id.read)
        val subject = item.findViewById<TextView>(R.id.subject)
        val download = item.findViewById<Button>(R.id.download)
        book.text = "Name : " + data.bookname
        bookDesc.text = "Description : " + data.description
        subject.text = "Subject : ${data.subject}"
        read.setOnClickListener {
            val bundle = bundleOf("location" to data.location)
            itemView.findNavController().navigate(R.id.action_book_list_to_books_page , bundle)
        }
        download.setOnClickListener {

            downloader( "${RetrofitInstance.BASEURL}/${data.location}", data.filename)
        }
    }

    private fun downloader(url : String , filename : String){

        val request = DownloadManager.Request(Uri.parse(url))
        request.setTitle(filename)
        request.setDescription("File is downloading please wait")
        request.addRequestHeader("ngrok-skip-browser-warning" , "69420")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS , filename)
        val manager : DownloadManager = itemView.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
        Toaster.toast( itemView.context, "Downloading ${filename}")
    }
}