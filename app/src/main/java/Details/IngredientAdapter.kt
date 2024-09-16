package Details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tastyfinalproject.R

class IngredientAdapter (private val ingredients: List<Pair<String?, String?>>) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ingredientName = itemView.findViewById<TextView>(R.id.ingredient_name)
        val ingredientMeasure = itemView.findViewById<TextView>(R.id.ingredient_measure)
        val ingredientImage = itemView.findViewById<ImageView>(R.id.ingredient_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.eachrow, parent, false)
        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val (ingredient, measure) = ingredients[position]
        holder.ingredientName.text = ingredient
        holder.ingredientMeasure.text = measure
        holder.ingredientImage.setImageResource(R.drawable.ic_launcher_foreground)
    }

    override fun getItemCount() = ingredients.size

}