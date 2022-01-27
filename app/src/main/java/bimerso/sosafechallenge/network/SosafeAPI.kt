package bimerso.sosafechallenge.network

import bimerso.sosafechallenge.models.PointsResponse
import bimerso.sosafechallenge.network.Url.GOOGLE_APIKEY
import bimerso.sosafechallenge.network.Url.NEARBY_URL
import retrofit2.Response
import retrofit2.http.*

interface SosafeAPI {
    @GET("nearbysearch/json")
    suspend fun searchPoints(
        @Query("location") location:String,
        @Query("radius") radius:String = "10",
        @Query("keyword") keyword : String,
        @Query("key") apikey : String = GOOGLE_APIKEY
    ): Response<PointsResponse>

}