package cl.carabineros.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import cl.carabineros.model.Direccion
import cl.carabineros.model.Persona

class DatabaseHandler
    (context: Context)
    :SQLiteOpenHelper(context, dbName, null, dbVersion)
{
    companion object
    {
        private var dbName = "dbCarabineros";
        private var dbVersion = 1;

        /* Tabla de personas */
        private var tabla_personas = "personas";
        private var persona_id = "id";
        private var persona_nombre = "nombre";
        private var persona_segundoNombre = "segundo_nombre";
        private var persona_apellidoPaterno = "apellido_paterno";
        private var persona_apellidoMaterno = "apellido_materno";
        private var persona_rut = "rut";
        private var persona_numeroSerie = "numero_serie";
        private var persona_genero = "genero";
        private var persona_region = "region";
        private var persona_comuna = "comuna";
        private var persona_direccion = "direccion";
        private var persona_claveUnica = "clave_unica";
        private var persona_correo = "email";

        /* Tabla de direcciones */
        private var tabla_direcciones = "direcciones";
        private var direccion_id = "id";
        private var direccion_nombreLugar = "nombre_lugar";
        private var direccion_tipo = "direccion_tipo";
        private var direccion_region = "region";
        private var direccion_comuna = "comuna";
        private var direccion_direccion = "direccion";


    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val creationQuery: String =
            "CREATE TABLE $tabla_personas (" +
                    "$persona_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$persona_nombre TEXT NOT NULL, " +
                    "$persona_segundoNombre TEXT NOT NULL, " +
                    "$persona_apellidoPaterno TEXT NOT NULL, " +
                    "$persona_apellidoMaterno TEXT NOT NULL, " +
                    "$persona_rut TEXT NOT NULL, " +
                    "$persona_numeroSerie TEXT NOT NULL, " +
                    "$persona_genero INTEGER NOT NULL, " +
                    "$persona_region INTEGER NOT NULL, " +
                    "$persona_comuna INTEGER NOT NULL, " +
                    "$persona_direccion TEXT NOT NULL, " +
                    "$persona_claveUnica TEXT NOT NULL, " +
                    "$persona_correo TEXT NOT NULL )" +
            "CREATE TABLE $tabla_direcciones (" +
                    "$direccion_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$direccion_nombreLugar TEXT NOT NULL, " +
                    "$direccion_tipo INTEGER NOT NULL, " +
                    "$direccion_region TEXT NOT NULL, " +
                    "$direccion_comuna TEXT NOT NULL, " +
                    "$direccion_direccion TEXT NOT NULL )"

        p0!!.execSQL(creationQuery);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val deletionQuery: String =
            "DROP TABLE IF EXISTS $tabla_personas " +
            "DROP TABLE IF EXISTS $tabla_direcciones ";

        db!!.execSQL(deletionQuery);
    }

    fun insertPersona(p: Persona): Long
    {
        val content = ContentValues();
        content.put(persona_nombre, p.nombre);
        content.put(persona_segundoNombre, p.segundoNombre);
        content.put(persona_apellidoPaterno, p.apellidoPaterno);
        content.put(persona_apellidoMaterno, p.apellidoMaterno);
        content.put(persona_rut, p.rut);
        content.put(persona_numeroSerie, p.numeroSerie);
        content.put(persona_genero, p.genero);
        content.put(persona_region, p.region);
        content.put(persona_comuna, p.comuna);
        content.put(persona_direccion, p.direccion);
        content.put(persona_claveUnica, p.claveUnica);
        content.put(persona_correo, p.correo);

        return this.writableDatabase.insert(tabla_personas, null, content);
    }

    fun selectPersonas(): List<Persona>
    {
        val list: ArrayList<Persona> = ArrayList();
        val query = "SELECT * FROM $tabla_personas";
        val cursor: Cursor? = this.readableDatabase.rawQuery(query, null);

        if (cursor!!.moveToFirst())
        {
            do
            {
                list.add(
                    Persona(
                    cursor.getColumnIndex(persona_id),
                    cursor.getString(cursor.getColumnIndex(persona_nombre)),
                    cursor.getString(cursor.getColumnIndex(persona_segundoNombre)),
                    cursor.getString(cursor.getColumnIndex(persona_apellidoPaterno)),
                    cursor.getString(cursor.getColumnIndex(persona_apellidoMaterno)),
                    cursor.getString(cursor.getColumnIndex(persona_rut)),
                    cursor.getString(cursor.getColumnIndex(persona_numeroSerie)),
                    cursor.getInt(cursor.getColumnIndex(persona_genero)),
                    cursor.getInt(cursor.getColumnIndex(persona_region)),
                    cursor.getInt(cursor.getColumnIndex(persona_comuna)),
                    cursor.getString(cursor.getColumnIndex(persona_direccion)),
                    cursor.getString(cursor.getColumnIndex(persona_claveUnica)),
                    cursor.getString(cursor.getColumnIndex(persona_correo))
                )
                );
            } while (cursor.moveToNext())
        }

        cursor.close();
        return list;
    }

    fun insertDireccion(d: Direccion): Long
    {
        val content = ContentValues();

        content.put(direccion_nombreLugar, d.nombreLugar);
        content.put(direccion_tipo, d.tipo);
        content.put(direccion_region, d.region);
        content.put(direccion_comuna, d.comuna);
        content.put(direccion_direccion, d.direccion);

        return writableDatabase.insert(tabla_direcciones, null, content);
    }

    fun selectDirecciones(): List<Direccion>
    {
        val list: ArrayList<Direccion> = ArrayList();
        val query = "SELECT * FROM $tabla_direcciones";
        val cursor: Cursor? = readableDatabase.rawQuery(query, null);

        if (cursor!!.moveToFirst())
        {
            do
            {
              list.add(
                  Direccion(
                  cursor.getColumnIndex(direccion_id),
                  cursor.getString(cursor.getColumnIndex(direccion_nombreLugar)),
                  cursor.getInt(cursor.getColumnIndex(direccion_tipo)),
                  cursor.getInt(cursor.getColumnIndex(direccion_region)),
                  cursor.getInt(cursor.getColumnIndex(direccion_comuna)),
                  cursor.getString(cursor.getColumnIndex(direccion_direccion))
              )
              );
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

}