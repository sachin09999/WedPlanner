package com.sachin.wedplanner.domain.usecase

import com.sachin.wedplanner.data.repo.Repo
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repo: Repo) {
    fun registerUser(name: String, email: String, password: String) =
        repo.registerUser(name, email, password)
}