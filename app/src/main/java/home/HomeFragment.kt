package home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tastyfinalproject.R
import network.Meal
import network.MealResponse
import network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val itemList = mutableListOf<Meal>()
    private lateinit var adapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = ItemAdapter(itemList)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        val leftSpacing = resources.getDimensionPixelSize(R.dimen.item_left_spacing)
        val rightSpacing = resources.getDimensionPixelSize(R.dimen.item_right_spacing)
        val bottomSpacing = resources.getDimensionPixelSize(R.dimen.item_bottom_spacing)
        recyclerView.addItemDecoration(
            SpacingItemDecoration(leftSpacing, rightSpacing, bottomSpacing)
        )
        fetchMeals()
    }

    private fun fetchMeals() {
        RetrofitInstance.api.searchMeal("chicken").enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals ?: emptyList()
                    itemList.clear()
                    itemList.addAll(meals)

                    adapter.notifyItemRangeInserted(0, meals.size)
                } else {
                    showError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                showError("Failed to fetch meals: ${t.message}")
            }
        })
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        Log.e("API Error", errorMessage)
    }
}