package com.example.lab3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3.R

class MyNewAdapter(val ids : ArrayList<Int>, val surnames : ArrayList<String>, val names : ArrayList<String>, val lastnames : ArrayList<String>, val dates : ArrayList<String>) : RecyclerView.Adapter<MyNewAdapter.ViewHolder>() {
    override fun getItemCount() = ids.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id.text = ids[position].toString()
        holder.surname.text = surnames[position]
        holder.name.text = names[position]
        holder.lastname.text = lastnames[position]
        holder.date.text = dates[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.updated_element_of_recycler, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var id : TextView = itemView.findViewById(R.id.ID)
        var surname : TextView = itemView.findViewById(R.id.surname)
        var name : TextView = itemView.findViewById(R.id.name_s)
        var lastname : TextView = itemView.findViewById(R.id.last_name)
        var date : TextView = itemView.findViewById(R.id.Date)
    }
}