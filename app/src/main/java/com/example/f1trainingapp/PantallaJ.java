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
    private int puntuacion;
    private boolean esPrimerIntento = true;
    private long tiempoInicio;

    // Declarar arrays globales
    private int[][] imagenesPorNivel = {
            {R.drawable.ferrari, R.drawable.haas, R.drawable.mercedes, R.drawable.aston_martin, R.id.img2, R.drawable.haas_f1},
            {R.drawable.mclaren, R.drawable.alfa_romeo, R.drawable.alpine, R.drawable.williams, R.id.img3, R.drawable.alpine_f1},
            {R.drawable.haas, R.drawable.ferrari, R.drawable.aston_martin, R.drawable.redbull, R.id.img2, R.drawable.ferrari_f1},
            {R.drawable.alpha_tauri, R.drawable.alfa_romeo, R.drawable.mercedes, R.drawable.williams, R.id.img1, R.drawable.alpha_tauri_f1},
            {R.drawable.mercedes, R.drawable.aston_martin, R.drawable.alpine, R.drawable.mclaren, R.id.img4, R.drawable.mclaren_f1},
            {R.drawable.ferrari, R.drawable.mercedes, R.drawable.mclaren, R.drawable.aston_martin, R.id.img2, R.drawable.mercedes_f1},
            {R.drawable.mclaren, R.drawable.alfa_romeo, R.drawable.aston_martin, R.drawable.williams, R.id.img3, R.drawable.aston_martin_f1},
            {R.drawable.haas, R.drawable.alfa_romeo, R.drawable.aston_martin, R.drawable.redbull, R.id.img2, R.drawable.alfa_romeo_f1},
            {R.drawable.williams, R.drawable.alfa_romeo, R.drawable.mercedes, R.drawable.haas, R.id.img1, R.drawable.williams_f1},
            {R.drawable.mercedes, R.drawable.aston_martin, R.drawable.alpine, R.drawable.redbull, R.id.img4, R.drawable.redbull_f1}

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_jugar2);

        nivelActual = getIntent().getIntExtra("nivelActual", 0);
        puntuacion = getIntent().getIntExtra("puntuacion", 0);

        ImageButton b1 = findViewById(R.id.img1);
        ImageButton b2 = findViewById(R.id.img2);
        ImageButton b3 = findViewById(R.id.img3);
        ImageButton b4 = findViewById(R.id.img4);
        myImage = findViewById(R.id.myImage);

        configurarNivel();

        if (nivelActual == 0) {
            tiempoInicio = System.currentTimeMillis();
        }

        View.OnClickListener listener = v -> {
            int respuestaCorrectaId = imagenesPorNivel[nivelActual][4];
            if (v.getId() == respuestaCorrectaId) {
                sumarPuntuacion();
                mostrarDialogoCorrecto();
            } else {
                Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show();
                esPrimerIntento = false;
            }
            Log.v("Puntuacion", "Puntuación actual: " + puntuacion);
            Log.v("Nivel", "Nivel actual: " + nivelActual);
        };

        b1.setOnClickListener(listener);
        b2.setOnClickListener(listener);
        b3.setOnClickListener(listener);
        b4.setOnClickListener(listener);

    }

    private void configurarNivel() {
        if (nivelActual >= 0 && nivelActual < imagenesPorNivel.length) {
            ImageButton b1 = findViewById(R.id.img1);
            ImageButton b2 = findViewById(R.id.img2);
            ImageButton b3 = findViewById(R.id.img3);
            ImageButton b4 = findViewById(R.id.img4);
            myImage = findViewById(R.id.myImage);

            // Configurar los botones
            b1.setImageResource(imagenesPorNivel[nivelActual][0]);
            b2.setImageResource(imagenesPorNivel[nivelActual][1]);
            b3.setImageResource(imagenesPorNivel[nivelActual][2]);
            b4.setImageResource(imagenesPorNivel[nivelActual][3]);

            // Configurar la imagen del ImageView
            myImage.setImageResource(imagenesPorNivel[nivelActual][5]);

            // Asegurarse de que el ImageView sea visible
            myImage.setVisibility(View.VISIBLE);

            // Ocultar la imagen después de 2.5 segundos (opcional)
            new Handler().postDelayed(() -> myImage.setVisibility(View.INVISIBLE), 2500);
        } else {
            Log.e("ConfigurarNivel", "Nivel fuera de rango: " + nivelActual);
        }
    }


    private void sumarPuntuacion() {
        puntuacion += esPrimerIntento ? 10 : 5;
        esPrimerIntento = true;
    }

    private void mostrarDialogoCorrecto() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialoge_with_image, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            if (nivelActual >= imagenesPorNivel.length - 1) {
                long tiempoFin = System.currentTimeMillis();
                long tiempoTotal = tiempoFin - tiempoInicio;

                String fechaActual = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());

                Intent intent = new Intent(PantallaJ.this, PantallaJD2.class);
                intent.putExtra("puntuacion", puntuacion);
                intent.putExtra("tiempo", tiempoTotal);
                intent.putExtra("fecha", fechaActual);
                startActivity(intent);
            } else {
                nivelActual++;
                configurarNivel();
            }
            dialog.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
