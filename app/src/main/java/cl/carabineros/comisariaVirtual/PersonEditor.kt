package cl.carabineros.comisariaVirtual

import android.graphics.Region
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import cl.carabineros.comisariaVirtual.tabFragments.TabPerson1
import cl.carabineros.comisariaVirtual.tabFragments.TabAddressAndPerson2
import cl.carabineros.model.Sector
import cl.carabineros.interfaces.ApiServices
import cl.carabineros.utils.ApiGetters
import cl.carabineros.utils.TabMethods
import cl.example.comisariaVirtual.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_item_viewer.*
import kotlinx.android.synthetic.main.activity_person_editor.*
import kotlinx.android.synthetic.main.activity_person_editor.toolbar
import kotlinx.android.synthetic.main.tab_person_and_address_2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PersonEditor : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_editor);

        configureActivity();
    }

    private fun configureActivity()
    {
        configureToolbar();
        configureViewPager();
        configureDropList();
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

    fun configureDropList()
    {
        val regionList = ApiGetters().getRegions();

        if (regionList == null)
        {
            Toast.makeText(
                this,
                "Ocurrió un error cargando las regiones.",
                Toast.LENGTH_LONG)
                .show();
            return;
        }

        val adapter = ArrayAdapter(
            this,
            R.layout.layout_list_item,
            regionList);

        inputRegion.setAdapter(adapter);
    }


}