package Favourite
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedFavotiteVM: ViewModel() {
     val _selectedMealId = MutableLiveData<String>()
    val selectedMealId: LiveData<String> get() = _selectedMealId

    fun setSelectedMealId(mealId: String) {
        _selectedMealId.value = mealId
    }


    }



