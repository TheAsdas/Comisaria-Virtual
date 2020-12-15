package cl.carabineros.comisariaVirtual.utils

import cl.carabineros.comisariaVirtual.model.Persona
import cl.example.comisariaVirtual.R

class Images
{
    companion object
    {

        fun getOriginalIcon(p: Persona): Int
        {
            return if (p.genero == 1) R.drawable.ic_male_small
                else R.drawable.ic_female_small;
        }

    }
}
