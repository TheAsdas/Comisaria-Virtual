package cl.carabineros.comisariaVirtual

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cl.carabineros.comisariaVirtual.api.RegionsApi
import cl.carabineros.comisariaVirtual.tabFragments.TabAddressAndPerson2
import cl.carabineros.comisariaVirtual.tabFragments.TabPerson1
import cl.carabineros.model.Sector
import cl.carabineros.utils.TabMethods
import cl.example.comisariaVirtual.R
import kotlinx.android.synthetic.main.activity_person_editor.*
import kotlinx.android.synthetic.main.tab_person_and_address_2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PersonEditor : AppCompatActivity()
{
    private lateinit var retrofit: Retrofit;
    private lateinit var service: RegionsApi;
    private lateinit var context: Context;

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_editor);

        configureActivity();
    }

    private fun configureActivity()
    {
        //init context
        context = this;

        configureApi();
        configureToolbar();
        configureViewPager();
        configureRegionsMenu();
    }

    private fun configureApi()
    {
        //init retrofit
        retrofit = Retrofit.Builder()
            .baseUrl("http://apis.modernizacion.cl/dpa/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //init service
        service = this.retrofit.create(RegionsApi::class.java);
    }

    private fun configureToolbar()
    {
        toolbar.setNavigationOnClickListener{finish()};
    }

    private fun configureViewPager()
    {
        val tabs = TabMethods(supportFragmentManager);
        tabs.addTab(
            Pair(
                getString(R.string.data_tab),
                TabPerson1()
            )
        );
        tabs.addTab(
            Pair(
                getString(R.string.address_tab),
                TabAddressAndPerson2()
            )
        );

        viewPager.adapter = tabs;
        tabLayout.setupWithViewPager(viewPager);
    }

    private fun configureRegionsMenu()
    {

        val regionCall = service.getRegions();

        regionCall.enqueue(object : Callback<ArrayList<Sector>> {
            /**
            On failure, show Toast with an error message.

            @param call
            @param t
             **/
            override fun onFailure(
                call: Call<ArrayList<Sector>>,
                t: Throwable
            ) {
                Toast.makeText(
                    context,
                    "Error al listar las regiones.",
                    Toast.LENGTH_LONG
                ).show();

                t.printStackTrace();
            }

            override fun onResponse(
                call: Call<ArrayList<Sector>>,
                response: Response<ArrayList<Sector>>)
            {
                val adapter = ArrayAdapter(
                    context,
                    R.layout.layout_list_item,
                    response.body()!!
                );
                selectRegion.setAdapter(adapter);

                selectRegion.setOnItemClickListener {
                        adapterView: AdapterView<*>,
                        view1: View,
                        index: Int,
                        l: Long ->
                    val selectedItem = adapterView.getItemAtPosition(index) as Sector;

                    //debug message:
                    println("Seleccionaste $selectedItem, cuya ID es ${selectedItem.codigo}");

                    loadCommunes(selectedItem);
                }
            }
        });
    }

    private fun loadCommunes(sector: Sector)
    {
        val communeCall = service.getCommunesOfRegion(sector.codigo);

        communeCall.enqueue(object : Callback<ArrayList<Sector>> {
            override fun onFailure(
                call: Call<ArrayList<Sector>>,
                t: Throwable)
            {
                Toast.makeText(
                    context, "Error al cargar las comunas de $sector",
                    Toast.LENGTH_SHORT
                ).show();

                t.printStackTrace();
            }

            override fun onResponse(
                call: Call<ArrayList<Sector>>,
                response: Response<ArrayList<Sector>>)
            {
                val adapter = ArrayAdapter(
                    context,
                    R.layout.layout_list_item,
                    response.body()!!
                );
                selectCommune.setAdapter(adapter);
            }
        });
    }


}


