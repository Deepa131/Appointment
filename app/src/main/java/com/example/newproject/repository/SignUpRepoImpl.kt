package com.example.newproject.repository

import com.google.firebase.auth.FirebaseAuth

class SignUpRepoImpl(var auth : FirebaseAuth) : SignUpRepo {
    override fun signup(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            if (it.isSuccessful){
                callback(true, "Registration successful",auth.currentUser?.uid.toString())
            }else{
                callback(false, it.exception?.message.toString(),"" )
            }

        }
    }
}