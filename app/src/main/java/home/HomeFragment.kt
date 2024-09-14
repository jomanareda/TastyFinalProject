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
import Favourite.SharedFavotiteVM
import androidx.fragment.app.activityViewModels
//import androidx.navigation.fragment.findNavController
import android.widget.RadioGroup
//import androidx.appcompat.widget.PopupMenu
//import android.widget.ImageButton
//import authenitcation.authActivity

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val itemList = mutableListOf<Meal>()
    private lateinit var adapter: ItemAdapter
//    private lateinit var moreButton: ImageButton
    private val sharedViewModel: SharedFavotiteVM by activityViewModels()
    private lateinit var filterGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        filterGroup = view.findViewById(R.id.filterGroup)

        adapter = ItemAdapter(itemList) { selectedMeal ->
            // Handle item click here
            sharedViewModel.setSelectedMealId(selectedMeal.idMeal)
//            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
        }

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        val leftSpacing = resources.getDimensionPixelSize(R.dimen.item_left_spacing)
        val rightSpacing = resources.getDimensionPixelSize(R.dimen.item_right_spacing)
        val bottomSpacing = resources.getDimensionPixelSize(R.dimen.item_bottom_spacing)
        recyclerView.addItemDecoration(
            SpacingItemDecoration(leftSpacing, rightSpacing, bottomSpacing)
        )

        filterGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.buttonAll -> fetchMeals()
                R.id.buttonMeal -> fetchMealsByCategory("Breakfast")
                R.id.buttonSoup -> fetchMealsByCategory("Side")
                R.id.buttonSalad -> fetchMealsByCategory("Beef")
                R.id.buttonDessert -> fetchMealsByCategory("Dessert")
            }
        }

//        moreButton.setOnClickListener {
//            showPopupMenu(it)
//        }
        fetchMeals()
    }

    private fun fetchMeals() {
        val alphabet = ('a'..'z')

        for (letter in alphabet) {
            RetrofitInstance.api.searchMeal(letter.toString()).enqueue(object : Callback<MealResponse> {
                override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                    if (response.isSuccessful) {
                        val meals = response.body()?.meals ?: emptyList()
                        val previousSize = itemList.size
                        itemList.addAll(meals)
                        adapter.notifyItemRangeInserted(previousSize, meals.size)
                    } else {
                        showError("Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    showError("Failed to fetch meals: ${t.message}")
                }
            })
        }
    }

    private fun fetchMealsByCategory(category: String) {
        RetrofitInstance.api.filterMealsByCategory(category).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals ?: emptyList()
                    itemList.clear()
                    itemList.addAll(meals)
                    adapter.notifyDataSetChanged()
                } else {
                    showError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                showError("Failed to fetch meals: ${t.message}")
            }
        })
    }
//    private fun showPopupMenu(view: View) {
//        val popupMenu = PopupMenu(requireContext(), view)
//        popupMenu.menuInflater.inflate(R.menu.more_menu, popupMenu.menu)
//        popupMenu.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.about -> {
//                    fun onLoginSuccess() {
//                        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//                        with(sharedPreferences.edit()) {
//                            putBoolean("isSignedIn", fasle)
//                            apply()
//                        }
//                        (requireActivity() as authActivity).navigateToLogin()
//                    }
//                    true
//                }
//                R.id.signout -> {
//                    Toast.makeText(context, "about selected", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                else -> false
//            }
//        }
//        popupMenu.show()
//    }

//    fun navigateToLogin() {
//        val intent = Intent(this, authActivity::class.java)
//        startActivity(intent)
//        finish()
//    }

    private fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        Log.e("API Error", errorMessage)
    }
}