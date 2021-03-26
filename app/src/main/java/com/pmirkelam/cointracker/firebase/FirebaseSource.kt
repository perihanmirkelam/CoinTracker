package com.pmirkelam.cointracker.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pmirkelam.cointracker.auth.data.User
import com.pmirkelam.cointracker.coindetail.data.CoinDetail
import com.pmirkelam.cointracker.utils.Constants.COLLECTION_FAVORITES
import com.pmirkelam.cointracker.utils.Constants.COLLECTION_USERS
import javax.inject.Inject

class FirebaseSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    fun signUpUser(user: User) =
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)

    fun signInUser(email: String, password: String) =
        firebaseAuth.signInWithEmailAndPassword(email, password)

    fun saveUser(user: User) =
        firestore.collection(COLLECTION_USERS).document(user.email)
            .set(user)

    fun getUid() = firebaseAuth.uid

    private fun getUserCollection(email: String) =
        firestore.collection(COLLECTION_USERS).document(email)

    fun setFavoriteCoinDetail(user: User, coinDetail: CoinDetail) = getUserCollection(user.email)
        .collection(COLLECTION_FAVORITES).document(coinDetail.id).set(coinDetail)

    fun deleteFavoriteCoinDetail(user: User, id: String) = getUserCollection(user.email)
        .collection(COLLECTION_FAVORITES).document(id).delete()

    fun getFavoriteCoinDetails(user: User) = getUserCollection(user.email)
            .collection(COLLECTION_FAVORITES).get()

    fun isFavoriteCoinDetail(user: User, coinDetailId: String) = getUserCollection(user.email)
            .collection(COLLECTION_FAVORITES).document(coinDetailId).get()
}