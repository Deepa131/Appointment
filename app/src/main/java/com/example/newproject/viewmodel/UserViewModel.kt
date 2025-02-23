package com.example.newproject.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.newproject.model.UserModel
import com.example.newproject.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.MutableData

class UserViewModel(val repo: UserRepository) {
    fun login(email:String, password:String, callback:(Boolean, String) -> Unit){
        repo.login(email, password, callback)
    }

    fun signup(email: String,password: String, callback: (Boolean, String, String) -> Unit){
        repo.signup(email, password, callback)
    }

    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email, callback)
    }

    fun addUserToDatabase(userId:String, userModel: UserModel, callback: (Boolean, String) -> Unit){
        repo.addUserToDatabase(userId, userModel, callback)
    }

    fun getCurrentUser() : FirebaseUser?{
        return repo.getCurrentUser()
    }

    var  _userData = MutableLiveData<UserModel?>()
    var userData = MutableLiveData<UserModel?>()
        get() = _userData

    fun getUserFromDatabase(userId: String){
        repo.getUserFromDatabase(userId){
            userModel, success, message ->
            if (success){
                _userData.value = userModel
            }else{
                _userData.value = null
            }
        }
    }

    fun logout(callback: (Boolean, String) -> Unit){
        repo.logout(callback)
    }

    fun editProfile(userId: String, data:MutableMap<String, Any>, callback: (Boolean, String) -> Unit){
        repo.editProfile(userId, data, callback)
    }

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit){
        repo.uploadImage(context, imageUri, callback)
    }

}