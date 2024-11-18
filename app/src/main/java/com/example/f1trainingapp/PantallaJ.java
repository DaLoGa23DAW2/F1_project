package com.example.f1trainingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class PantallaJ extends AppCompatActivity {

    private ImageView myImage;
    private int nivelActual;
    public int puntuacion;
    public int puntuacion_total = 10;
    public int puntuacion_parcial = 5;
    private int[] respuestasCorrectas;
    private boolean esPrimerIntento = true; // Variable de control para el primer intento
    private long tiempoInicio; // Tiempo al inicio del juego


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_jugar2);

        // Registrar el tiempo de inicio solo en el primer nivel
        if (nivelActual == 0) {
            tiempoInicio = System.currentTimeMillis();
        }

        // Resto del código
        respuestasCorrectas = new int[]{R.id.img2, R.id.img3, R.id.img2, R.id.img1, R.id.img4, R.id.img2, R.id.img1, R.id.img4, R.id.img4, R.id.img2};
        nivelActual = getIntent().getIntExtra("nivelActual", 0);
        puntuacion = getIntent().getIntExtra("puntuacion", 0);
        configurarNivel();

        ImageButton b1 = findViewById(R.id.img1);
        ImageButton b2 = findViewById(R.id.img2);
        ImageButton b3 = findViewById(R.id.img3);
        ImageButton b4 = findViewById(R.id.img4);
        myImage = findViewById(R.id.myImage);

        int finalPuntuacion = puntuacion;
        View.OnClickListener listener = v -> {
            if (v.getId() == respuestasCorrectas[nivelActual]) {
                sumarPuntuacion();
                mostrarDialogoCorrecto();
            } else {
                Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show();
                esPrimerIntento = false; // Si falla, no es el primer intento
            }
            Log.v("Puntuacion", "Puntuacion actual: " + finalPuntuacion);
            Log.v("Puntuacion", "lvl: " + nivelActual);
        };

        b1.setOnClickListener(listener);
        b2.setOnClickListener(listener);
        b3.setOnClickListener(listener);
        b4.setOnClickListener(listener);

        new Handler().postDelayed(() -> myImage.setVisibility(View.INVISIBLE), 500);
    }

    private void sumarPuntuacion() {
        if (esPrimerIntento) {
            puntuacion += puntuacion_total; // Sumar 10 puntos
        } else {
            puntuacion += puntuacion_parcial; // Sumar 5 puntos
        }
        esPrimerIntento = true; // Reinicia para el siguiente nivel
    }

    private void configurarNivel() {
        // Configura contenido específico del nivel
    }

    private void mostrarDialogoCorrecto() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialoge_with_image, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            if (nivelActual >= 9) { // Último nivel
                long tiempoFin = System.currentTimeMillis();
                long tiempoTotal = tiempoFin - tiempoInicio;

                // Obtener la fecha actual
                String fechaActual = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());

                // Pasar los datos a PantallaJD2
                Intent intent = new Intent(PantallaJ.this, PantallaJD2.class);
                intent.putExtra("puntuacion", puntuacion);
                intent.putExtra("tiempo", tiempoTotal);
                intent.putExtra("fecha", fechaActual);
                startActivity(intent);
            } else {
                // Continuar con el siguiente nivel
                Intent intent = new Intent(PantallaJ.this, PantallaJ.class);
                intent.putExtra("nivelActual", nivelActual + 1);
                intent.putExtra("puntuacion", puntuacion);
                startActivity(intent);
            }
            dialog.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}

