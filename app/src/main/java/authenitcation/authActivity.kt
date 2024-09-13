package authenitcation

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.tastyfinalproject.R
import com.example.tastyfinalproject.databinding.ActivityAuthBinding
import home.MainActivity

class authActivity : AppCompatActivity() {

//    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAuthBinding
//    private  lateinit var  navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


// to navigate between fragment
//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment_auth) as NavHostFragment
//        navController = navHostFragment.navController

// Load the splash fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_auth, SplashFragment())
            .commit()

    }
        fun navigateToHome() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

