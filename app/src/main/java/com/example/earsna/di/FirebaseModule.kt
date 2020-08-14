package com.example.earsna.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object FirebaseModule {

    fun getFirebaseAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

}