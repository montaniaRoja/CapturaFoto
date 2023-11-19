package com.example.capturafoto;


public class Transacciones {
    // Nombre de la base de datos
    public static final String nameDB = "fotosdb";

    //Tablas de la base de datos
    public static final String Tabla1 = "fotos";


    // Campos de la tabla
    public static final String id = "id";

    public static final String foto = "foto";
    public static final String descripcion = "descripcion";

    public static final String DeleteContact = "DELETE FROM " + Transacciones.Tabla1 + " WHERE " + Transacciones.id + " = ?";


    // Consultas de Base de datos
    //ddl


    public static final String CreateTableFotos = "CREATE TABLE fotos " +
            "( id INTEGER PRIMARY KEY AUTOINCREMENT, descripcion VARCHAR(100),foto BLOB)";


    public static final String DropTableFotos = "DROP TABLE IF EXISTS fotos";

    public static final String SelectTableFotos = "SELECT * FROM " + Transacciones.Tabla1;

}


