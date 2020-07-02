package com.example.mainproject3


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mainproject3.databinding.LayoutSignupBinding
import com.example.mainproject3.databinding.LayoutStartBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.layout_signup.*
import com.google.firebase.auth.FirebaseAuth.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database

import com.google.firebase.ktx.Firebase


class fragment_signup : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding=DataBindingUtil.inflate<LayoutSignupBinding>( inflater, R.layout.layout_signup  , container, false)

        auth = getInstance()

        database=Firebase.database.reference

        binding.btnSignup1.setOnClickListener {
            signUpUser()
        }


        return binding.root
    }


    private fun signUpUser(){


        if(text_signup2.text.toString().isEmpty()){
            text_signup2.error="Please enter email"
            text_signup2.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(text_signup2.text.toString()).matches()){
            text_signup2.error="Please enter valid email"
            text_signup2.requestFocus()
            return
        }
        if(text_signup3.text.toString().isEmpty()){
            text_signup3.error="Please enter password"
            text_signup3.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(text_signup2.text.toString(), text_signup3.text.toString()).addOnCompleteListener { task->
                if (task.isSuccessful) {
                    val user :FirebaseUser? =auth.currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                onAuthSuccess(user)






                            }
                        }
                          } else {
                    // If sign in fails, display a message to the user.


                    Toast.makeText(activity, " Signup Failed", Toast.LENGTH_SHORT).show();
                }

                // ...
            }


        }
    private fun onAuthSuccess(user: FirebaseUser){
        val username =usernameFromEmail(user.email!!)

        writeNewUser(user.uid,username, user.email!!)

        view?.findNavController()?.navigate(fragment_signupDirections.actionFragmentSignupToFragmentLogin())

    }


    private fun usernameFromEmail(email:String):String{
        return if(email.contains("@")){
            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]

        }else {
            email
        }
    }



    private fun writeNewUser(userId:String, name:String, email:String){
        val user= User(name,email)
       database.child("Users").child(userId).setValue(user)

    }


    data class User(
        var username:String?="",
        var email: String=""
    )


}




