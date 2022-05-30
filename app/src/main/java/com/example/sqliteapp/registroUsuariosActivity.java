package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqliteapp.utilidades.utilidades;

public class registroUsuariosActivity extends AppCompatActivity {

    EditText campoId, campoNombre, campoTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        campoId= (EditText) findViewById(R.id.campoId);
        campoNombre= (EditText) findViewById(R.id.campoNombre);
        campoTelefono= (EditText) findViewById(R.id.campoTelefono);
    }

    public void onClick(View view){
        registrarUsuarios();
        //registrarUsuariosSql();
    }

    private void registrarUsuariosSql() {

        ConeccionSQLiteHelper conn=new ConeccionSQLiteHelper(this,"bd_usuarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        //insert into usuarios (id, nombre, telefono) values (123,'Daniel','7777772')
        String insert="INSERT INTO "+utilidades.TABLA_USUARIO
                +" ( "
                +utilidades.CAMPO_ID+","+utilidades.CAMPO_NOMBRE+","+utilidades.CAMPO_TELEFONO+") " +
                " VALUES ("+campoId.getText().toString()+", '"+ campoNombre.getText().toString()+"','"
                +campoTelefono.getText().toString()+"')";

        db.execSQL(insert);



        db.close();
    }

    private void registrarUsuarios() {
        ConeccionSQLiteHelper conn=new ConeccionSQLiteHelper(this,"bd_usuarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(utilidades.CAMPO_ID,campoId.getText().toString());
        values.put(utilidades.CAMPO_NOMBRE,campoNombre.getText().toString());
        values.put(utilidades.CAMPO_TELEFONO,campoTelefono.getText().toString());

        Long idResultante=db.insert(utilidades.TABLA_USUARIO,utilidades.CAMPO_ID,values);

        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();

        //aumentando eso de limpiar datos
        campoId.setText("");
        campoNombre.setText("");
        campoTelefono.setText("");
        db.close();

    }
}