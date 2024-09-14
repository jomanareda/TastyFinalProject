package home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tastyfinalproject.R
import network.Meal

class ItemAdapter(
    private val itemList: List<Meal>,
    private val onItemClick: (Meal) -> Unit  // Add this lambda parameter for click handling
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        val itemTitle: TextView = itemView.findViewById(R.id.itemTitle)
        val itemTime: TextView = itemView.findViewById(R.id.itemTime)
    }

    private fun shortenText(text: String, maxLength: Int): String {
        return if (text.length > maxLength) {
            text.substring(0, maxLength) + "..."
        } else {
            text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemTitle.text = currentItem.strMeal

        val shortenedTitle = shortenText(currentItem.strMeal, 25)
        holder.itemTitle.text = shortenedTitle

        holder.itemTime.text = "15 mins"

        Glide.with(holder.itemView.context)
            .load(currentItem.strMealThumb)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.itemImage)


        holder.itemView.setOnClickListener {
            onItemClick(currentItem)  // Call the lambda function when an item is clicked
        }
    }

    override fun getItemCount() = itemList.size
}
