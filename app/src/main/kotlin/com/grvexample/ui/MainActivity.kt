package com.grvexample.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grvexample.R
import com.grvexample.adapter.ViewType
import com.grvexample.adapter.ViewTypeAdapter
import com.grvexample.base.DataBindingActivity
import com.grvexample.databinding.ActivityMainBinding
import com.grvexample.extension.lazyN
import org.koin.android.ext.android.inject

class MainActivity : DataBindingActivity<ActivityMainBinding>() {

    private val mainVM by inject<MainVM>()

    private val adapter by lazyN { ViewTypeAdapter<ViewType<*>>(onItemActionListener = mainVM) }

    override fun layoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb.vm = mainVM
        setupAdapter()
        initObservers()
    }

    private fun setupAdapter() {
        vb.rvList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter.setList(mainVM.getList())
        vb.rvList.adapter = adapter
    }

    private fun initObservers() {
        mainVM.insertEvent.observe(this, Observer {
            adapter.insertElement(it.second, it.first)
        })
        mainVM.removeItemEvent.observe(this, Observer {
            adapter.removeElement(it)
        })
        mainVM.updateEvent.observe(this, Observer {
            adapter.updateElement(it.second, it.first)
        })
    }
}