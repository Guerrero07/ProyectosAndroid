package com.djguerrero.todo_app.Activitys;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.djguerrero.todo_app.Entidades.Tareas;
import com.djguerrero.todo_app.R;
import com.djguerrero.todo_app.Servicios.BasedeDatos;
import com.djguerrero.todo_app.Servicios.ListaTareaAdapter;
import java.util.ArrayList;

public class vera_category extends AppCompatActivity {
    BasedeDatos basedeDatos;
    ArrayList<Tareas> listaTarea;
    RecyclerView lista;
    TextView categoria;
    Button volver;
    int id;
    String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_categoria);
        lista=findViewById(R.id.rvListaC);
        categoria=findViewById(R.id.tvcategoria);
        volver=findViewById(R.id.button);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("CATEGORIA");

       Bundle dato= getIntent().getExtras();
        id = dato.getInt("id");
        cat=dato.getString("cat");
        basedeDatos = new BasedeDatos(getApplicationContext());

        listaTarea = new ArrayList<>();
        lista.setLayoutManager(new LinearLayoutManager(this));
        ListaTareaAdapter adapter1 = new ListaTareaAdapter(listaTarea);
        lista.setAdapter(adapter1);

        if (id != 0){
            consularCategoria();
            categoria.setText(cat);
        }
    }
    public void mensaje(View view){
        Toast.makeText(this, "Categoria: "+cat, Toast.LENGTH_SHORT).show();
    }

    private void consularCategoria() {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();
        Tareas tareas = null;

        Cursor cursor = db.rawQuery("SELECT * FROM AGENDA WHERE cod_cat= "+id, null);

        while (cursor.moveToNext()) {

            tareas = new Tareas();
            tareas.setId(cursor.getString(0));
            tareas.setCategoria(cursor.getString(1));
            tareas.setDescripcion(cursor.getString(2));
            tareas.setFecha(cursor.getString(3));
            tareas.setHora(cursor.getString(4));
            tareas.setEstado(cursor.getString(5));

            listaTarea.add(tareas);
        }
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
