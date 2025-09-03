package com.sachin.wedplanner.domain.usecase

import com.sachin.wedplanner.data.repo.Repo
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repo: Repo) {
    fun loginUser(email: String, password: String) = repo.loginUser(email, password)
}