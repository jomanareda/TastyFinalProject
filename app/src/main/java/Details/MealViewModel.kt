package Details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import network.Meal
import network.MealResponse
import network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel : ViewModel() {
    private val _meal = MutableLiveData<Meal>()
    val meal: LiveData<Meal> get() = _meal

    private val _ingredients = MutableLiveData<List<Pair<String?, String?>>>()
    val ingredients: LiveData<List<Pair<String?, String?>>>
        get() = _ingredients
    private val _youtubeUrl = MutableLiveData<String?>()
    val youtubeUrl: LiveData<String?> get() = _youtubeUrl

    fun fetchMeal(mealId: String) {
        RetrofitInstance.api.getMeal(mealId).enqueue(object : Callback<MealResponse> {
            override fun onResponse(
                call: Call<MealResponse>,
                response: Response<MealResponse>
            ) {
                if (response.isSuccessful) {
                    _meal.value = response.body()?.meals?.firstOrNull()
                    _ingredients.value = extractIngredients( response.body()?.meals?.firstOrNull())
                    _youtubeUrl.value = _meal.value?.strYoutube

                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.d("error",t.message.toString())
            }

        })
        }

    private fun extractIngredients(meal: Meal?): List<Pair<String?, String?>> {
        return meal?.let {
            listOf(
                Pair(it.strIngredient1, it.strMeasure1),
                Pair(it.strIngredient2, it.strMeasure2),
                Pair(it.strIngredient3, it.strMeasure3),
                Pair(it.strIngredient4, it.strMeasure4),
                Pair(it.strIngredient5, it.strMeasure5),
                Pair(it.strIngredient6, it.strMeasure6),
                Pair(it.strIngredient7, it.strMeasure7),
                Pair(it.strIngredient8, it.strMeasure8),
                Pair(it.strIngredient9, it.strMeasure9),
                Pair(it.strIngredient10, it.strMeasure10),
                Pair(it.strIngredient11, it.strIngredient11)
            ).filter { it.first != null && it.first!!.isNotBlank() }
        } ?: emptyList()
    }

}