package com.tonapp.vehicules

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Recherche par VIN — region = "USA", "EUROPE" ou "ASIE"
    @GET("api/vehicules/vin/{vin}")
    suspend fun rechercherParVin(
        @Path("vin") vin: String,
        @Query("region") region: String
    ): VehiculeResult

    // Recherche par plaque (France uniquement)
    @GET("api/vehicules/plaque/{plaque}")
    suspend fun rechercherParPlaque(
        @Path("plaque") plaque: String
    ): VehiculeResult

    // Envoi du texte collé depuis VROOM.PRO pour interprétation par l'IA
    @POST("api/vroom/interpreter")
    suspend fun interpreterTexteVroom(
        @Body requete: VroomRequest
    ): VroomResult
}
