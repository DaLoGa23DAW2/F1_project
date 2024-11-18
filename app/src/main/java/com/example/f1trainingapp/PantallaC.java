package com.example.f1trainingapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

public class PantallaC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_conf);

        // Botón para volver
        Button botonVolver = findViewById(R.id.botonVolver);
        botonVolver.setOnClickListener(v -> finish());

        // Referencia al TableLayout
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // Crear instancia de SQLiteHelper y consultar datos
        SQLiteHelper dbHelper = new SQLiteHelper(this);
        Cursor cursor = dbHelper.consultarPartidas();

        // Añadir encabezados a la tabla
        TableRow headerRow = new TableRow(this);
        headerRow.setBackgroundColor(Color.BLACK);

        addCellToRow(headerRow, "ID", true);
        addCellToRow(headerRow, "Nombre", true);
        addCellToRow(headerRow, "Puntuación", true);
        addCellToRow(headerRow, "Tiempo", true);
        addCellToRow(headerRow, "Fecha", true);

        tableLayout.addView(headerRow);

        // Añadir filas con los datos de la BD
        if (cursor != null && cursor.moveToFirst()) {
            do {
                TableRow dataRow = new TableRow(this);
                dataRow.setBackgroundColor(Color.LTGRAY);

                addCellToRow(dataRow, cursor.getString(cursor.getColumnIndexOrThrow("id")), false);
                addCellToRow(dataRow, cursor.getString(cursor.getColumnIndexOrThrow("nombre")), false);
                addCellToRow(dataRow, cursor.getString(cursor.getColumnIndexOrThrow("puntuacion")), false);
                addCellToRow(dataRow, cursor.getString(cursor.getColumnIndexOrThrow("tiempo")), false);
                addCellToRow(dataRow, cursor.getString(cursor.getColumnIndexOrThrow("fecha")), false);

                tableLayout.addView(dataRow);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    // Método para agregar una celda a una fila
    private void addCellToRow(TableRow row, String text, boolean isHeader) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(16);
        textView.setPadding(8, 8, 8, 8);

        if (isHeader) {
            textView.setTextAppearance(android.R.style.TextAppearance_Material_Widget_ActionBar_Title);
            textView.setTextColor(Color.WHITE);
        }

        row.addView(textView);
    }
}

