package cl.carabineros.interfaces;

import cl.carabineros.model.Sector;
import retrofit2.Call;
import retrofit2.http.*;

interface ApiServices
{
    @GET("/dpa/regiones")
    fun getRegions(): Call<List<Sector>>;

    @GET("/dpa/regiones/{id}")
    fun getRegionById(@Path("id") id: Int): Call<Sector>;

    @GET("/dpa/{id}/comunas")
    fun getCommunesOfRegion(@Path("id") id: Int): Call<List<Sector>>;

    @GET("/dpa/comunas/{id}")
    fun getCommuneById(@Path("id") id: Int): Call<Sector>;
}