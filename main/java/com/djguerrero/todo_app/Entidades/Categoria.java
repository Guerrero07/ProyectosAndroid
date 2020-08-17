package com.djguerrero.todo_app.Entidades;

import java.io.Serializable;

public class Categoria implements Serializable {
    private int Idc;
    private String Descrip;

    public Categoria(int Idc, String Descrip) {
        this.Idc = Idc;
        this.Descrip = Descrip;

    }

    public Categoria() {
    }

    public int getIdc() {
        return Idc;
    }

    public void setIdc(int Idc) {
        this.Idc = Idc;
    }

    public String getDescrip() {
        return Descrip;
    }

    public void setDescrip(String Descrip) {
        this.Descrip = Descrip;
    }

}