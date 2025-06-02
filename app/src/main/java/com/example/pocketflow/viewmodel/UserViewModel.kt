package com.example.pocketflow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pocketflow.model.AppDatabase
import com.example.pocketflow.model.SharedPref
import com.example.pocketflow.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private val sharedPref = SharedPref(application)
    private val daoUser = AppDatabase.getDatabase(application).userDao()

    val signupResult = MutableLiveData<String>()
    val signinResult = MutableLiveData<String?>()

    val loggedInUserId = MutableLiveData<Int?>()


    fun registerUser(user: User) {
        launch() {
            val existingUser = daoUser.checkUsername(user.username)
            if (existingUser != null) {
                signupResult.postValue("Username Already Used")
            } else {
                daoUser.insert(user)
                signupResult.postValue("Sign Up Succesfully")
            }
        }
    }
    fun login(username: String, password: String) {
        launch() {
            val user = daoUser.login(username, password)
            if (user != null) {
                loggedInUserId.postValue(user.id)
                signinResult.postValue("Login Succesfully")
            } else {
                signinResult.postValue("Wrong Username or Password")
            }
        }
    }

    fun clearLoginResult() {
        signinResult.value = null
    }

}