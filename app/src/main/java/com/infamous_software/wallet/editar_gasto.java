package com.infamous_software.wallet;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class editar_gasto extends AppCompatActivity {

    EditText campoId, campoDia, campoMes, campoAño, campoComida, campoEntretenimiento, campoTransporte, campoOtros;
    ConexionSQLiteHelper conn;

    String Dia = String.valueOf(MainActivity.dia);
    String Mes = String.valueOf(MainActivity.mes);
    String Año = String.valueOf(MainActivity.año);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_gasto);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

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

        consultarSql();


    }




    private void eliminarGasto() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={campoDia.getText().toString()};
        db.delete(Utilidades.TABLA_USUARIO,Utilidades.CAMPO_DIA+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Se eliminó el gasto de este dia...",Toast.LENGTH_LONG).show();
        limpiar();
        db.close();
        setResult(RESULT_OK, null);
        finish();

    }

    private void actualizarGasto() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={campoDia.getText().toString()};
        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_DIA,campoDia.getText().toString());
        values.put(Utilidades.CAMPO_MES,campoMes.getText().toString());
        values.put(Utilidades.CAMPO_AÑO,campoAño.getText().toString());
        values.put(Utilidades.CAMPO_COMIDA,campoComida.getText().toString());
        values.put(Utilidades.CAMPO_TRANSPORTE,campoTransporte.getText().toString());
        values.put(Utilidades.CAMPO_ENTRETENIMIENTO,campoEntretenimiento.getText().toString());
        values.put(Utilidades.CAMPO_OTROS,campoOtros.getText().toString());
        db.update(Utilidades.TABLA_USUARIO,values,Utilidades.CAMPO_DIA+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Se actualizó el gasto de ete dia...",Toast.LENGTH_LONG).show();
        db.close();
        setResult(RESULT_OK, null);
        finish();


    }

    private void consultarSql() {
        SQLiteDatabase db=conn.getReadableDatabase();

        try {
            //select nombre,telefono from usuario where codigo=?
            Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_USUARIO+" WHERE dia = "+Dia+" AND mes =  "+Mes+" AND año = "+Año,null);

           // Cursor cursor=db.rawQuery("SELECT "+Utilidades.CAMPO_NOMBRE+","+Utilidades.CAMPO_TELEFONO+ " FROM "+Utilidades.TABLA_USUARIO+" WHERE "+Utilidades.CAMPO_ID+"=? ",parametros);
            cursor.moveToFirst();
            campoDia.setText(cursor.getString(1));
            campoMes.setText(cursor.getString(2));
            campoAño.setText(cursor.getString(3));
            campoComida.setText(cursor.getString(4));
            campoTransporte.setText(cursor.getString(5));
            campoEntretenimiento.setText(cursor.getString(6));
            campoOtros.setText(cursor.getString(7));

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"No existe gasto registrado en esta fecha...",Toast.LENGTH_LONG).show();
            limpiar();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar_gasto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.actualizar_gasto) {

            if(campoComida.getText().toString().trim().length() > 0 && campoTransporte.getText().toString().trim().length() > 0 && campoEntretenimiento.getText().toString().trim().length() > 0 &&campoOtros.getText().toString().trim().length() > 0){
                actualizarGasto();
            }else{
                //Toast.makeText(getApplicationContext(),"Rellene todos los campos...",Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(android.R.id.content), "Rellene todos los campos...", Snackbar.LENGTH_LONG).show();

            }
        }

        if (id == R.id.eliminar_gasto) {
            eliminarGasto();
        }
        return super.onOptionsItemSelected(item);
    }


    private void limpiar() {
        campoId.setText("");
        campoDia.setText("");
        campoMes.setText("");
        campoAño.setText("");
        campoComida.setText("");
        campoTransporte.setText("");
        campoEntretenimiento.setText("");
        campoOtros.setText("");
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

}
