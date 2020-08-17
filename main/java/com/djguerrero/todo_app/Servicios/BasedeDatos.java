package com.djguerrero.todo_app.Servicios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BasedeDatos extends SQLiteOpenHelper {
    static final String NOMBREBD = "ADMINISTRADOR";
    static final int VERSIONBD= 1;
    static final String CREAR_TABLA_AGENDA= "create table AGENDA " +
            "(id integer primary key, "+
            "categoria tex, "+
            "descripcion tex, " +
            "fecha tex, " +
            "hora tex, "+
            "estado integer, "+
            "cod_cat integer, "+
             "FOREIGN KEY(cod_cat) REFERENCES CATEGORIA (id_categoria))";

    static final String CREAR_TABLA_CATEGORIA= "create table CATEGORIA " +
            "(id_categoria integer primary key, " +
            "descrip tex)";


    public BasedeDatos(Context context){
        super(context, NOMBREBD, null, VERSIONBD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_AGENDA);
        db.execSQL(CREAR_TABLA_CATEGORIA);
        db.execSQL("INSERT INTO CATEGORIA VALUES (1, 'HOME')");
        db.execSQL("INSERT INTO CATEGORIA VALUES (2, 'FAMILY')");
        db.execSQL("INSERT INTO CATEGORIA VALUES (3, 'WORK')");
        db.execSQL("INSERT INTO CATEGORIA VALUES (4, 'UNIVERSITY')");
        db.execSQL("INSERT INTO CATEGORIA VALUES (5, 'OTHERS')");
      //  db.execSQL("INSERT INTO CATEGORIA VALUES (6, 'AMIGOS')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS AGENDA");
        db.execSQL("DROP TABLE IF EXISTS CATEGORIA");
        onCreate(db);
    }

}
