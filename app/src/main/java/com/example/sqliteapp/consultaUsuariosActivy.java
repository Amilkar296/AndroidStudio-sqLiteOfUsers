package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqliteapp.utilidades.utilidades;

public class consultaUsuariosActivy extends AppCompatActivity {

    EditText campoId, campoNombre, campoTelefono;

    ConeccionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_usuarios_activy);

        conn=new ConeccionSQLiteHelper(getApplicationContext(),"bd_usuarios", null,1);


        campoId= (EditText) findViewById(R.id.documentoId);
        campoNombre= (EditText) findViewById(R.id.campoNombreConsulta);
        campoTelefono= (EditText) findViewById(R.id.campoTelefonoConsulta);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnConsultar:
                consultar();
                //consultarSql();
                break;
            case R.id.btnActualizar:
                actualizarUsuario();
                break;
            case R.id.btnEliminar:
                eliminarUsuario();
                break;
        }
    }

    private void eliminarUsuario() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={campoId.getText().toString()};

        db.delete(utilidades.TABLA_USUARIO,utilidades.CAMPO_ID+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Se elimino el usuario",Toast.LENGTH_LONG).show();
        campoId.setText("");
        limpiar();
        db.close();
    }

    private void actualizarUsuario() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={campoId.getText().toString()};
        ContentValues values=new ContentValues();
        values.put (utilidades.CAMPO_NOMBRE,campoNombre.getText().toString());
        values.put (utilidades.CAMPO_TELEFONO,campoTelefono.getText().toString());

        db.update(utilidades.TABLA_USUARIO,values,utilidades.CAMPO_ID+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se actualizo el usuario",Toast.LENGTH_LONG).show();
        campoId.setText("");
        limpiar();
        db.close();
    }

    private void consultarSql() {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={campoId.getText().toString()};

        try{
            //select nombre, telefono from usuario where codigo
            Cursor cursor=db.rawQuery("SELECT "+ utilidades.CAMPO_NOMBRE+","
            +utilidades.CAMPO_TELEFONO+" FROM "+utilidades.TABLA_USUARIO+" WHERE "
            +utilidades.CAMPO_ID+"=?",parametros);

            cursor.moveToFirst();
            campoNombre.setText(cursor.getString(0));
            campoTelefono.setText(cursor.getString(1));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();
            limpiar();
        }
    }

    private void consultar() {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={campoId.getText().toString()};
        String[] campos={utilidades.CAMPO_NOMBRE,utilidades.CAMPO_TELEFONO};

        try{
            Cursor cursor=db.query(utilidades.TABLA_USUARIO,campos,utilidades.CAMPO_ID+"=?",parametros,null,null,null);
            cursor.moveToFirst();
            campoNombre.setText(cursor.getString(0));
            campoTelefono.setText(cursor.getString(1));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();
            limpiar();
        }



    }

    private void limpiar() {
        campoNombre.setText("");
        campoTelefono.setText("");

    }
}