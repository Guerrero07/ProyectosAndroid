package com.djguerrero.todo_app.Servicios;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.djguerrero.todo_app.Activitys.MainActivity;
import com.djguerrero.todo_app.Activitys.second_activity;
import com.djguerrero.todo_app.Entidades.Tareas;
import com.djguerrero.todo_app.R;

import java.util.ArrayList;

public class ListaTareaAdapter extends RecyclerView.Adapter<ListaTareaAdapter.TareaViewHolder> {

    ArrayList<Tareas> ListaTarea;
    Boolean sw=true;
    public ListaTareaAdapter(ArrayList<Tareas>ListaTarea){
        this.ListaTarea=ListaTarea;
    }
    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_model,null,false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TareaViewHolder holder, int position) {
        holder.Id.setText(ListaTarea.get(position).getId());
        holder.Categoria.setText(ListaTarea.get(position).getCategoria());
        holder.Descripcion.setText(ListaTarea.get(position).getDescripcion());
        holder.Fecha.setText(ListaTarea.get(position).getFecha());
        holder.Hora.setText(ListaTarea.get(position).getHora());
        holder.est.setText(ListaTarea.get(position).getEstado());
        holder.expanderLayoud.setVisibility(View.GONE);
        holder.setOnClickListener();

      holder.layuod.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              if (sw==true) {
                  holder.expanderLayoud.setVisibility(View.VISIBLE);
                  sw=false;
              }else { holder.expanderLayoud.setVisibility(View.GONE);
              sw=true;}
          }
      });
        int i=Integer.valueOf(holder.est.getText().toString());
        if (i==1) {
            holder.btn2.setChecked(true);

        }else {holder.btn1.setChecked(true);
            holder.fondo.setBackgroundColor(Color.RED);
            holder.expanderLayoud.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() { return ListaTarea.size(); }

    public class TareaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context context;
        TextView Categoria, Descripcion, Fecha, Hora, Id, est, fondo;
       LinearLayout expanderLayoud, layuod;
        ImageButton Editar, Eliminar;
        RadioButton btn1, btn2;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();

            Id=itemView.findViewById(R.id.tvId);
            Categoria=itemView.findViewById(R.id.tvCat);
            Descripcion=itemView.findViewById(R.id.tvDescripcion);
            Fecha=itemView.findViewById(R.id.tvFecha);
            Hora=itemView.findViewById(R.id.tvHora);
            expanderLayoud=itemView.findViewById(R.id.Linear2);
            layuod=itemView.findViewById(R.id.Linearl);
            btn1=itemView.findViewById(R.id.rb1);
            btn2=itemView.findViewById(R.id.rb2);
            Eliminar=itemView.findViewById(R.id.btnEliminar);
            Editar=itemView.findViewById(R.id.btnEditar);
            est=itemView.findViewById(R.id.textViewE);
            fondo=itemView.findViewById(R.id.Color);
        }

     private void setOnClickListener(){
            Editar.setOnClickListener(this);
            Eliminar.setOnClickListener(this);
            btn2.setOnClickListener(this);
            btn1.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            BasedeDatos database = new BasedeDatos(v.getContext());
            SQLiteDatabase db = database.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM AGENDA WHERE id="+ Id.getText(), null);

            switch (v.getId()) {
                case R.id.btnEditar:
                    Intent intent = new Intent(v.getContext(), second_activity.class);
                    intent.putExtra("id", Id.getText());
                    v.getContext().startActivity(intent);
                    break;
                case R.id.btnEliminar:

                    if (cursor.moveToFirst()) {
                        db.delete("AGENDA", "Id = " + Id.getText(), null);
                        Toast.makeText(v.getContext(), "Eliminacion Exitosa cod: " + Id.getText(), Toast.LENGTH_SHORT).show();
                        db.close();
                        Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                        v.getContext().startActivity(intent1);
                    } else
                        {Toast.makeText(v.getContext(), "Eliminacion Fallida cod: " + Id.getText(), Toast.LENGTH_SHORT).show();}
                    break;
                case R.id.rb1:

                    if (cursor.moveToFirst()) {
                        ContentValues registros = new ContentValues();
                        registros.put("estado", 0);
                        db.update("AGENDA", registros, "id = " + Id.getText(), null);
                        db.close();
                        Toast.makeText(v.getContext(), "Pendiente", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(v.getContext(), "No Existe el ID: " + Id.getText(), Toast.LENGTH_SHORT).show();
                        db.close();
                    }
                    break;
                case R.id.rb2:

                   if (cursor.moveToFirst()) {
                        ContentValues registros = new ContentValues();
                        registros.put("estado", 1);
                        db.update("AGENDA", registros, "id = " + Id.getText(), null);
                        db.close();
                        Toast.makeText(v.getContext(), "Realizado", Toast.LENGTH_SHORT).show();
                    }else {
                       Toast.makeText(v.getContext(), "No Existe el ID: " + Id.getText(), Toast.LENGTH_SHORT).show();
                       db.close();
                   }
                    break;
            }
        }
    }
}
