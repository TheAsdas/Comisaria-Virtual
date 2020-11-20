package cl.carabineros.comisariaVirtual

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItem
import androidx.appcompat.view.menu.ActionMenuItemView
import cl.carabineros.utils.ActivityMethods
import cl.example.comisariaVirtual.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_item_viewer.*
import kotlinx.android.synthetic.main.activity_list_viewer.*
import org.w3c.dom.Text
import java.lang.Exception
import java.lang.RuntimeException

class ListViewer : AppCompatActivity() {
    //Variables
    private val placeholderList = ArrayList<String>();
    private var title: String = "";

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_viewer)

        configureActivity();
    }


    private fun configureActivity()
    {
        configureToolbar();
        defineTitle();
        configureFloatingButton();
        if (toolbar.title == "¡ERROR!") addPlaceholders();
    }

    private fun configureToolbar()
    {
        toolbar.setNavigationOnClickListener{finish()};

//        val searchView = findViewById<View>(R.id.appbar_search);
//        var textView: TextView? = null;
//
//        for (i in 0..searchView.)
//        {
//            if (searchView.getChildAt(i) is TextView)
//            {
//                textView = searchView.getChildAt(i) as TextView;
//                break;
//            }
//        }
//
//
//        textView ?: run {
//            textView?.setTextColor(resources.getColor(R.color.colorWhite));
//            textView?.setHintTextColor(resources.getColor(R.color.colorWhite));
//        }

    }

    /**
     * Defines the Appbar title. This title is received through Intent's extras.
     */
    private fun defineTitle()
    {
        title = intent.getStringExtra("title").toString();
        if (title == "") title = "¡ERROR!";
        toolbar.title = title;
    }

    /**
     * Adds one hundred placeholders to the ListView.
     */
    private fun addPlaceholders()
    {
        for (i in 1..100)
        {
            placeholderList.add("Ítem $i");
        }

        var adapter = ArrayAdapter<String>(
            this,
            R.layout.layout_list_item,
            placeholderList
        )

        itemList.adapter = adapter;
    }

    /**
     * Chooses which action to give to the FloatingButton. The action assigned depends on this
     * Activity's title.
     */
    private fun configureFloatingButton()
    {
        if (title == getString(R.string.permisos)) addButton.hide();
    }

    /**
     * Sets the destination of the FloatingActionButton when clicked.
     *
     * @param Any Class with the destination.
     */
    private inline fun <reified Any> setDestination()
    {
        addButton.setOnClickListener { ActivityMethods.goTo<Any>(this) };
    }

    /**
     * Chooses the destination to go when this button is pressed, according to the Activity's title.
     *
     * @param view View.
     * @throws Exception If the destination is incorrect.
     */
    fun goToDestination(view: View)
    {
        if (title == getString(R.string.direcciones))
            ActivityMethods.goTo<AddressEditor>(this);
        else if (title == getString(R.string.personas))
            ActivityMethods.goTo<PersonEditor>(this);
        else
            throw Exception("Invalid destination.");


/*        when (title)
        {
            getString(R.string.direcciones) -> ActivityMethods.goTo<AddressEditor>(this);
            getString(R.string.personas) -> ActivityMethods.goTo<PersonEditor>(this);
            getString(R.string.permisos) -> ActivityMethods.goTo<DataPicker>(this);
            else -> throw Exception("Destination invalid");
        }*/
    }

}