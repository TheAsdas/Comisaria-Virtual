package cl.carabineros.comisariaVirtual.screens

import android.os.Bundle
import cl.carabineros.comisariaVirtual.abstraction.ListViewer
import cl.carabineros.comisariaVirtual.model.Person
import cl.carabineros.comisariaVirtual.utils.ActivityMethods
import cl.carabineros.comisariaVirtual.utils.DatabaseHandler
import cl.example.comisariaVirtual.R

class PersonListViewer: ListViewer<Person>()
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

    override fun selectFrom(): ArrayList<Person>
    {
        return DatabaseHandler(this).selectPersonas();
    }

    override fun deleteFrom(item: Person) {
        DatabaseHandler(this).deletePersona(item);
    }

    override fun clickAction(item: Person) {
        ActivityMethods.goToWithObject<PersonEditor>(
            this,
            mapOf(Pair("person", item))
        );
    }
}



