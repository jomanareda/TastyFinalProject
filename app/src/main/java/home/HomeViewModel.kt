package home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import network.Meal
import network.MealResponse
import network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> get() = _meals

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        fetchMeals()
    }

    fun fetchMeals() {
        val alphabet = ('a'..'z')
        val allMeals = mutableListOf<Meal>()

        alphabet.forEach { letter ->
            RetrofitInstance.api.searchMeal(letter.toString()).enqueue(object : Callback<MealResponse> {
                override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.meals?.let { mealsList ->
                            allMeals.addAll(mealsList)
                            _meals.value = allMeals
                        }
                    } else {
                        _errorMessage.value = "Error: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    _errorMessage.value = "Failed to fetch meals: ${t.message}"
                }
            })
        }
    }

    fun fetchMealsByCategory(category: String) {
        RetrofitInstance.api.filterMealsByCategory(category).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    response.body()?.meals?.let { _meals.value = it }
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                _errorMessage.value = "Failed to fetch meals: ${t.message}"
            }
        })
    }
}
