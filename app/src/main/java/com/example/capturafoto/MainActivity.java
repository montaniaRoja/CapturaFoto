package com.example.capturafoto;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText txtDescripcion;
    Button btnFoto, btnGuardar, btnVer;
    SQLiteConexion conexion;

    ImageView imageView;

    String pathPhoto;

    byte[] imagenEnBytes;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDescripcion=(EditText)findViewById(R.id.txtDescripcion);

        btnFoto=(Button) findViewById(R.id.btnFoto);
        btnGuardar=(Button) findViewById(R.id.btnGuardar);
        btnVer=(Button) findViewById(R.id.btnVer);
        imageView=findViewById(R.id.imageView);


        conexion =  new SQLiteConexion(this, Transacciones.nameDB, null, 1);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
            }
        });
        View.OnClickListener butonclick = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Class<?> actividad = null;
                //if (view.getId() == R.id.btnCountry) {
                //  actividad = CountryAdd.class;
                //}
                if (view.getId() == R.id.btnVer) {
                    actividad = VisualizarFoto.class;
                }

                if(actividad != null)
                {
                    NoveActivity(actividad);
                }

            }

        };
        btnVer.setOnClickListener(e->verLista());

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {AddContact();

            }
        });
    }

    private void verLista() {
        iniciarNuevoActivity();
    }

    private void iniciarNuevoActivity() {
        Intent intent=new Intent(MainActivity.this, ListaFotos.class);
        startActivity(intent);

    }

    private void permisos() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(),android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA},101);

        }
        else{
            tomarFoto();
        }
    }
    public void onRequestPermissionsResult(int requestCode,String[]permissions,int[]grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if(requestCode==101){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                tomarFoto();
            }
            else{
                Toast.makeText(getApplicationContext(),"Persmiso denegado",Toast.LENGTH_LONG).show();
            }
        }
    }
    private void tomarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 102);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 102 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");


            imageView.setImageBitmap(imageBitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagenEnBytes = stream.toByteArray();

            saveImageToFile(imagenEnBytes);
        }


    }

    private void saveImageToFile(byte[] imageData) {
        try {
            String fileName = "image.png"; // Nombre del archivo de imagen
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(imageData);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void AddContact() {
        String descripcion = txtDescripcion.getText().toString();
        byte[] imagenEnBytes = readImageFromFile();


            try {

                SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.nameDB, null, 1);
                SQLiteDatabase db = conexion.getWritableDatabase();



                ContentValues valores = new ContentValues();
                valores.put(Transacciones.descripcion, descripcion);
                valores.put(Transacciones.foto, imagenEnBytes);

                Long Result = db.insert(Transacciones.Tabla1, Transacciones.id, valores);

                Toast.makeText(this, getString(R.string.Respuesta), Toast.LENGTH_SHORT).show();
                db.close();
                imageView.setImageBitmap(null);
                txtDescripcion.setText("");


            } catch (Exception exception) {
                Toast.makeText(this, getString(R.string.ErrorResp), Toast.LENGTH_SHORT).show();
            }
        }


    private byte[] readImageFromFile() {
        try {
            String fileName = "image.png"; // Nombre del archivo de imagen
            FileInputStream fis = openFileInput(fileName);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = fis.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }






    private void NoveActivity(Class<?> actividad)
    {
        Intent intent = new Intent(getApplicationContext(),actividad);
        startActivity(intent);
    }

}