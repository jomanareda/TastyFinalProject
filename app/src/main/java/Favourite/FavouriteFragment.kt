package Favourite

import Search.SearchViewModel
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MyAdapter
import com.example.tastyfinalproject.R
import network.Meal
import androidx.lifecycle.Observer


class FavouriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private var allMeals: List<Meal> = emptyList()
    private val viewModel: SearchViewModel by viewModels()
    private val sharedViewModel: SharedFavouriteVM by viewModels()
    private var favoriteMeals: List<Meal> = emptyList()

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

        viewModel.meals.observe(viewLifecycleOwner) { meals ->
            allMeals = meals
            filterAndUpdateRecyclerView()
        }

        sharedViewModel.selectedMealId.observe(viewLifecycleOwner, Observer { mealId ->
            mealId?.let {
                Log.d("xx", "mealId: ${it}")

                addFavoriteMealId(it)
                filterAndUpdateRecyclerView()
            }
        })
        viewModel.fetchMeals()
    }

    override fun onStart() {
        addFavoriteMealId("52977")

        super.onStart()
    }


    private fun setupRecyclerView() {
        myAdapter = MyAdapter(emptyList()) { meal ->
            Toast.makeText(requireContext(), "Clicked on ${meal.strMeal}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = myAdapter
    }

    private fun filterAndUpdateRecyclerView() {
        val favoriteMealIds = getFavoriteMealIds()
        Log.d("Favourite", "filterAndUpdateRecyclerView: ${favoriteMealIds} ")
        favoriteMeals = allMeals.filter { meal ->
            Log.d("Favourite", "Processing Meal: ${meal.idMeal}, Is Favorite: ${meal.idMeal in favoriteMealIds}")
            meal.idMeal in favoriteMealIds
        }
        Log.d("Favourite", "filterAndUpdateRecyclerView: ${favoriteMeals} ")

        updateRecyclerView(favoriteMeals)
    }

    private fun addFavoriteMealId(mealId: String) {
        val sharedPreferences = requireContext().getSharedPreferences("favourites_prefs", Context.MODE_PRIVATE)
        val favoriteMealIds = sharedPreferences.getStringSet("favorite_meal_ids", mutableSetOf()) ?: mutableSetOf()
        favoriteMealIds.add(mealId)
        sharedPreferences.edit().putStringSet("favorite_meal_ids", favoriteMealIds).apply()
    }

    private fun removeFavoriteMealId(mealId: String) {
        val sharedPreferences = requireContext().getSharedPreferences("favourites_prefs", Context.MODE_PRIVATE)
        val favoriteMealIds = sharedPreferences.getStringSet("favorite_meal_ids", mutableSetOf()) ?: mutableSetOf()
        favoriteMealIds.remove(mealId)
        sharedPreferences.edit().putStringSet("favorite_meal_ids", favoriteMealIds).apply()
    }

    private fun getFavoriteMealIds(): Set<String> {
        val sharedPreferences = requireContext().getSharedPreferences("favourites_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getStringSet("favorite_meal_ids", emptySet()) ?: emptySet()
    }

    private fun updateRecyclerView(meals: List<Meal>) {
        Log.d("Favourite", "updateRecyclerView: ${meals}")
        myAdapter.updateData(meals)
    }

}

