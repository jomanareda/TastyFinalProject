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

class MyAdapter(private var recipeList: List<Meal>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = recipeList[position]

        Glide.with(holder.imageView.context)
            .load(data.strMealThumb)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.imageView)

        holder.title.text = data.strMeal
        holder.desc.text = data.strInstructions
    }

    override fun getItemCount(): Int = recipeList.size

    fun updateData(newList: List<Meal>) {
        recipeList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView2)
        val title: TextView = itemView.findViewById(R.id.title)
        val desc: TextView = itemView.findViewById(R.id.desc)
    }
}
