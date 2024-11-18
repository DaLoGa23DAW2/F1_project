package com.example.f1trainingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PantallaF extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pantalla_final);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Creamos el botón y lo asignamos al layout
        Button botoAjustes = findViewById(R.id.botonReintentar);

        botoAjustes.setOnClickListener(v -> {
            Intent intent = new Intent(PantallaF.this, PantallaM.class);
            startActivity(intent);
            finish();
        });

        // Funcionalidad del botón salir
        Button botoSalir = findViewById(R.id.botonSalir);

        botoSalir.setOnClickListener(view -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("SALIR")
                    .setMessage("¿Estás seguro de que quieres cerrar la app?")
                    .setPositiveButton("Sí", (dialogInterface, which) -> {
                        finishAffinity();
                    })
                    .setNegativeButton("No", (dialogInterface, which) -> {
                        dialogInterface.dismiss();
                    })
                    .show();

            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#03BFB5"));

            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setTextColor(Color.parseColor("#004146"));
        });

        // Recibir los datos del Intent (nombre, puntuación, tiempo, fecha)
        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        int puntuacion = intent.getIntExtra("puntuacion", 0);
        long tiempo = intent.getLongExtra("tiempo", 0);
        String fecha = intent.getStringExtra("fecha");

        // Guardar los datos en la base de datos
        SQLiteHelper dbHelper = new SQLiteHelper(this);
        dbHelper.insertarPartida(nombre, puntuacion, tiempo, fecha);

        // Mostrar los datos guardados en el Log
        Log.v("SQLite", "Datos guardados correctamente: " +
                "Nombre=" + nombre +
                ", Puntuación=" + puntuacion +
                ", Tiempo=" + tiempo +
                ", Fecha=" + fecha);

        // Llamada al método para mostrar los datos de las partidas
        mostrarDatosPartidas();
    }

    // Método independiente para mostrar los datos de las partidas
    private void mostrarDatosPartidas() {
        SQLiteHelper dbHelper = new SQLiteHelper(this);
        Cursor cursor = dbHelper.consultarPartidas();

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // Limpiar la tabla antes de agregar nuevas filas
        tableLayout.removeAllViews();

        // Agregar la fila de encabezados
        TableRow headerRow = new TableRow(this);
        headerRow.addView(createTextView("Nombre"));
        headerRow.addView(createTextView("Puntuación"));
        headerRow.addView(createTextView("Tiempo"));
        headerRow.addView(createTextView("Fecha"));
        tableLayout.addView(headerRow);

        // Agregar las filas con los datos de las partidas
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                @SuppressLint("Range") int puntuacion = cursor.getInt(cursor.getColumnIndex("puntuacion"));
                @SuppressLint("Range") long tiempo = cursor.getLong(cursor.getColumnIndex("tiempo"));
                @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));

                TableRow dataRow = new TableRow(this);
                dataRow.addView(createTextView(nombre));
                dataRow.addView(createTextView(String.valueOf(puntuacion)));
                dataRow.addView(createTextView(String.valueOf(tiempo)));
                dataRow.addView(createTextView(fecha));
                tableLayout.addView(dataRow);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    private View createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(10, 10, 10, 10);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
