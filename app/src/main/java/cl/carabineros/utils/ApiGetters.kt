package cl.carabineros.utils

import cl.carabineros.interfaces.ApiServices
import cl.carabineros.model.Sector
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiGetters
{
    fun getRegions(): List<Sector>?
    {
        var regions: List<Sector>? = null;

        val retrofit = Retrofit.Builder()
            .baseUrl("https://apis.modernizacion.cl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        val services = retrofit.create(ApiServices::class.java);

        services.getRegions()
            .enqueue(object: Callback<List<Sector>>
            {
                override fun onResponse(
                    call: Call<List<Sector>>,
                    response: Response<List<Sector>>)
                {
                    regions = response.body()!!;
                }

                override fun onFailure(
                    call: Call<List<Sector>>,
                    t: Throwable)
                {
                    t.printStackTrace();
                }
            }
        );

        return regions;
    }
}