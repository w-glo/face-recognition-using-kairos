package com.wajahat.facerecognition.model

import kotlinx.serialization.Serializable

/**
 * Created by Wajahat Jawaid(wajahatjawaid@gmail.com)
 */
@Serializable
data class Transaction(val face_id: String)