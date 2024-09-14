package authenitcation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import authenitcation.database.RegisterDatabase
import authenitcation.database.RegisterRepository
import authenitcation.login.LoginViewModel
import authenitcation.login.LoginViewModelFactory
import com.example.tastyfinalproject.R
import com.example.tastyfinalproject.databinding.FragmentLoginBinding
import home.MainActivity

class loginFragment : Fragment() {

    val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = LoginViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.myLoginViewModel= loginViewModel

        binding.lifecycleOwner = this

        loginViewModel.navigatetoRegister.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","insidi observe")
                nagivgateToRegister()
                loginViewModel.doneNavigatingRegiter()
            }
        })

        loginViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoast()
            }
        })

        loginViewModel.errotoastUsername .observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "User doesnt exist,please Register!", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastErrorUsername()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastInvalidPassword()
            }
        })

        loginViewModel.navigatetoHome.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","inside observe")
                navigateHome()
                loginViewModel.doneNavigatingHome()
            }
        })


        return binding.root
    }


    private fun nagivgateToRegister() {
        Log.i("MYTAG","navigate to register")
        findNavController()
            .navigate(R.id.action_loginFragment_to_registerFragment)

    }

    private fun navigateHome() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
//    private fun onLoginSuccess() {
//        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//        with(sharedPreferences.edit()) {
//            putBoolean("isSignedIn", true)
//            apply()
//        }
//        (requireActivity() as authActivity).navigateToHome()
//    }

}
