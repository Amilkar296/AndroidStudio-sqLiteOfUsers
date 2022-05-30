package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sqliteapp.entidades.Usuario;
import com.example.sqliteapp.utilidades.utilidades;

import java.util.ArrayList;
import java.util.List;

public class consultaCombo extends AppCompatActivity {

    //https://www.youtube.com/watch?v=rlEczdtlsiM #58
    Spinner comboPersonas;
    TextView txtNombre,txtDocumento,txtTelefono;
    ArrayList<String>listaPersonas;
    ArrayList<Usuario>PersonasList;

    ConeccionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_combo);

        conn=new ConeccionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

        comboPersonas= (Spinner) findViewById(R.id.comboPersonas);

        txtDocumento= (TextView) findViewById(R.id.txtDocumento);
        txtNombre= (TextView) findViewById(R.id.txtNombre);
        txtTelefono= (TextView) findViewById(R.id.txtTelefono);

        consultarListaPersonas();

        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter
                (this, android.R.layout.simple_spinner_item,listaPersonas);

        comboPersonas.setAdapter(adaptador);

        comboPersonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idl) {

                if(position!=0) {
                    txtDocumento.setText(PersonasList.get(position - 1).getId().toString());
                    txtNombre.setText(PersonasList.get(position - 1).getNombre());
                    txtTelefono.setText(PersonasList.get(position - 1).getTelefono());
                }else{
                    txtDocumento.setText("");
                    txtNombre.setText("");
                    txtTelefono.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private void consultarListaPersonas() {
        SQLiteDatabase db=conn.getWritableDatabase();

        Usuario persona=null;
        PersonasList = new ArrayList<Usuario>();
        //select * from ususarios
        Cursor cursor=db.rawQuery("SELECT * FROM "+ utilidades.TABLA_USUARIO,null);


        while (cursor.moveToNext()){
            persona=new Usuario();
            persona.setId(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setTelefono(cursor.getString(2));

            Log.i("id",persona.getId().toString());
            Log.i("Nom",persona.getNombre());
            Log.i("Tel",persona.getTelefono());

            PersonasList.add(persona);

        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaPersonas=new ArrayList<String>();
        listaPersonas.add("Seleccione");

        for(int i=0;i<PersonasList.size();i++){
            listaPersonas.add(PersonasList.get(i).getId()+" - "+PersonasList.get(i).getNombre());
        }
    }
}