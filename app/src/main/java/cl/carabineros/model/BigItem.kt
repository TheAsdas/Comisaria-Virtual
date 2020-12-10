package cl.carabineros.model

import cl.example.comisariaVirtual.R

class BigItem<T> (
    val originalItem: T,
    val title: String,
    val subtitle: String,
    val imageId: Int,
    var isSelected: Boolean = false
    )
{
    companion object Utils
    {
        fun parseListToBigItem(list: ArrayList<Persona>): ArrayList<BigItem<Any>>
        {
            val parsedList = ArrayList<BigItem<Any>>();

            for (item in list)
            {
                parsedList.add(
                    BigItem(
                        item,
                        "${item.nombre} ${item.segundoNombre} ${item.apellidoPaterno} ${item.apellidoMaterno}",
                        item.rut,
                        if (item.genero == 1) R.drawable.ic_male_small else R.drawable.ic_female_small
                    )
                );
            }
            return parsedList;
        }
    }


    fun toggle()
    {
        isSelected = !isSelected;
    }

}