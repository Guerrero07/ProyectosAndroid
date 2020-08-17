package com.djguerrero.todo_app.Entidades;

import java.io.Serializable;

public class Tareas implements Serializable {

    private String Id;
    private String Categoria;
    private String Descripcion;
    private String Fecha;
    private String Hora;
    private String idCat;
    private String Estado;

    public Tareas(String Id, String Categoria, String Descripcion, String Fecha, String Hora,String idCat, String Estado){
        this.Id=Id;
        this.Categoria= Categoria;
        this.Descripcion=Descripcion;
        this.Fecha=Fecha;
        this.Hora=Hora;
        this.idCat=idCat;
        this.Estado=Estado;

    }
    public Tareas(){
    }
    public String  getId(){ return Id; }

    public void setId(String Id){ this.Id= Id; }

    public String  getCategoria(){ return Categoria; }

    public void setCategoria(String Categoria){ this.Categoria= Categoria; }

    public String getDescripcion(){ return Descripcion; }

    public void setDescripcion(String Descripcion){ this.Descripcion= Descripcion; }

    public String getFecha(){ return Fecha; }

    public void setFecha(String Fecha){ this.Fecha= Fecha; }

    public String getHora(){ return Hora; }

    public void setHora(String Hora){ this.Hora= Hora; }

    public String getidCat(){ return idCat; }

    public void setidCat(String idCat){ this.idCat= idCat; }

    public String getEstado(){ return Estado; }

    public void setEstado(String Estado){ this.Estado= Estado; }
}
