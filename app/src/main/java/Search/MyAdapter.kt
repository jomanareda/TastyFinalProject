package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tastyfinalproject.R
import network.Meal

class MyAdapter(private val recipeList: MutableList<Meal>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = recipeList[position]

        // Load image using Glide
        Glide.with(holder.imageView.context)
            .load(data.strMealThumb) // Load the URL from strMealThumb
            .placeholder(R.drawable.placeholder_image) // Placeholder image until the URL loads
            .into(holder.imageView)

        holder.title.text = data.strMeal
        holder.desc.text = data.desc
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    fun updateData(newList: List<Meal>) {
        recipeList.clear()
        recipeList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView2)
        val title: TextView = itemView.findViewById(R.id.title)
        val desc: TextView = itemView.findViewById(R.id.desc)
    }
}
