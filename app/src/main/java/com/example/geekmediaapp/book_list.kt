package com.example.geekmediaapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geekmediaapp.Recycler.MyRecyclerAdapter
import com.example.geekmediaapp.Retrofit.LibraryItem
import com.example.geekmediaapp.databinding.FragmentBookListBinding

class book_list : Fragment() {
    private lateinit var binding: FragmentBookListBinding
    private lateinit var MyList : MutableList<LibraryItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentBookListBinding.inflate(layoutInflater)

    }
    private lateinit var RecyclerAdapter : MyRecyclerAdapter

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MyList = mutableListOf()
        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        viewModel.getBookList().observe(requireActivity() , Observer {
            val iterator = it.body()?.listIterator()
            if(iterator!=null){
                while(iterator.hasNext()){
                    val cur = iterator.next()
                    MyList.add(LibraryItem(cur.id , cur.bookname , cur.description , cur.filename , cur.location , cur.subject))
                    initRecycler()
                }
            }
        })
        return binding.root
    }

    private fun initRecycler(){
        if(MyList!=null){
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        RecyclerAdapter = MyRecyclerAdapter(MyList)
        binding.recyclerView.adapter = RecyclerAdapter
        }
    }

}