package authenitcation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.tastyfinalproject.R


class registerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=  inflater.inflate(R.layout.fragment_register, container, false)
    //navigate to sign in from signInText
        val signInText :TextView= view.findViewById(R.id.signInText)
        signInText.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    //navigate to sign in from signInButton
        val signUpButton : Button=view.findViewById(R.id.buttonSignUp)
        signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return view
    }

}