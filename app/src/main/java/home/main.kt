package home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tastyfinalproject.R
import com.example.tastyfinalproject.databinding.ActivityRecipeBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up Navigation Controller with NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set up the BottomNavigationView with NavController
        NavigationUI.setupWithNavController(binding.bnv, navController)
    }

    // Handle "Up" navigation
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
