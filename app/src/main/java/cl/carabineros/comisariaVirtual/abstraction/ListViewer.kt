package cl.carabineros.comisariaVirtual.abstraction

import kotlin.collections.ArrayList;
import android.os.Bundle;
import android.view.MenuItem
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import cl.carabineros.comisariaVirtual.adapters.BigItemAdapter;
import cl.carabineros.comisariaVirtual.model.BigItem;
import cl.carabineros.comisariaVirtual.model.BigItem.Utils.parseListToBigItem
import cl.example.comisariaVirtual.R;
import kotlinx.android.synthetic.main.activity_item_viewer.*
import kotlinx.android.synthetic.main.activity_list_viewer.*;
import kotlinx.android.synthetic.main.layout_big_list_item.view.*;

/**
 * List viewer: This class will show a list of data recompiled from a Database. This list allows for
 * multi-selection and the elimination of entries.
 *
 * @param Any This defines what type of Object this list will be composed of.
 * @constructor Create empty List viewer
 */
@Suppress("UNCHECKED_CAST")
abstract class ListViewer<Any> : AppCompatActivity()
{
    //Variables:
    private var actionTitle: String = "Undefined"
            set(value) {
                toolbar.title = value;
                field = value;
            };

    private lateinit var list: ArrayList<Any>;
    private lateinit var adapter: BigItemAdapter<Any>;

    /**
     * Action for the ActionButton.
     */
    protected abstract fun action();

    /**
     * Destination in Database, from where select objects from.
     *
     * @return An ArrayList with Database objects.
     */
    protected abstract fun selectFrom(): ArrayList<Any>;

    /**
     * Destination in Database, from where delete Objects from.
     */
    protected abstract fun deleteFrom(item: Any);

    /**
     * Defines what happens when a item on the list is clicked by the user.
     *
     * @param item Item clicked by the user. It is NOT the BigItem from the list, but the original
     * object which the BigItem was built with.
     */
    protected abstract fun clickAction(item: Any);

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_viewer);

        this.configureActivity();
        println(list);
    }

    override fun onResume() {
        super.onResume()
        stopSelectionMode();

        updateList();
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId)
    {
        R.id.appbar_delete -> {
            println("Kachigga!");
            true;
        }
        else ->
        {
            println("My nigga.");
            super.onOptionsItemSelected(item);
        }
    }



    private fun configureActivity()
    {
        configureToolbar();
        configureFloatingButton();
        updateList();
        configureList();
    }

    /**
     * Configures the toolbar.
     */
    private fun configureToolbar()
    {
        toolbar.title = title;
        toolbar.setNavigationOnClickListener{ finish() };
        toolbar.menu.findItem(R.id.appbar_delete).isVisible = false;
        toolbar.menu.findItem(R.id.appbar_search).isVisible = true;
        toolbar.menu.findItem(R.id.appbar_delete).setOnMenuItemClickListener { delete() }
    }

    /**
     * Chooses which action to give to the FloatingButton. The action assigned depends on this
     * Activity's title.
     */
    private fun configureFloatingButton()
    {
        this.addButton.setOnClickListener { action() };
    }



    /**
     * Retrieves data from database and updates the list.
     */
    private fun updateList()
    {
        list = selectFrom();

        adapter = BigItemAdapter(this, parseListToBigItem(list));
        listView.adapter = adapter;
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
            else clickAction(item.originalItem);
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
        item: BigItem<Any>,
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

    private fun delete(): Boolean
    {
        val deletionList = adapter.getSelection();

        print("[");
        for (item in deletionList)
        {
            print("$item,");
            deleteFrom(item);
        }
        println("]");

        stopSelectionMode();
        updateList();

        return true;
    }

    fun setTitle(title: String)
    {
        this.title = title;

    }

}


