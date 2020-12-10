package cl.carabineros.adapters

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import cl.carabineros.model.BigItem
import cl.carabineros.model.Persona
import cl.example.comisariaVirtual.R
import kotlinx.android.synthetic.main.layout_big_list_item.view.*

class BigItemAdapter(
    val myContext: Context,
    val itemList: ArrayList<BigItem<Any>>)
    : ArrayAdapter<BigItem<Any>>(
    myContext,
    0,
    itemList)
{
    @RequiresApi(Build.VERSION_CODES.P)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater
            .from(myContext)
            .inflate(R.layout.layout_big_list_item, parent, false);
        val item = itemList[position];

        layout.listTitle.text = item.title;
        layout.listSubtitle.text = item.subtitle;
        layout.listIcon.setImageResource(item.imageId);

        return layout;
    }
}