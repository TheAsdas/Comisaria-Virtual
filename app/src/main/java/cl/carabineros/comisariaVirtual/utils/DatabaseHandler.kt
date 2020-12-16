package cl.carabineros.comisariaVirtual.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import cl.carabineros.comisariaVirtual.model.Address
import cl.carabineros.comisariaVirtual.model.Person

class DatabaseHandler
    (context: Context)
    :SQLiteOpenHelper(context, dbName, null, dbVersion)
{
    companion object DatabaseData
    {
        private var dbName = "dbCarabineros";
        private var dbVersion = 5;

        private var tabla1 = "personas";
        private var tabla1_1 = "id";
        private var tabla1_2 = "nombre";
        private var tabla1_3 = "segundo_nombre";
        private var tabla1_4 = "apellido_paterno";
        private var tabla1_5 = "apellido_materno";
        private var tabla1_6 = "rut";
        private var tabla1_7 = "numero_serie";
        private var tabla1_8 = "genero";
        private var tabla1_9 = "region";
        private var tabla1_10 = "comuna";
        private var tabla1_11 = "direccion";
        private var tabla1_12 = "email";

        /* Tabla de direcciones */
        private var tabla2 = "direcciones";
        private var tabla2_1 = "id";
        private var tabla2_2 = "nombre_lugar";
        private var tabla2_3 = "direccion_tipo";
        private var tabla2_4 = "region";
        private var tabla2_5 = "comuna";
        private var tabla2_6 = "direccion";


    }

    override fun onCreate(p0: SQLiteDatabase?)
    {
        println("Creando base de datos...");

        val creationQuery: String =
            "CREATE TABLE $tabla1_1 (" +
                    "$tabla1 INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$tabla1_2 TEXT, " +
                    "$tabla1_3 TEXT, " +
                    "$tabla1_4 TEXT, " +
                    "$tabla1_5 TEXT, " +
                    "$tabla1_6 TEXT, " +
                    "$tabla1_7 TEXT, " +
                    "$tabla1_8 INTEGER, " +
                    "$tabla1_9 INTEGER, " +
                    "$tabla1_10 INTEGER, " +
                    "$tabla1_11 TEXT, " +
                    "$tabla1_12 TEXT); " +
            "CREATE TABLE $tabla2 (" +
                    "$tabla2_1 INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$tabla2_2 TEXT, " +
                    "$tabla2_3 INTEGER, " +
                    "$tabla2_4 TEXT, " +
                    "$tabla2_5 TEXT, " +
                    "$tabla2_6 TEXT); "

        p0!!.execSQL(creationQuery);

        println("Base de datos creada.")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        println("Actualizando base de datos...");

        val deletionQuery: String =
            "DROP TABLE IF EXISTS $tabla1_1 ;" +
            "DROP TABLE IF EXISTS $tabla2 ;";

        db!!.execSQL(deletionQuery);

        onCreate(db);
    }

    fun insertPersona(p: Person): Long
    {
        val content = ContentValues();
        content.put(tabla1_2, p.nombre);
        content.put(tabla1_3, p.segundoNombre);
        content.put(tabla1_4, p.apellidoPaterno);
        content.put(tabla1_5, p.apellidoMaterno);
        content.put(tabla1_6, p.rut);
        content.put(tabla1_7, p.numeroSerie);
        content.put(tabla1_8, p.genero);
        content.put(tabla1_9, p.region);
        content.put(tabla1_10, p.comuna);
        content.put(tabla1_11, p.direccion);
        content.put(tabla1_12, p.correo);

        return this.writableDatabase.insert(tabla1, null, content);
    }

    fun selectPersonas(): ArrayList<Person>
    {
        val list: ArrayList<Person> = ArrayList();
        val query = "SELECT * FROM $tabla1";
        val cursor: Cursor? = this.readableDatabase.rawQuery(query, null);

        if (cursor!!.moveToFirst())
        {
            do
            {
                list.add(
                    Person(
                        cursor.getInt(cursor.getColumnIndex(tabla1_1)),
                        cursor.getString(cursor.getColumnIndex(tabla1_2)),
                        cursor.getString(cursor.getColumnIndex(tabla1_3)),
                        cursor.getString(cursor.getColumnIndex(tabla1_4)),
                        cursor.getString(cursor.getColumnIndex(tabla1_5)),
                        cursor.getString(cursor.getColumnIndex(tabla1_6)),
                        cursor.getString(cursor.getColumnIndex(tabla1_7)),
                        cursor.getInt(cursor.getColumnIndex(tabla1_8)),
                        cursor.getInt(cursor.getColumnIndex(tabla1_9)),
                        cursor.getInt(cursor.getColumnIndex(tabla1_10)),
                        cursor.getString(cursor.getColumnIndex(tabla1_11)),
                        cursor.getString(cursor.getColumnIndex(tabla1_12))
                    )
                );
            } while (cursor.moveToNext())
        }

        cursor.close();
        return list;
    }

    fun deletePersona(p: Person)
    {
        this.writableDatabase.delete(
            tabla1,
            "$tabla1_1 = ${p.id}",
            null
        );
    }


    fun insertDireccion(d: Address): Long
    {
        val content = ContentValues();

        content.put(tabla2_2, d.nombreLugar);
        content.put(tabla2_3, d.tipo);
        content.put(tabla2_4, d.region);
        content.put(tabla2_5, d.comuna);
        content.put(tabla2_6, d.direccion);

        return writableDatabase.insert(tabla2, null, content);
    }

    fun selectDirecciones(): List<Address>
    {
        val list: ArrayList<Address> = ArrayList();
        val query = "SELECT * FROM $tabla2";
        val cursor: Cursor? = readableDatabase.rawQuery(query, null);

        if (cursor!!.moveToFirst())
        {
            do
            {
                list.add(
                    Address(
                        cursor.getInt(cursor.getColumnIndex(tabla2_1)),
                        cursor.getString(cursor.getColumnIndex(tabla2_2)),
                        cursor.getInt(cursor.getColumnIndex(tabla2_3)),
                        cursor.getInt(cursor.getColumnIndex(tabla2_4)),
                        cursor.getInt(cursor.getColumnIndex(tabla2_5)),
                        cursor.getString(cursor.getColumnIndex(tabla2_6))
                    )
                );
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

}