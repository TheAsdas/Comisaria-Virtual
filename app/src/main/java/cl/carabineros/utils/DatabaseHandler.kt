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
        private var dbVersion = 5;

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

    override fun onCreate(p0: SQLiteDatabase?)
    {
        println("Creando base de datos...");

        val creationQuery: String =
            "CREATE TABLE $tabla_personas (" +
                    "$persona_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$persona_nombre TEXT, " +
                    "$persona_segundoNombre TEXT, " +
                    "$persona_apellidoPaterno TEXT, " +
                    "$persona_apellidoMaterno TEXT, " +
                    "$persona_rut TEXT, " +
                    "$persona_numeroSerie TEXT, " +
                    "$persona_genero INTEGER, " +
                    "$persona_region INTEGER, " +
                    "$persona_comuna INTEGER, " +
                    "$persona_direccion TEXT, " +
                    "$persona_correo TEXT); " +
            "CREATE TABLE $tabla_direcciones (" +
                    "$direccion_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$direccion_nombreLugar TEXT, " +
                    "$direccion_tipo INTEGER, " +
                    "$direccion_region TEXT, " +
                    "$direccion_comuna TEXT, " +
                    "$direccion_direccion TEXT); "

        p0!!.execSQL(creationQuery);

        println("Base de datos creada.")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        println("Actualizando base de datos...");

        val deletionQuery: String =
            "DROP TABLE IF EXISTS $tabla_personas ;" +
            "DROP TABLE IF EXISTS $tabla_direcciones ;";

        db!!.execSQL(deletionQuery);

        onCreate(db);
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
        content.put(persona_correo, p.correo);

        return this.writableDatabase.insert(tabla_personas, null, content);
    }

    fun insertPersona(p: Persona, materno: String): Long
    {
        val content = ContentValues();
        content.put(persona_nombre, p.nombre);
        content.put(persona_segundoNombre, p.segundoNombre);
        content.put(persona_apellidoPaterno, p.apellidoPaterno);
        content.put(persona_apellidoMaterno, materno);
        content.put(persona_rut, p.rut);
        content.put(persona_numeroSerie, p.numeroSerie);
        content.put(persona_genero, p.genero);
        content.put(persona_region, p.region);
        content.put(persona_comuna, p.comuna);
        content.put(persona_direccion, p.direccion);
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
                        cursor.getInt(cursor.getColumnIndex(persona_id)),
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
                  cursor.getInt(cursor.getColumnIndex(direccion_id)),
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