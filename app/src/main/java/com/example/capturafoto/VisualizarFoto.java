package com.example.capturafoto;


import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.widget.ImageView;

public class VisualizarFoto extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_foto);


        // Recupera los bytes de la firma del Intent
        byte[] firmaBytes = getIntent().getByteArrayExtra("firmaBytes");


        ImageView imageView = findViewById(R.id.imageView2);
        Bitmap firmaBitmap = BitmapFactory.decodeByteArray(firmaBytes, 0, firmaBytes.length);
        imageView.setImageBitmap(firmaBitmap);
    }
}