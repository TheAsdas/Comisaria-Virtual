package cl.carabineros.comisariaVirtual.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import cl.carabineros.comisariaVirtual.model.BigItem
import cl.example.comisariaVirtual.R
import kotlinx.android.synthetic.main.layout_big_list_item.view.*

class BigItemAdapter<Any>(
    private val myContext: Context,
    private val itemList: ArrayList<BigItem<*>>)
    : ArrayAdapter<BigItem<*>>(
    myContext,
    0,
    itemList)
{
    var selectionMode: Boolean = false;
    private var layoutList = ArrayList<View>();

    @RequiresApi(Build.VERSION_CODES.P)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater
            .from(myContext)
            .inflate(R.layout.layout_big_list_item, parent, false);
        val item = itemList[position];

        layout.listTitle.text = item.title;
        layout.listSubtitle.text = item.subtitle;
        layout.listIcon.setImageResource(item.imageId);

        layoutList.add(layout);

        return layout;
    }

    fun getSelection(): ArrayList<Any>
    {
        val selectedItemList = ArrayList<Any>();

        for (item in itemList)
            if (item.isSelected) selectedItemList.add(item.originalItem as Any);

        return selectedItemList;
    }

    fun stopSelectionMode()
    {
        selectionMode = false;


        itemList.forEachIndexed{
                i: Int,
                bigItem: BigItem<*> ->

            if (bigItem.isSelected)
            {
                bigItem.toggle();
                layoutList[i].listIcon.setImageResource(bigItem.imageId);
                @Suppress("DEPRECATION")
                layoutList[i].listIcon.setColorFilter(
                    myContext.resources.getColor(R.color.colorGray)
                );
            }
        };

    }

}