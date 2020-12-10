package cl.carabineros.comisariaVirtual

import android.os.Bundle

import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import cl.carabineros.adapters.BigItemAdapter
import cl.carabineros.model.BigItem
import cl.carabineros.model.Persona
import cl.carabineros.utils.ActivityMethods
import cl.carabineros.utils.DatabaseHandler
import cl.example.comisariaVirtual.R
import kotlinx.android.synthetic.main.activity_list_viewer.*

class ListViewer : AppCompatActivity() {
    //Variables
    private val placeholderList = ArrayList<String>();
    private var title: String = "";

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_viewer);
        configureActivity();
    }

    override fun onResume() {
        super.onResume()
        loadDbData();
    }

    private fun configureActivity()
    {
        configureToolbar();
        defineTitle();
        configureFloatingButton();
        loadDbData();

    }

    private fun configureToolbar()
    {
        toolbar.setNavigationOnClickListener{finish()};
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

        val adapter = ArrayAdapter(
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

        when (title)
        {
            getString(R.string.direcciones) -> setDestination<AddressEditor>()
            getString(R.string.personas) -> setDestination<PersonEditor>()
            else -> addButton.hide();
        }
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

    private fun loadDbData()
    {
        val listPersonas = DatabaseHandler(this).selectPersonas() as ArrayList<Persona>;

        println("Lista: ")
        for (person in listPersonas)
        {
            println(person.nombre + " " + person.apellidoPaterno);
        }

        val adapter = BigItemAdapter(this, BigItem.parseListToBigItem(listPersonas));
        itemList.adapter = adapter;
    }






}


