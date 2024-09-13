package Search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MyAdapter
import com.example.tastyfinalproject.R
import com.google.android.material.textfield.TextInputLayout
import network.Meal

class SearchFragment : Fragment() {

    private lateinit var searchEditText: TextInputLayout
    private lateinit var filterButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private var allMeals: MutableList<Meal> = mutableListOf() // Initialize as an empty list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve meals list from arguments
        arguments?.let {
            val mealsList = it.getSerializable("mealsList") as? List<Meal>
            allMeals = mealsList?.toMutableList() ?: mutableListOf()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchEditText = view.findViewById(R.id.searchText)
        filterButton = view.findViewById(R.id.filter)
        recyclerView = view.findViewById(R.id.SearchrecyclerView)

        setupRecyclerView()
        setupSearch()
        setupFilter()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = MyAdapter(allMeals)
    }

    private fun setupSearch() {
        searchEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                Toast.makeText(requireContext(), "search with recipe name", Toast.LENGTH_SHORT).show()
            } else {
                val query = searchEditText.text.toString()
                if (query.isNotEmpty()) {
                    performSearch(query)
                } else {
                    Toast.makeText(requireContext(), "Please enter a search term", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun performSearch(query: String) {
        val filteredMeals = allMeals.filter { meal ->
            meal.strMeal.contains(query, ignoreCase = true)
        }
        updateRecyclerView(filteredMeals)
    }

    private fun updateRecyclerView(filteredMeals: List<Meal>) {
        (recyclerView.adapter as? MyAdapter)?.updateData(filteredMeals)
    }

    private fun setupFilter() {
        filterButton.setOnClickListener {
            Toast.makeText(requireContext(), "This feature is coming soon", Toast.LENGTH_SHORT).show()
        }
    }
}
