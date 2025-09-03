package com.sachin.wedplanner.domain.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sachin.wedplanner.data.repo.Repo
import com.sachin.wedplanner.domain.repo.RepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideRepo(auth : FirebaseAuth, firestore: FirebaseFirestore) : Repo  = RepoImpl(auth, firestore)

}