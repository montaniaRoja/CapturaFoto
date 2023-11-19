package com.example.capturafoto;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;



public class ListaFotos extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listView;
    ArrayList<Fotos> listFirmas;

    ArrayList<String> arregloFirmas;

    int selectedPosition = ListView.INVALID_POSITION;

    private byte[] selectedFirma;

    private ArrayList<byte[]> arregloFirma;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_fotos);

        conexion = new SQLiteConexion(this, Transacciones.nameDB, null, 1);
        arregloFirmas = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        arregloFirma = new ArrayList<byte[]>();

        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, arregloFirmas);
        listView.setAdapter(adp);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedContact = arregloFirmas.get(i);
                selectedPosition = i;
                selectedFirma = listFirmas.get(i).getFoto();  // Obtener la firma del objeto Firmas
                showConfirmationDialog(selectedContact, selectedFirma);
            }
        });




        GetPersons();

    }

    private void showConfirmationDialog(String selectedContact, byte[] firmaFotos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ver Foto de " + selectedContact);
        builder.setMessage("¿Desea ver la foto ?");

        builder.setPositiveButton("Ver", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ListaFotos.this, VisualizarFoto.class);

                intent.putExtra("firmaBytes", firmaFotos);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }


    private void GetPersons() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Fotos foto = null;
        listFirmas = new ArrayList<Fotos>();

        Cursor cursor = db.rawQuery(Transacciones.SelectTableFotos, null);
        while (cursor.moveToNext()) {
            foto = new Fotos();
            foto.setId(cursor.getInt(0));
            foto.setNombre(cursor.getString(1));
            foto.setFoto(cursor.getBlob(2)); // Utiliza getBlob() en lugar de getString().getBytes()

            listFirmas.add(foto);
        }

        cursor.close();
        FillList();
    }


    private void FillList() {
        for (int i = 0; i < listFirmas.size(); i++) {
            arregloFirmas.add(listFirmas.get(i).getId() + "-" +
                    listFirmas.get(i).getNombre() + "**" );
        }
    }



}