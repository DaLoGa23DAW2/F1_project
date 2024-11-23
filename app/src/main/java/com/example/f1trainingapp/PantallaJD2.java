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

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PantallaJD2 extends AppCompatActivity {

    private TextView textViewDatos; // Declarar el TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_jugar);

        // Inicializar el TextView
        textViewDatos = findViewById(R.id.textViewDatos);

        // Llama al método para obtener datos del servidor
        obtenerDatosDelServidor();

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

    private void obtenerDatosDelServidor() {
        String url = "http://10.0.2.2:8000/?nom=Juan"; // Cambiar la IP si usas un dispositivo físico

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> textViewDatos.setText("Error al obtener datos del servidor."));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    runOnUiThread(() -> textViewDatos.setText(responseData));
                } else {
                    runOnUiThread(() -> textViewDatos.setText("Error: " + response.code()));
                }
            }
        });
    }
}

