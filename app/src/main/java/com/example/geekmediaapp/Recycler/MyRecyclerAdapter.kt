package com.example.geekmediaapp.Recycler

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.geekmediaapp.R
import com.example.geekmediaapp.Retrofit.LibraryItem
import com.example.geekmediaapp.Toaster

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
        book.text = "Name : " + data.bookname
        bookDesc.text = "Description : " + data.description
        read.setOnClickListener {
            itemView.findNavController().navigate(R.id.action_book_list_to_books_page)
        }
    }
}