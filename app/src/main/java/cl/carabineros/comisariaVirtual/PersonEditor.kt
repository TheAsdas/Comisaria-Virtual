package cl.carabineros.comisariaVirtual

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cl.carabineros.comisariaVirtual.api.RegionsApi
import cl.carabineros.comisariaVirtual.tabFragments.TabAddressAndPerson2
import cl.carabineros.comisariaVirtual.tabFragments.TabPerson1
import cl.carabineros.model.*
import cl.carabineros.utils.TabMethods
import cl.example.comisariaVirtual.R
import kotlinx.android.synthetic.main.activity_person_editor.*
import kotlinx.android.synthetic.main.tab_person_and_address_2.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class PersonEditor : AppCompatActivity()
{
    private lateinit var retrofit: Retrofit;
    private lateinit var service: RegionsApi;
    private lateinit var context: Context;

    private var selectedRegion: Region? = null;
    private var selectedCommune: Comuna? = null;

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
            .baseUrl("https://www.cofralit.cl/desarrollo/Api/")
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

        regionCall.enqueue(object : Callback<Regiones> {
            override fun onFailure(
                call: Call<Regiones>,
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
                call: Call<Regiones>,
                response: Response<Regiones>)
            {
                //get the list with regions from response
                val regionList = response.body()!!.Regiones;
                //delete last item from ArrayList
                regionList.removeAt(regionList.size - 1);
                //create ArrayAdapter for the list
                val adapter = ArrayAdapter(
                    context,
                    R.layout.layout_list_item,
                    regionList
                );
                //set ArrayAdapter
                selectRegion.setAdapter(adapter);

                /* When a Region from the list is selected, it will load the Communes of said
                Region. */
                selectRegion.setOnItemClickListener {
                        adapterView: AdapterView<*>,
                        _: View,
                        index: Int,
                        _: Long ->
                    selectedRegion = adapterView.getItemAtPosition(index) as Region;

                    //debug message:
                    println("Seleccionaste $selectedRegion, cuya ID es ${selectedRegion?.IdRegion}");

                    //clear previously selected Commune
                    selectCommune.clearListSelection();
                    selectCommune.setText("");
                    selectedCommune = null;

                    loadCommunes(selectedRegion!!);
                }
            }
        });
    }

    /**
     * Load communes from the region selected by the user.
     *
     * @param region Region selected by the user.
     */
    private fun loadCommunes(region: Region)
    {
        val communeCall = service.getCommunesOfRegion(region.IdRegion);

        communeCall.enqueue(object : Callback<ArrayList<Comuna>> {
            override fun onFailure(
                call: Call<ArrayList<Comuna>>,
                t: Throwable)
            {
                //show error Toast
                Toast.makeText(
                    context, "Error al cargar las comunas de $region",
                    Toast.LENGTH_SHORT
                ).show();

                //set an empty ArrayList
                val adapter = ArrayAdapter(
                    context,
                    R.layout.layout_list_item,
                    ArrayList<Regiones>());
                selectCommune.setAdapter(adapter);

                //clear previously selected Commune
                selectCommune.clearListSelection();
                selectCommune.setText("");
                selectedCommune = null;

                //print error message in console
                t.printStackTrace();
            }

            override fun onResponse(
                call: Call<ArrayList<Comuna>>,
                response: Response<ArrayList<Comuna>>)
            {
                //extract commune list from response
                val communesList = response.body()!!;

                //create ArrayAdapter for the list
                val adapter = ArrayAdapter(
                    context,
                    R.layout.layout_list_item,
                    communesList
                );

                //set ArrayAdapter
                selectCommune.setAdapter(adapter);

                //When an item from the list is selected, this item is saved in memory.
                selectCommune.setOnItemClickListener{
                        adapterView: AdapterView<*>,
                        _: View,
                        i: Int,
                        _: Long ->
                    selectedCommune = adapterView.getItemAtPosition(i) as Comuna;
                }

            }
        });
    }


}


