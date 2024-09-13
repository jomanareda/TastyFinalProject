package network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("api/json/v1/1/search.php")
    fun searchMeal(@Query("s") mealName: String): Call<MealResponse>

    @GET("api/json/v1/1/filter.php")
    fun filterMealsByIngredient(@Query("i") ingredient: String): Call<MealResponse>
}
