package cl.carabineros.utils

import cl.carabineros.comisariaVirtual.api.RegionsApi
import cl.carabineros.model.Regiones
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiGetters {
    fun getRegions(): ArrayList<Regiones>? {
        val regions = ArrayList<Regiones>();


        val retrofit = Retrofit.Builder()
            .baseUrl("http://apis.modernizacion.cl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        val services = retrofit.create(RegionsApi::class.java);

//        services.getRegions()
//            .enqueue(object: Callback<List<Sector>>
//            {
//                override fun onResponse(
//                    call: Call<List<Sector>>,
//                    response: Response<List<Sector>>)
//                {
//                    val apiRegions = response.body();
//
//                    for (region in apiRegions!!)
//                    {
//                        regions.add(region);
//                    }
//
//                    println("Lista recibida de la API: $regions");
//                }
//
//                override fun onFailure(
//                    call: Call<List<Sector>>,
//                    t: Throwable)
//                {
//                    t.printStackTrace();
//                }
//            }
//        );
//        println("Lista copiada de la API: $regions");
//        return regions;
//    }
        return null;
    }
}