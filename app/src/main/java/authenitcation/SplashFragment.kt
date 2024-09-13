package authenitcation

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.example.tastyfinalproject.R
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check sign-in status
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isSignedIn = sharedPreferences.getBoolean("isSignedIn", false)

        Handler(Looper.getMainLooper()).postDelayed({
//            if (findNavController().currentDestination?.id == R.id.splashFragment) {
//                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                if (isSignedIn) {
                    (requireActivity() as authActivity).navigateToHome()
                } else {
                    // Show login fragment if the user is not signed in
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_auth, loginFragment())
                        .commit()
            }
        }, 4000) // 4000 milliseconds delay
    }

}