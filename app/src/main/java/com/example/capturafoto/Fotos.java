package com.example.capturafoto;



public class Fotos {

    private Integer id;
    private String nombre;

    private byte[] foto;



    public Fotos(Integer id, String nombre, byte foto[]){
        this.id=id;
        this.nombre=nombre;

        this.foto=foto;

    }

    public Fotos(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}