package cl.carabineros.comisariaVirtual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cl.carabineros.comisariaVirtual.tabFragments.TabPerson1
import cl.carabineros.comisariaVirtual.tabFragments.TabAddressAndPerson2
import cl.carabineros.utils.TabMethods
import cl.example.comisariaVirtual.R
import kotlinx.android.synthetic.main.activity_person_editor.*
import kotlinx.android.synthetic.main.activity_person_editor.toolbar

class PersonEditor : AppCompatActivity() {
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
}