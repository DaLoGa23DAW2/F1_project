package com.example.f1trainingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PantallaP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_pausa);

        // Creamos el boton y lo asignamos al layout
        Button botoReanudar = findViewById(R.id.botonReanudar);

        botoReanudar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Creamos el boton y lo asignamos al layout
        Button botoAjustes = findViewById(R.id.botonConf);

        botoAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaP.this, PantallaC.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        // Creamos el botón y lo asignamos al layout
        Button botoSalir = findViewById(R.id.botonSalir);

        botoSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creamos un dialogo de confirmación
                AlertDialog dialog = new AlertDialog.Builder(PantallaP.this)
                        .setTitle("¿Estás seguro de que quieres salir?")
                        .setMessage("Se perderán todos los datos")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(PantallaP.this, PantallaM.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .create();

                dialog.show();

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.parseColor("#03BFB5"));

                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setTextColor(Color.parseColor("#004146"));
            }
        });
    }
}