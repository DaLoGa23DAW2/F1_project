package com.example.f1trainingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "JuegoDB";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas
    private static final String TABLE_PARTIDAS = "Partidas";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_PUNTUACION = "puntuacion";
    private static final String COLUMN_TIEMPO = "tiempo";
    private static final String COLUMN_FECHA = "fecha";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla
        String CREATE_PARTIDAS_TABLE = "CREATE TABLE " + TABLE_PARTIDAS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOMBRE + " TEXT, "
                + COLUMN_PUNTUACION + " INTEGER, "
                + COLUMN_TIEMPO + " INTEGER, "
                + COLUMN_FECHA + " TEXT" + ")";
        db.execSQL(CREATE_PARTIDAS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Elimina la tabla si ya existe y la recrea
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTIDAS);
        onCreate(db);
    }

    public void insertarPartida(String nombre, int puntuacion, long tiempo, String fecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("puntuacion", puntuacion);
        values.put("tiempo", tiempo);
        values.put("fecha", fecha);

        long resultado = db.insert("partidas", null, values);
        if (resultado == -1) {
            Log.e("SQLite", "Error al insertar datos");
        } else {
            Log.v("SQLite", "Datos insertados correctamente");
        }
    }

    // Método para consultar todas las partidas
    public Cursor consultarPartidas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PARTIDAS, null);
    }
}
