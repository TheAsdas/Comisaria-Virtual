package cl.carabineros.comisariaVirtual

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cl.carabineros.comisariaVirtual.tabFragments.*
import cl.carabineros.utils.TabMethods
import cl.example.comisariaVirtual.R
import kotlinx.android.synthetic.main.activity_address_editor.*

class AddressEditor : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_editor);

        configureActivity();
    }

    private fun configureActivity()
    {
        configureAppbar();
        configureViewPager();
    }

    private fun configureAppbar()
    {
        appbar.setNavigationOnClickListener{ finish() };
    }

    private fun configureViewPager()
    {
        val tabs = TabMethods(supportFragmentManager);

        tabs.addTab(
            Pair(
                getString(R.string.data_tab),
                TabAddress1()
            )
        );
        tabs.addTab(
            Pair(
                getString(R.string.direction_tab),
                TabAddressAndPerson2()
            )
        );

        viewPager.adapter = tabs;
        tabLayout.setupWithViewPager(viewPager);
    }
}