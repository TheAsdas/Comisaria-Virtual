package cl.carabineros.comisariaVirtual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import cl.carabineros.utils.ActivityMethods
import cl.example.comisariaVirtual.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureActivity();
    }

    /**
     * Configures this Activity.
     */
    private fun configureActivity()
    {
        configureButton<ListViewer>(
            buttonPermisos,
            mapOf("title" to resources.getString(R.string.permisos))
        );

        configureButton<ListViewer>(
            buttonPersonas,
            mapOf("title" to resources.getString(R.string.personas))
        );

        configureButton<ListViewer>(
            buttonDirecciones,
            mapOf("title" to resources.getString(R.string.direcciones))
        );

    }

    /**
     * Configures a button that leads to an Activity.
     *
     * @param Any This Class is the destination Activity.
     * @param button This Object is the button you want to configure.
     * @param parameters This Map is the parameters you want to pass to the new Activity, through
     * Intent.
     */
    private inline fun<reified Any> configureButton(
        button: Button,
        parameters: Map<String, String>)
    {
        button.setOnClickListener { ActivityMethods.goTo<Any>(this, parameters) }
    }
}