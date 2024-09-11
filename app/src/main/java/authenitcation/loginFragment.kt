package authenitcation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tastyfinalproject.R

class loginFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view=   inflater.inflate(R.layout.fragment_login, container, false)
        val signUpText : TextView = view.findViewById(R.id.signUpText)
        signUpText.setOnClickListener{
            val fragment = registerFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_auth, fragment)?.commit()
        }


        return view
    }



}