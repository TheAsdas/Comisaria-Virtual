package cl.carabineros.comisariaVirtual.utils

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class ActivityMethods() : AppCompatActivity()
{
    companion object
    {
        inline fun <reified Activity> goTo(
            context: Context,
            parameters: Map<String, String>)
        {
            val intent = Intent(context, Activity::class.java);

            for (parameter in parameters)
            {
                intent.putExtra(parameter.key, parameter.value);
            }

            context.startActivity(intent);
        }

        inline fun<reified Activity> goToWithObject(
            context: Context,
            parameters: Map<String, Serializable>)
        {
            val intent = Intent(context, Activity::class.java);

            for (parameter in parameters)
            {
                intent.putExtra(parameter.key, parameter.value);
            }

            context.startActivity(intent);
        }

        inline fun <reified Activity> goTo(
            context: Context)
        {
            context.startActivity(Intent(context, Activity::class.java));
        }
    }
}