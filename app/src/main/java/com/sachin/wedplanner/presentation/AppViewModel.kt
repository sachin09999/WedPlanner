package com.sachin.wedplanner.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sachin.wedplanner.common.ResultState
import com.sachin.wedplanner.domain.model.Banner
import com.sachin.wedplanner.domain.model.User
import com.sachin.wedplanner.domain.model.Venue
import com.sachin.wedplanner.domain.usecase.FetchBannerUseCase
import com.sachin.wedplanner.domain.usecase.FetchVenueUseCase
import com.sachin.wedplanner.domain.usecase.LoginUseCase
import com.sachin.wedplanner.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val bannerUseCase: FetchBannerUseCase,
    private val venueUseCase: FetchVenueUseCase
) : ViewModel() {

    private val _registerScreenState = MutableStateFlow(RegisterScreenState())
    val registerScreenState = _registerScreenState.asStateFlow()

    private val _loginScreenState = MutableStateFlow(LoginScreenState())
    val loginScreenState = _loginScreenState.asStateFlow()

    private val _bannersState = MutableStateFlow(BannersState())
    val bannersState = _bannersState.asStateFlow()

    private val _venueState = MutableStateFlow(VenuesState())
    val venueState = _venueState.asStateFlow()

    init {
        fetchBanners()
        fetchVenue()
    }

    fun registerUser(
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            registerUseCase.registerUser(email, password, name).collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _registerScreenState.value = RegisterScreenState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _registerScreenState.value = RegisterScreenState(
                            isLoading = false,
                            userData = result.data
                        )
                    }

                    is ResultState.Error -> {
                        _registerScreenState.value = RegisterScreenState(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase.loginUser(email, password).collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _loginScreenState.value = LoginScreenState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _loginScreenState.value = LoginScreenState(
                            isLoading = false,
                            userData = result.data
                        )
                    }

                    is ResultState.Error -> {
                        _loginScreenState.value = LoginScreenState(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun fetchBanners() {
        Log.d("AppViewModel", "fetchBanners called")
        viewModelScope.launch {
            bannerUseCase.fetchBanner().collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _bannersState.value = BannersState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _bannersState.value = BannersState(
                            isLoading = false,
                            banners = result.data
                        )
                    }

                    is ResultState.Error -> {
                        _bannersState.value = BannersState(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun fetchVenue() {
        viewModelScope.launch {
            venueUseCase.fetchVenue().collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _venueState.value = VenuesState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _venueState.value = VenuesState(
                            isLoading = false,
                            venues = result.data
                        )
                    }

                    is ResultState.Error -> {
                        _venueState.value = VenuesState(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }

        }
    }
}

data class RegisterScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: String? = null
)

data class LoginScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userData: String? = null
)

data class BannersState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val banners: List<Banner> = emptyList()
)

data class VenuesState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val venues: List<Venue> = emptyList()
)