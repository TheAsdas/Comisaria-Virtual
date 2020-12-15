package cl.carabineros.comisariaVirtual.model

import cl.example.comisariaVirtual.R
import java.lang.Exception

/**
 * BigItem is the model of the objects that are used by the ListView on this app. It stores some
 * data that used to manage its state in the list.
 *
 * @param Any BigItems are based in an already existing Model. This defines what model it is.
 * @property originalItem The original Model which was used to build this BigItem.
 * @property title Title shown in the ListView.
 * @property subtitle Subtitle shown in the ListView.
 * @property imageId Original icon of this item, used it to restore when it is deselected.
 * @property isSelected State of the item, usually changed by user clicks.
 * @constructor Create empty Big item
 */
class BigItem<Any> (
    val originalItem: Any,
    val title: String,
    val subtitle: String,
    val imageId: Int,
    var isSelected: Boolean = false,
    )
{
    companion object Utils
    {
        fun parseListToBigItem (list: ArrayList<*>): ArrayList<BigItem<*>>
        {
            val parsedList = ArrayList<BigItem<*>>();
            val anyClass: String?;

            try
            {
                anyClass = list[0]::class.simpleName;
            }
            catch (e: Exception)
            {
                return parsedList;
            }

            println(anyClass);
            for (item in list)
            {
                parsedList.add(
                    when (anyClass)
                    {
                        "Persona" -> createPersona(item as Persona);
                        "Direccion" -> createDireccion(item as Direccion);
                        else -> throw Exception("Type not defined.")
                    }
                );
            }
            return parsedList;
        }

        fun createDireccion(d: Direccion): BigItem<Direccion>
        {
            TODO();
        }

        private fun createPersona(p: Persona): BigItem<Persona>
        {
            return BigItem(
                p,
                "${p.nombre} ${p.segundoNombre} ${p.apellidoPaterno} ${p.apellidoMaterno}",
                p.rut,
                if (p.genero == 1) R.drawable.ic_male_small else R.drawable.ic_female_small,
            )
        }
    }


    /**
     * Toggles the state of this item.
     *
     * @param state Optionally, you can choose which state to put this item by including a boolean
     * as parameter.
     */
    fun toggle(state: Boolean? = null)
    {
        isSelected = state ?: !isSelected;
    }

}