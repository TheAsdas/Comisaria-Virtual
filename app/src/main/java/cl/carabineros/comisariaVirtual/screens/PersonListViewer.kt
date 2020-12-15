package cl.carabineros.comisariaVirtual.screens

import android.os.Bundle
import cl.carabineros.comisariaVirtual.abstraction.ListViewer
import cl.carabineros.comisariaVirtual.model.Persona
import cl.carabineros.comisariaVirtual.utils.ActivityMethods
import cl.carabineros.comisariaVirtual.utils.DatabaseHandler
import cl.example.comisariaVirtual.R

class PersonListViewer: ListViewer<Persona>()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.personas));
    }
    
    override fun action()
    {
        ActivityMethods.goTo<PersonEditor>(this)
    }

    override fun selectFrom(): ArrayList<Persona>
    {
        return DatabaseHandler(this).selectPersonas();
    }

    override fun deleteFrom(item: Persona) {
        DatabaseHandler(this).deletePersona(item);
    }

    override fun clickAction(item: Persona) {
        ActivityMethods.goToWithObject<PersonEditor>(
            this,
            mapOf(Pair("person", item))
        );
    }
}



