package cl.carabineros.comisariaVirtual

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import cl.example.comisariaVirtual.R

class AddressEditor : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_editor);
    }
}