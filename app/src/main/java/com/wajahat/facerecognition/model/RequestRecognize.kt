package com.wajahat.facerecognition.model

import kotlinx.serialization.Serializable

/**
 * Created by Wajahat Jawaid(wajahatjawaid@gmail.com)
 */
@Serializable
data class RequestRecognize(val image: String, val gallery_name: String)