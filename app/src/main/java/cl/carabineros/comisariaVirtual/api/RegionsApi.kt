package cl.carabineros.comisariaVirtual.api;

import cl.carabineros.model.Comuna
import cl.carabineros.model.Regiones;
import retrofit2.Call;
import retrofit2.http.*;

interface RegionsApi
{
    /**
     * Get regions from API.
     *
     * @return Regiones, a class which contains an ArrayList with all the Regions.
     */
    @GET("getRegiones")
    fun getRegions(): Call<Regiones>;

    @GET("/dpa/regiones/{codigo}")
    fun getRegionById(@Path("codigo") id: Int): Call<ArrayList<Regiones>>;

    /**
     * Get Communes of a Region using its ID.
     *
     * @param id: Integer with the ID of the region which you want the communes of.
     * @return ArrayList with Communes of the Region.
     */
    @GET("getComunasByIdRegion/{id}")
    fun getCommunesOfRegion(@Path("id") id: Int): Call<ArrayList<Comuna>>;

    @GET("comunas/{codigo}")
    fun getCommuneById(@Path("codigo") id: Int): Call<ArrayList<Regiones>>;
}