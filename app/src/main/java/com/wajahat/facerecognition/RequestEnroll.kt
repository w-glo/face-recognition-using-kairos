package com.wajahat.facerecognition

import kotlinx.serialization.Serializable

/**
 * Created by Wajahat Jawaid(wajahatjawaid@gmail.com)
 */
@Serializable
data class RequestEnroll(val image: String, val gallery_name: String, val subject_id: String)