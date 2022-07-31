package com.wajahat.facerecognition

import io.ktor.client.request.*
import io.ktor.http.*
import timber.log.Timber

/**
 * Created by Wajahat Jawaid(wajahatjawaid@gmail.com)
 */
class KairosApi {

    suspend fun recognize(): RecognizeResponse {
        Timber.d("Recognizingss")
        return ktorHttpClient.post<RecognizeResponse>("https://slct82o04d.execute-api.ap-south-1.amazonaws.com/staging/public/experience/outing/search") {
            method = HttpMethod.Get
//            body = Request(image, galleryName)
//            headers {
//                append("Content-Type", "application/json")
//                append("app_id", "bb3c0e3d")
//                append("app_key", "74aa45ae066df94e34d04eb4d536dcdb")
//            }
        }
    }

    suspend fun recognize(image: String, galleryName: String): RecognizeResponse {
        Timber.d("Recognizing")
        return ktorHttpClient.post("https://api.kairos.com/recognize") {
            method = HttpMethod.Post
            body = RequestRecognize(image, galleryName)
        }
    }

    suspend fun enroll(image: String, galleryName: String, subjectId: String): EnrollResponse {
        Timber.d("Recognizing")
        return ktorHttpClient.post("https://api.kairos.com/enroll") {
            method = HttpMethod.Post
            body = RequestEnroll(image, galleryName, subjectId)
        }
    }

//    suspend fun fetchAllMovies() {
//        try {
//            val response = client.post("requestUrl")
//        } catch (e: java.lang.Exception) {
//            //handle Exception
//            if (e.message!!.isNotEmpty()) {
//                // known exception
//            } else {
//                //unknown exception
//            }
//        }
//    }
}