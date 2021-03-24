package com.pmirkelam.cointracker.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseSource @Inject constructor(private val firebaseAuth: FirebaseAuth, private val firestore: FirebaseFirestore) {

    fun signUpUser(user: User) = firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)

    fun signInUser(email: String,password: String) = firebaseAuth.signInWithEmailAndPassword(email,password)

    fun saveUser(user: User)=firestore.collection("users").document(user.email).set(user)

    fun fetchUser()=firestore.collection("users").get()

    fun getUid() = firebaseAuth.uid
}