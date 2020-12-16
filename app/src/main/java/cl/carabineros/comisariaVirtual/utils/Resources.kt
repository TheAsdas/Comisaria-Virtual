package cl.carabineros.comisariaVirtual.utils

import cl.carabineros.comisariaVirtual.model.Person
import cl.example.comisariaVirtual.R

class Images
{
    companion object
    {

        fun getOriginalIcon(p: Person): Int
        {
            return if (p.genero == 1) R.drawable.ic_male_small
                else R.drawable.ic_female_small;
        }

    }
}
