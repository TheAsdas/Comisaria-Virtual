package cl.carabineros.comisariaVirtual.tabFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cl.example.comisariaVirtual.R

class TabAddressAndPerson2: Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.tab_person_and_address_2,
            container,
            false);

        return view;
    }
}