package com.example.lab7.data

interface DataListener{
    fun onGoodAdded(id:Int)
    fun onGoodRemoved(id:Int)
    fun onGoodChanged(id: Int)
}