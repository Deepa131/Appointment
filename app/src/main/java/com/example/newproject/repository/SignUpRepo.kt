package com.example.newproject.repository

interface SignUpRepo {
    fun signup(email: String,password: String, callback: (Boolean, String, String) -> Unit)
}