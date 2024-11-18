package com.example.f1trainingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PantallaM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.menu_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // FUNCIONALIDADES DE LOS BOTONES

        // Funcionalidad del boton jugar
        Button botoJugar = findViewById(R.id.botoJugar);

        botoJugar.setOnClickListener(view -> {
            Intent intent = new Intent(this, PantallaJ.class);
            startActivity(intent);
        });

        // Funcionalidad del boton jugar
        Button botoConf = findViewById(R.id.botoConf);

        botoConf.setOnClickListener(view -> {
            Intent intent = new Intent(this, PantallaC.class);
            startActivity(intent);
        });

        // Funcionalidad del boton jugar
        Button botoAyuda = findViewById(R.id.botoAyuda);

        botoAyuda.setOnClickListener(view -> {
            Intent intent = new Intent(this, PantallaA.class);
            startActivity(intent);
        });

        // Funcionalidad del botón salir
        Button botoSalir = findViewById(R.id.botoSalir);

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
    }
}