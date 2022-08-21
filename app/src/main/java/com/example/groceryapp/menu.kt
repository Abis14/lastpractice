package com.example.groceryapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [menu.newInstance] factory method to
 * create an instance of this fragment.
 */
class menu : Fragment() {
  lateinit var rateapp:ImageView
  lateinit var shareapp:ImageView
  lateinit var logout:ImageView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_menu, container, false)
rateapp=view.findViewById(R.id.imageView9)
        shareapp=view.findViewById(R.id.imageView10)
        logout=view.findViewById(R.id.imageView11)
        logout.setOnClickListener{
  val auth:FirebaseAuth
          auth=FirebaseAuth.getInstance()
           Firebase.auth.signOut()
//            val intent=Intent(activity,SIGNIN::class.java)
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val mgoogle:GoogleSignInClient=GoogleSignIn.getClient(requireActivity(),gso)
            mgoogle.signOut().addOnCompleteListener {
            mgoogle.revokeAccess().addOnCompleteListener {
                val intent= Intent(activity, SIGNIN::class.java)
                startActivity(intent)
                activity?.finish()

            }

            }
        }
        return view    }



}