package Search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MyAdapter
import com.example.tastyfinalproject.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import network.Meal

class SearchFragment : Fragment() {

    private lateinit var searchTextInputLayout: TextInputLayout
    private lateinit var searchEditText: TextInputEditText
    private lateinit var filterButton: ImageButton
    private lateinit var numberOfResults: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.meals.observe(this) { meals ->
            updateRecyclerView(meals)
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

        searchTextInputLayout = view.findViewById(R.id.inputLaout)
        searchEditText = searchTextInputLayout.findViewById(R.id.searchText)
        filterButton = view.findViewById(R.id.filter)
        recyclerView = view.findViewById(R.id.SearchrecyclerView)
        numberOfResults = view.findViewById(R.id.numberOfResults)

        setupRecyclerView()
        setupSearch()
        setupFilter()


        viewModel.fetchMeals()
    }

    private fun setupRecyclerView() {
        myAdapter = MyAdapter(emptyList()) { meal ->
            Toast.makeText(requireContext(), "Clicked on ${meal.strMeal}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = myAdapter
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isNotEmpty()) {
                    viewModel.performSearch(query)
                } else {
                    viewModel.resetSearch()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupFilter() {
        filterButton.setOnClickListener {
            Toast.makeText(requireContext(), "This feature is coming soon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateRecyclerView(meals: List<Meal>) {
        myAdapter.updateData(meals)
        numberOfResults.text = "${meals.size.toString()} results"
    }

}

