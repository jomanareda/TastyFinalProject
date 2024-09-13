package Favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MyAdapter
import com.example.tastyfinalproject.R
import network.Meal

class FavouriteFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var allMeals: List<Meal> = emptyList()
    private var itemId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemId = it.getString("ITEM_ID") // Retrieve the item ID
            allMeals = it.getSerializable("allMeals") as List<Meal> // Retrieve the list of meals
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)

        setupRecyclerView()

        itemId?.let { performFilter(it) }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MyAdapter(mutableListOf())
    }

    private fun performFilter(itemId: String) {
        // Filter the meals based on the received itemId
        val filteredMeal = allMeals.filter { it.idMeal == itemId }

        // Update the RecyclerView with the filtered meals
        updateRecyclerView(filteredMeal)
    }

    private fun updateRecyclerView(meals: List<Meal>) {
        // Update the RecyclerView's adapter with the filtered list
        (recyclerView.adapter as MyAdapter).updateData(meals)
    }
}
