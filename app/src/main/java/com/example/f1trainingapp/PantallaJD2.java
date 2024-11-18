package com.example.f1trainingapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class PantallaJD2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_jugar);

        // Recibir datos del Intent
        Intent intent = getIntent();
        int puntuacion = intent.getIntExtra("puntuacion", 0);
        long tiempo = intent.getLongExtra("tiempo", 0);
        String fecha = intent.getStringExtra("fecha");

        // Configura el botón de pausa para ingresar el nombre
        Button botonPausa = findViewById(R.id.botonPausa);
        botonPausa.setOnClickListener(view -> mostrarDialogoConTabla(puntuacion, tiempo, fecha));
    }

    private void mostrarDialogoConTabla(int puntuacion, long tiempo, String fecha) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_puntuacion, null);
        EditText input = dialogView.findViewById(R.id.editTextPuntuacion);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("Aceptar", (dialogInterface, which) -> {
                    String nombre = input.getText().toString().trim();
                    if (nombre.isEmpty()) {
                        Toast.makeText(this, "Por favor, ingresa tu nombre", Toast.LENGTH_SHORT).show();
                    } else {
                        // Pasar todos los datos a PantallaF
                        Intent intent = new Intent(PantallaJD2.this, PantallaF.class);
                        intent.putExtra("nombre", nombre);
                        intent.putExtra("puntuacion", puntuacion);
                        intent.putExtra("tiempo", tiempo);
                        intent.putExtra("fecha", fecha);
                        startActivity(intent);
                    }
                })
                .create();

        dialog.show();
    }
}
