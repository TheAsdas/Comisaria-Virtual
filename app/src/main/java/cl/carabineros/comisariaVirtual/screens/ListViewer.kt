package cl.carabineros.comisariaVirtual.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cl.carabineros.comisariaVirtual.adapters.BigItemAdapter
import cl.carabineros.comisariaVirtual.model.BigItem
import cl.carabineros.comisariaVirtual.model.Persona
import cl.carabineros.comisariaVirtual.utils.ActivityMethods
import cl.carabineros.comisariaVirtual.utils.DatabaseHandler
import cl.example.comisariaVirtual.R
import kotlinx.android.synthetic.main.activity_list_viewer.*
import kotlinx.android.synthetic.main.layout_big_list_item.view.*

class ListViewer : AppCompatActivity()
{
    //Variables:
    private lateinit var title: String;
    private lateinit var list: ArrayList<Any>;
    private lateinit var adapter: BigItemAdapter<Any>;

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_viewer);

        configureActivity();
    }

    override fun onResume() {
        super.onResume()
        stopSelectionMode();
        updateList();
    }

    private fun configureActivity()
    {
        configureTitle();
        configureToolbar();
        configureFloatingButton();
        updateList();
        configureList();
    }

    /**
     * Retrieves the title from Intent.
     */
    private fun configureTitle()
    {
        title = intent.getStringExtra("title")!!;
    }


    /**
     * Configures the toolbar.
     */
    private fun configureToolbar()
    {
        toolbar.title = title;
        toolbar.setNavigationOnClickListener{finish()};
        toolbar.menu.findItem(R.id.appbar_delete).isVisible = false;
        toolbar.menu.findItem(R.id.appbar_search).isVisible = true;
    }

    /**
     * Chooses which action to give to the FloatingButton. The action assigned depends on this
     * Activity's title.
     */
    private fun configureFloatingButton()
    {
        //ABSTRACTABLE?
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

        addButton.setOnClickListener { ActivityMethods.goTo<PersonEditor>(this) };
    }

    /**
     * Retrieves data from database and updates the list.
     */
    private fun updateList()
    {
        list = DatabaseHandler(this).selectPersonas() as ArrayList<Any>;

        adapter = BigItemAdapter(this, BigItem.parseListToBigItem(list as ArrayList<Persona>));
        listView.adapter = adapter;

        //configAdapter();
    }

    /**
     * Configures the adapter so it responds to user's clicks.
     */
    private fun configureList ()
    {
        //The long click listener enables the selection mode.
        listView.setOnItemLongClickListener { _, _, _, _ ->

            if (!adapter.selectionMode) activateSelectionMode();
            false;
        }

        //The click listener has different actions depending if selection mode is enabled.
        listView.setOnItemClickListener { parent, view, position, _ ->
            //This variable stores the clicked item.
            val item = parent.getItemAtPosition(position) as BigItem<Any>;
            //If selection mode is active, clicking an item of the list should enable or disable it.
            if (adapter.selectionMode) toggleClickedItem(view, item, adapter);
        };
    }

    /**
     * Toggles a clicked item when selection mode is enabled.
     *
     * @param view View clicked
     * @param item Object contained by the view
     * @param adapter Adapter which contains the list.
     */
    private fun toggleClickedItem(
        view: View,
        item: BigItem<*>,
        adapter: BigItemAdapter<Any>)
    {
        val icon = view.listIcon;
        item.toggle();

        //This if will change the icon to a selected state on the object clicked.
        if (item.isSelected) {
            icon.setImageResource(R.drawable.ic_option_checked);
            @Suppress("DEPRECATION")
            icon.setColorFilter(resources.getColor(R.color.colorPrimary));
        }
        //This will restore the original icon
        else {
            icon.setImageResource(item.imageId);
            @Suppress("DEPRECATION")
            icon.setColorFilter(resources.getColor(R.color.colorGray));
        }

        updateItemCount(adapter.getSelection().size);
    }

    /**
     * This will enable selection mode.
     */
    private fun activateSelectionMode() {
        //The variable that defines if selection mode is active is saved in the adapter.
        adapter.selectionMode = true;

        //Swapping the MenuItems of the toolbar's Menu.
        toolbar.menu.findItem(R.id.appbar_search).isVisible = false;
        toolbar.menu.findItem(R.id.appbar_delete).isVisible = true;

        //Set the navigation button to deactivate selection mode.
        toolbar.setNavigationOnClickListener { stopSelectionMode() }
    }

    /**
     * This will stop selection mode.
     */
    private fun stopSelectionMode() {
        adapter.stopSelectionMode();
        //Clicking the back button also restores the Appbar to its original state.
        toolbar.menu.findItem(R.id.appbar_search).isVisible = true;
        toolbar.title = title;
        configureToolbar()
    }

    /**
     * This updates the Appbar title so its reflects the items picked by the user.
     *
     * @param itemCount Number of items activated.
     */
    private fun updateItemCount(itemCount: Int)
    {
        toolbar.title = when (itemCount)
        {
            0 -> "Ninguno seleccionado";
            1 -> "1 seleccionado";
            else -> "$itemCount seleccionados"
        }
    }
}


