package com.djguerrero.todo_app.Activitys;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.djguerrero.todo_app.Servicios.BasedeDatos;
import com.djguerrero.todo_app.Entidades.Categoria;
import com.djguerrero.todo_app.R;

import java.util.ArrayList;
import java.util.Calendar;

public class second_activity extends AppCompatActivity {

    Button guardar, descartar;
    EditText descrip, fecha, hora;
    Spinner spinner2;
    ImageButton Bfecha, Bhora;
    CheckBox checkBox;
    Calendar calendar = Calendar.getInstance();
    int año, mes, dia, hr, min, id;

    ArrayList<Categoria> categorias;
    ArrayList<String> listacategoria;
    BasedeDatos basedeDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activity);
        guardar = findViewById(R.id.btnDone);
        descartar = findViewById(R.id.btnDiscard);
        descrip = findViewById(R.id.etDescripcion);
        spinner2 = findViewById(R.id.spinner2);
        Bfecha = findViewById(R.id.btnDate);
        Bhora = findViewById(R.id.btnHora);
        fecha = findViewById(R.id.etDate);
        año = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        hora = findViewById(R.id.etHora);
        checkBox = findViewById(R.id.cbHecho);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("REGISTRAR TAREA");

        basedeDatos = new BasedeDatos(getApplicationContext());

        consultarLista();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, R.layout.spinner_item_color, listacategoria);
        spinner2.setAdapter(adapter);

        Bundle datos= getIntent().getExtras();
        id =Integer.valueOf(datos.getString("id"));
        if (id != 0){
            ObtenerDatos();
            descartar.setText("Eliminar");
            guardar.setText("Modificar");
        }

        Bfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dia=calendar.get(Calendar.DATE);
                DatePickerDialog datePickerDialog=new DatePickerDialog(second_activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fecha.setText(dayOfMonth+"/"+ (month+1)+"/"+year);
                    }
                },año,mes,dia);
                datePickerDialog.show();
            }
        });

        Bhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hr = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(second_activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hora.setText(hourOfDay + ":" + minute);
                    }
                }, hr, min, false);
                timePickerDialog.show();
            }
        });
        descartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id==0) {
                    Intent intent = new Intent(second_activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    SQLiteDatabase base = basedeDatos.getWritableDatabase();
                    base.delete("AGENDA", "Id = " + id, null);
                    Toast.makeText(second_activity.this, "Eliminacion Exitosa cod: " + id, Toast.LENGTH_SHORT).show();
                    base.close();
                    Intent intent = new Intent(second_activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // metodo para colocar la fecha actual
        android.text.format.Time today = new android.text.format.Time(Time.getCurrentTimezone());
        today.setToNow();
        int d = today.monthDay;
        int m = today.month;
        int a = today.year;
        m = m + 1;
        fecha.setText(d + "/" + m + "/" + a);
    }

    private void consultarLista() {
        SQLiteDatabase bd = basedeDatos.getReadableDatabase();
        Categoria categoria = null;

        categorias = new ArrayList<Categoria>();
        Cursor fila = bd.rawQuery("select descrip from CATEGORIA", null);
        while (fila.moveToNext()) {
            categoria = new Categoria();
            categoria.setDescrip(fila.getString(0));
            categorias.add(categoria);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listacategoria = new ArrayList<String>();
        listacategoria.add("SELECCIONE");
        for (int i = 0; i < categorias.size(); i++) {
            listacategoria.add(categorias.get(i).getDescrip());
        }
    }

    public void Registrar(View view) {

        SQLiteDatabase base = basedeDatos.getWritableDatabase();
        String categoria = spinner2.getSelectedItem().toString();
        String descripg = descrip.getText().toString();

        if (categoria != "SELECCIONE" && !descripg.isEmpty()) {

            String fechag = fecha.getText().toString();
            String horag = hora.getText().toString();

            Cursor fila = base.rawQuery
                    ("select * from CATEGORIA where descrip = " + '"' + categoria + '"', null);

            if (fila.moveToFirst()) {
                int codigo = fila.getInt(0);
                String descripc = fila.getString(1);

                int i;
                if (checkBox.isChecked()) {
                    i = 1;
                } else {
                    i = 0;
                }
                ContentValues registros = new ContentValues();
                registros.put("categoria", descripc);
                registros.put("descripcion", descripg);
                registros.put("fecha", fechag);
                registros.put("hora", horag);
                registros.put("estado", i);
                registros.put("cod_cat", codigo);

                if (id != 0) {
                    base.update("AGENDA", registros, "id = " + id, null);
                    Toast.makeText(this, "Modificación Exitosa cod: " + id, Toast.LENGTH_SHORT).show();
                } else if (id == 0) {
                    base.insert("AGENDA", null, registros);
                    Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();

                    base.close();
                }
            }
            } else {
                Toast.makeText(this, "Debes Llenar Los Campos", Toast.LENGTH_SHORT).show();
            }
    }

    private void ObtenerDatos() {
        SQLiteDatabase base = basedeDatos.getReadableDatabase();
        Cursor fila = base.rawQuery
                ("select cod_cat, descripcion, fecha, hora, estado from AGENDA where Id = " + '"' + id + '"', null);
        if (fila.moveToFirst()) {
            int posicion= fila.getInt(0);
            spinner2.setSelection(posicion);
            descrip.setText(fila.getString(1));
            fecha.setText(fila.getString(2));
            hora.setText(fila.getString(3));
            int estado = Integer.valueOf(fila.getString(4));
            if (estado == 1) {
                checkBox.setChecked(true);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(second_activity.this, MainActivity.class);
        startActivity(intent);
    }
}
