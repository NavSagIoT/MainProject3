package com.example.mainproject3

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mainproject3.databinding.LayoutLoginBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.*
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.layout_login.*


class fragment_login : Fragment() {
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding=DataBindingUtil.inflate<LayoutLoginBinding>( inflater, R.layout.layout_login, container, false)
        auth = getInstance()

        binding.btnLogin1.setOnClickListener{
            doLogin()
        }

        return binding.root
    }

    private fun doLogin(){
        if(text_login1.text.toString().isEmpty()){
            text_login1.error="Please enter email"
            text_login1.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(text_login1.text.toString()).matches()){
            text_login1.error="Please enter valid email"
            text_login1.requestFocus()
            return
        }
        if(text_login2.text.toString().isEmpty()){
            text_login2.error="Please enter password"
            text_login2.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(text_login1.text.toString(), text_login2.text.toString()).addOnCompleteListener { task->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information


                    Toast.makeText(activity, " Authentication Success", Toast.LENGTH_SHORT).show();
                    val user = auth.currentUser
                    Nextlayout(user)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(activity, " Login Failed", Toast.LENGTH_SHORT).show();
                    Nextlayout(null)
                    // ...
                }

                // ...
            }
    }
    private fun Nextlayout(currentUser: FirebaseUser?){
        if(currentUser!=null){
            if(currentUser.isEmailVerified) {
                Toast.makeText(activity, " Authentication Success", Toast.LENGTH_SHORT).show();
                val absd=2
//PASSING THE ARGUMENT POINTER OF DATABASE TO FRAGMENT MENU
                view?.findNavController()?.navigate(fragment_loginDirections.actionFragmentLoginToFragmentMenu(absd,currentUser))
            }else{
                Toast.makeText(activity, " Authentication Email sent", Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(activity, " Authentication Failed /Retry", Toast.LENGTH_SHORT).show();

        }

    }





}
