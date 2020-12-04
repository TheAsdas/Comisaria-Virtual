package cl.carabineros.comisariaVirtual.api;

import cl.carabineros.model.Sector;
import retrofit2.Call;
import retrofit2.Response
import retrofit2.http.*;

interface RegionsApi
{
    @GET("regiones")
    fun getRegions(): Call<ArrayList<Sector>>;

    @GET("/dpa/regiones/{codigo}")
    fun getRegionById(@Path("codigo") id: Int): Call<ArrayList<Sector>>;

    @GET("regiones/{codigo}/comunas")
    fun getCommunesOfRegion(@Path("codigo") id: Int): Call<ArrayList<Sector>>;

    @GET("comunas/{codigo}")
    fun getCommuneById(@Path("codigo") id: Int): Call<ArrayList<Sector>>;
}