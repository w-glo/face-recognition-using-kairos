package com.wajahat.facerecognition.model

import kotlinx.serialization.SerialName

/**
 * Created by Wajahat Jawaid(wajahatjawaid@gmail.com)
 */
data class UserEntity(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("currentRank")
    val currentRank: Int,
    @SerialName("totalStars")
    val totalStars: Int,
    @SerialName("totalWordsMastered")
    val totalWordsMastered: Int,
)