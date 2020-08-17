package com.djguerrero.todo_app.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import com.djguerrero.todo_app.Servicios.BasedeDatos;
import com.djguerrero.todo_app.Entidades.Categoria;
import com.djguerrero.todo_app.Entidades.Tareas;
import com.djguerrero.todo_app.Servicios.ListaTareaAdapter;
import com.djguerrero.todo_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    Spinner spinner1;
    RecyclerView lista;
    ImageButton Eliminar;
    FloatingActionButton Agregar;
    ArrayList<Tareas> listaTarea;
    ArrayList<Categoria> categorias;
    ArrayList<String> listacategoria;
    BasedeDatos basedeDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner1 = findViewById(R.id.spinner1);
        lista = findViewById(R.id.rvListaC);
        Agregar = findViewById(R.id.btnAgregar);
        Eliminar = findViewById(R.id.btnEliminar);

        setTitle("AGENDA MOVIL");

        //recyclerview
        listaTarea = new ArrayList<>();
        lista.setLayoutManager(new LinearLayoutManager(this));
        ListaTareaAdapter adapter1 = new ListaTareaAdapter(listaTarea);
        lista.setAdapter(adapter1);

        basedeDatos = new BasedeDatos(getApplicationContext());

        consultarLista();
        consularTodos();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, R.layout.spinner_item_color, listacategoria);
        spinner1.setAdapter(adapter);

    }
    private void VerCategoria(int i, String c) {
        Intent intent = new Intent(this, vera_category.class);
        intent.putExtra("id", i);
        intent.putExtra("cat", c);
        startActivity(intent);
        finish();
    }

    private void consultarLista() {

        SQLiteDatabase bd = basedeDatos.getReadableDatabase();
        Categoria categoria = null;

        categorias = new ArrayList<Categoria>();
        Cursor fila = bd.rawQuery("select * from CATEGORIA", null);
        while (fila.moveToNext()) {
            categoria = new Categoria();
            categoria.setIdc(fila.getInt(0));
            categoria.setDescrip(fila.getString(1));
            categorias.add(categoria);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listacategoria = new ArrayList<String>();
        listacategoria.add("ALL CATEGORY");
        for (int i = 0; i < categorias.size(); i++) {
            listacategoria.add(categorias.get(i).getDescrip());
        }
    }

    private void consularTodos() {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();
        Tareas tareas = null;

        Cursor cursor = db.rawQuery("SELECT * FROM AGENDA ORDER BY cod_cat", null);

        while (cursor.moveToNext()) {
            tareas = new Tareas();
            tareas.setId(cursor.getString(0));
            tareas.setCategoria(cursor.getString(1));
            tareas.setDescripcion(cursor.getString(2));
            tareas.setFecha(cursor.getString(3));
            tareas.setHora(cursor.getString(4));
            tareas.setEstado(cursor.getString(5));
            listaTarea.add(tareas);
            db.close();
        }

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> lista, View view, int position, long id) {
                int i = spinner1.getSelectedItemPosition();
                String cat = spinner1.getSelectedItem().toString();
                if (i > 0) {
                    VerCategoria(i,cat);
                    }
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, second_activity.class);
                intent.putExtra("id","0");
                startActivity(intent);
                finish();
            }
        });
        Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("¿Desea Borrar Todos Los Datos?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SQLiteDatabase base = basedeDatos.getWritableDatabase();
                                base.delete("AGENDA",null, null);
                                base.close();
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.cancel();
                            }
                        });
                AlertDialog titulo = alert.create();
                titulo.setTitle("LIMPIAR");
                titulo.show();
            }
        });
    }
}
