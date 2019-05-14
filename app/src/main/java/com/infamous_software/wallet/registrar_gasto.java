package com.infamous_software.wallet;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class registrar_gasto extends AppCompatActivity {
    EditText campoId, campoDia, campoMes, campoAño, campoComida, campoEntretenimiento, campoTransporte, campoOtros;

    public static String LIMITE;

    String Dia = String.valueOf(MainActivity.dia);
    String Mes = String.valueOf(MainActivity.mes);
    String Año = String.valueOf(MainActivity.año);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.registrar_gasto);

        campoId = findViewById(R.id.campoId);
        campoDia = findViewById(R.id.campoDia);
        campoMes = findViewById(R.id.campoMes);
        campoAño = findViewById(R.id.campoAño);
        campoComida = findViewById(R.id.campoComida);
        campoTransporte = findViewById(R.id.campoTransporte);
        campoEntretenimiento = findViewById(R.id.campoEntretenimiento);
        campoOtros = findViewById(R.id.campoOtros);

        campoDia.setText(Dia);
        campoMes.setText(Mes);
        campoAño.setText(Año);

        campoId.setEnabled(false);
        campoDia.setEnabled(false);
        campoMes.setEnabled(false);
        campoAño.setEnabled(false);

        SharedPreferences sharedPreferences = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);
        LIMITE = sharedPreferences.getString(ajustes.LIMITE,"1000");


    }


    private void registrarUsuarios() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_ID,campoId.getText().toString());
        values.put(Utilidades.CAMPO_DIA,campoDia.getText().toString());
        values.put(Utilidades.CAMPO_MES,campoMes.getText().toString());
        values.put(Utilidades.CAMPO_AÑO,campoAño.getText().toString());
        values.put(Utilidades.CAMPO_COMIDA,campoComida.getText().toString());
        values.put(Utilidades.CAMPO_TRANSPORTE,campoTransporte.getText().toString());
        values.put(Utilidades.CAMPO_ENTRETENIMIENTO,campoEntretenimiento.getText().toString());
        values.put(Utilidades.CAMPO_OTROS,campoOtros.getText().toString());
        Long idResultante=db.insert(Utilidades.TABLA_USUARIO,Utilidades.CAMPO_ID,values);
        //Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Gasto Registrado...",Toast.LENGTH_SHORT).show();
        //Snackbar.make(findViewById(android.R.id.content), "Gasto Registrado...", Snackbar.LENGTH_LONG).show();
        db.close();
        setResult(RESULT_OK, null);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_agregar_gasto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.agregar_gasto) {

           if(campoComida.getText().toString().trim().length() > 0 && campoTransporte.getText().toString().trim().length() > 0 && campoEntretenimiento.getText().toString().trim().length() > 0 &&campoOtros.getText().toString().trim().length() > 0){
               registrarUsuarios();
           }else{
               //Toast.makeText(getApplicationContext(),"Rellene todos los campos...",Toast.LENGTH_SHORT).show();
               Snackbar.make(findViewById(android.R.id.content), "Rellene todos los campos...", Snackbar.LENGTH_LONG).show();

           }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
            super.onBackPressed();
    }
}
