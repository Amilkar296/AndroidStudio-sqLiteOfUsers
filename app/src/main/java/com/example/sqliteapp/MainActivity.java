package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConeccionSQLiteHelper conn=new ConeccionSQLiteHelper(this,"bd_usuarios",null,1);


    }

    public void onClick(View view){
        Intent miIntent=null;
        switch (view.getId()){
            case R.id.btnOpcionRegistro:
                miIntent = new Intent(MainActivity.this,registroUsuariosActivity.class);
                break;
            case R.id.btnOpcionConsulta:
                miIntent = new Intent(MainActivity.this,consultaUsuariosActivy.class);
                break;
            case R.id.btnConsultaSpinner:
                miIntent = new Intent(MainActivity.this,consultaCombo.class);
                break;

        }
        if (miIntent!=null){
            startActivity(miIntent);
        }
    }
}