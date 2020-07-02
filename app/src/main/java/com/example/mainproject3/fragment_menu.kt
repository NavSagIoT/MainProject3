package com.example.mainproject3

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.example.mainproject3.databinding.LayoutMenuBinding
import com.example.mainproject3.fragment_menuArgs.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.layout_menu.*

import com.google.firebase.database.ktx.database as database


class fragment_menu : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding= DataBindingUtil.inflate<LayoutMenuBinding>( inflater, R.layout.layout_menu, container, false)




//GETTING THE UID OF THE USER WHICH LOGGED IN

        val args= arguments?.let { fromBundle(it) }

        if (args != null) {
            Toast.makeText(activity, " ${args.absd}", Toast.LENGTH_LONG).show()
        };

        val user= args?.currentuser
        val username = user?.uid.toString()

        Toast.makeText(activity, " $username", Toast.LENGTH_LONG).show()


        val database =Firebase.database


        val User = database.getReference("Users")


        val myRef2=User.child("$username").child("data")

        User.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot!!.children
                children.forEach {
                    val value = it.getValue()

                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled",error.toException())
            }
        })


        binding.btnMenu1.setOnClickListener{
            if(binding.btnMenu1.isChecked) myRef2.child("FAN0").setValue(1) else myRef2.child("FAN0").setValue(0)

        }

        binding.btnMenu2.setOnClickListener{
            if(binding.btnMenu2.isChecked) myRef2.child("LED0").setValue(1) else myRef2.child("LED0").setValue(0)
        }


        return binding.root
    }



}