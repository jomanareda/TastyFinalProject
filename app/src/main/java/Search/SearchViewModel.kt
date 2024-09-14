package Search

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import network.Meal
import network.MealResponse
import network.RetrofitInstance
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> get() = _meals

    private var allMeals: List<Meal> = emptyList()

    fun fetchMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<MealResponse> = RetrofitInstance.api.searchMeal("").execute()
                if (response.isSuccessful) {
                    allMeals = response.body()?.meals ?: emptyList()
                    withContext(Dispatchers.Main) {
                        _meals.value = allMeals
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        // TODO: Later
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // TODO: Later
                }
            }
        }
    }

    fun performSearch(query: String) {
        val filteredMeals = allMeals.filter { meal ->
            meal.strMeal.contains(query, ignoreCase = true)
        }
        _meals.value = filteredMeals
    }

    fun resetSearch() {
        _meals.value = allMeals
    }
}
