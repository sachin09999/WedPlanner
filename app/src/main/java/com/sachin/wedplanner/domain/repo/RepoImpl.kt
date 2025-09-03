package com.sachin.wedplanner.domain.repo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sachin.wedplanner.common.ResultState
import com.sachin.wedplanner.data.repo.Repo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import com.sachin.wedplanner.domain.model.Banner
import com.sachin.wedplanner.domain.model.Venue
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : Repo {
    override fun registerUser(
        email: String,
        password: String,
        name: String
    ): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        val userDoc = mapOf(
                            "uid" to uid,
                            "email" to email,
                            "name" to name
                        )
                        firestore.collection("users").document(uid)
                            .set(userDoc)
                            .addOnSuccessListener {
                                trySend(ResultState.Success("User Registered Successfully"))
                                close()
                            }
                            .addOnFailureListener { e ->
                                trySend(ResultState.Error(e.message ?: "Failed to save user data"))
                                close()
                            }
                    } else {
                        trySend(ResultState.Error("User UID is null"))
                        close()
                    }
                } else {
                    trySend(ResultState.Error(task.exception?.message ?: "Registration failed"))
                    close()
                }
            }

        awaitClose { }
    }


    override fun loginUser(
        email: String,
        password: String
    ): Flow<ResultState<String>> = callbackFlow {
         trySend(ResultState.Loading)
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                trySend(ResultState.Success("User Logged In Successfully"))
                close()
            } else {
                trySend(ResultState.Error(task.exception?.message ?: "Login failed"))
                close()
            }
        }
        awaitClose { }

    }

    override fun fetchVenues(): Flow<ResultState<List<Venue>>> = callbackFlow {
        // Emit a loading state initially
        trySend(ResultState.Loading)

        // Get a reference to the 'venues' collection
        firestore.collection("venues")
            .get()
            .addOnSuccessListener { snapshot ->
                // Map the documents to your Venue data class
                val venues = snapshot.documents.mapNotNull {
                    it.toObject(Venue::class.java)?.copy(id = it.id)
                }
                // Emit the list of venues as a success result
                trySend(ResultState.Success(venues))
                close() // Close the flow after a successful fetch
            }
            .addOnFailureListener { e ->
                // Emit an error state if the fetch fails
                trySend(ResultState.Error(e.message ?: "Failed to fetch venues"))
                close() // Close the flow on failure
            }

        // Keep the flow open until explicitly closed, which happens above.
        awaitClose { }
    }

    override fun fetchBanners(): Flow<ResultState<List<Banner>>> = callbackFlow {
        trySend(ResultState.Loading)
        firestore.collection("banners")
            .get()
            .addOnSuccessListener { snapshot ->
                val banners = snapshot.documents.mapNotNull { it.toObject(Banner::class.java) }
                trySend(ResultState.Success(banners))
                close()
            }
            .addOnFailureListener { e ->
                trySend(ResultState.Error(e.message ?: "Failed to fetch banners"))
                close()
            }
        awaitClose { }
    }


}