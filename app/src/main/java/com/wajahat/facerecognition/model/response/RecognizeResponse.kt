package com.wajahat.facerecognition.model.response

import com.wajahat.facerecognition.model.Image
import kotlinx.serialization.Serializable

/**
 * Created by Wajahat Jawaid(wajahatjawaid@gmail.com)
 */
@Serializable
data class RecognizeResponse(val images: List<Image>? = null)