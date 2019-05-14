package com.infamous_software.wallet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    String USD, CLP, LIMITE;


    public TextView campoFecha, campoId, campoDia, campoMes, campoAño, campoComida, campoEntretenimiento, campoTransporte, campoOtros, gastoTotalMNX;
    public TextView campoComidaUSD, campoComidaCLP, campoTransporteUSD, campoTransporteCLP, campoEntretenimientoUSD, campoEntretenimientoCLP, campoOtrosUSD, campoOtrosCLP;
    public TextView warning;

    public static int dia;
    public static int mes;
    public static int año;

    public int diaActual;
    public int mesActual;
    public int añoActual;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

        SharedPreferences sharedPreferences = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);
        USD = sharedPreferences.getString(ajustes.USD,"20.14");
        CLP = sharedPreferences.getString(ajustes.CLP,"0.030");
        LIMITE = sharedPreferences.getString(ajustes.LIMITE,"1000");

        campoFecha = findViewById(R.id.fecha);
        gastoTotalMNX = findViewById(R.id.gastoTotalMNX);
        warning = findViewById(R.id.warning);

        campoComida = findViewById(R.id.campoComida);
        campoComidaUSD = findViewById(R.id.campoComidaUSD);
        campoComidaCLP = findViewById(R.id.campoComidaCLP);

        campoTransporte = findViewById(R.id.campoTransporte);
        campoTransporteUSD = findViewById(R.id.campoTransporteUSD);
        campoTransporteCLP = findViewById(R.id.campoTransporteCLP);

        campoEntretenimiento = findViewById(R.id.campoEntretenimiento);
        campoEntretenimientoUSD = findViewById(R.id.campoEntretenimientoUSD);
        campoEntretenimientoCLP = findViewById(R.id.campoEntretenimientoCLP);

        campoOtros = findViewById(R.id.campoOtros);
        campoOtrosUSD = findViewById(R.id.campoOtrosUSD);
        campoOtrosCLP = findViewById(R.id.campoOtrosCLP);

        warning.setVisibility(View.INVISIBLE);


        DateFormat df = new SimpleDateFormat("d/M/yyyy");
        String date = df.format(Calendar.getInstance().getTime());

        String[] splitDate = date.split("/");
        diaActual = Integer.parseInt(splitDate[0]);
        mesActual = Integer.parseInt(splitDate[1]);
        añoActual = Integer.parseInt(splitDate[2]);

        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                dia = i2;
                mes = i1 +1;
                año = i;

                String fechaSeleccionada = i2 + " / "+(i1 + 1)+ " / "+i;
                campoFecha.setText(fechaSeleccionada);
                consultarSqlHoy();

            }
        });
        consultarSql();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.agregar_gasto) {
            if(dia == 0 && mes == 0 && año == 0){
                Snackbar.make(findViewById(android.R.id.content), "Primero seleccione un dia...", Snackbar.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(this, registrar_gasto.class);
                startActivityForResult(intent, 1);
            }
            return true;
        }

        if (id == R.id.ajustes) {
            Intent intent = new Intent(this, ajustes.class);
            startActivityForResult(intent, 3);
            return true;
        }

        if (id == R.id.editar_gasto) {
            if(dia == 0 && mes == 0 && año == 0) {
                Snackbar.make(findViewById(android.R.id.content), "Primero seleccione un dia...", Snackbar.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(this, editar_gasto.class);
                startActivityForResult(intent, 2);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void consultarSql() {
        SQLiteDatabase db=conn.getReadableDatabase();
        try {
            //select nombre,telefono from usuario where codigo=?
            Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_USUARIO+" WHERE dia = "+diaActual+" AND mes =  "+mesActual+" AND año = "+añoActual,null);
            // Cursor cursor=db.rawQuery("SELECT "+Utilidades.CAMPO_NOMBRE+","+Utilidades.CAMPO_TELEFONO+ " FROM "+Utilidades.TABLA_USUARIO+" WHERE "+Utilidades.CAMPO_ID+"=? ",parametros);
            cursor.moveToFirst();

            campoComida.setText(cursor.getString(4));
            campoTransporte.setText(cursor.getString(5));
            campoEntretenimiento.setText(cursor.getString(6));
            campoOtros.setText(cursor.getString(7));

           convertirUSD();
           convertirCLP();
           gastadoHoyMNX();
        }catch (Exception e){
            Snackbar.make(findViewById(android.R.id.content), "No existe gasto registrado en esta fecha...", Snackbar.LENGTH_LONG).show();
            gastoTotalMNX.setText("$ 0.00 MNX");
            limpiar();
        }
    }

    private void consultarSqlHoy() {
        SQLiteDatabase db=conn.getReadableDatabase();
        try {

            Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_USUARIO+" WHERE dia = "+dia+" AND mes =  "+mes+" AND año = "+año,null);

            // Cursor cursor=db.rawQuery("SELECT "+Utilidades.CAMPO_NOMBRE+","+Utilidades.CAMPO_TELEFONO+ " FROM "+Utilidades.TABLA_USUARIO+" WHERE "+Utilidades.CAMPO_ID+"=? ",parametros);
            cursor.moveToFirst();

            String dia = cursor.getString(1);
            String mes = cursor.getString(2);
            String año = cursor.getString(3);
            String fecha = dia+" / "+mes+" / "+año;

            campoFecha.setText(fecha);
            campoComida.setText(cursor.getString(4));
            campoTransporte.setText(cursor.getString(5));
            campoEntretenimiento.setText(cursor.getString(6));
            campoOtros.setText(cursor.getString(7));

            gastadoHoyMNX();
            convertirUSD();
            convertirCLP();

        }catch (Exception e){
            Snackbar.make(findViewById(android.R.id.content), "No existe gasto registrado en esta fecha...", Snackbar.LENGTH_LONG).show();
            gastoTotalMNX.setText("$ 0.00 MNX");
            limpiar();
        }
    }

    private void limpiar() {
        campoComida.setText("");
        campoTransporte.setText("");
        campoEntretenimiento.setText("");
        campoOtros.setText("");
        campoComidaUSD.setText("");
        campoTransporteUSD.setText("");
        campoEntretenimientoUSD.setText("");
        campoOtrosUSD.setText("");
        campoComidaCLP.setText("");
        campoTransporteCLP.setText("");
        campoEntretenimientoCLP.setText("");
        campoOtrosCLP.setText("");
        warning.setVisibility(View.INVISIBLE);
    }

    private void convertirUSD(){
        String s_mnxComida = (String) campoComida.getText();
        float mnxComida = Float.parseFloat(s_mnxComida);
        float usdComida = mnxComida / Float.parseFloat(USD);
        campoComidaUSD.setText(String.valueOf(new DecimalFormat("#.##").format(usdComida)));

        String s_mnxTransporte = (String) campoTransporte.getText();
        float mnxTransporte = Float.parseFloat(s_mnxTransporte);
        float usdTransporte = mnxTransporte / Float.parseFloat(USD);
        campoTransporteUSD.setText(String.valueOf(new DecimalFormat("#.##").format(usdTransporte)));

        String s_mnxEntretenimiento = (String) campoEntretenimiento.getText();
        float mnxEntretenimiento = Float.parseFloat(s_mnxEntretenimiento);
        float usdEntretenimiento = mnxEntretenimiento / Float.parseFloat(USD);
        campoEntretenimientoUSD.setText(String.valueOf(new DecimalFormat("#.##").format(usdEntretenimiento)));

        String s_mnxOtros = (String) campoOtros.getText();
        float mnxOtros = Float.parseFloat(s_mnxOtros);
        float usdOtros = mnxOtros / Float.parseFloat(USD);
        campoOtrosUSD.setText(String.valueOf(new DecimalFormat("#.##").format(usdOtros)));
    }

    private void convertirCLP(){
        String s_mnxComida = (String) campoComida.getText();
        float mnxComida = Float.parseFloat(s_mnxComida);
        float clpComida = mnxComida / Float.parseFloat(CLP);
        campoComidaCLP.setText(String.valueOf(new DecimalFormat("#.##").format(clpComida)));

        String s_mnxTransporte = (String) campoTransporte.getText();
        float mnxTransporte = Float.parseFloat(s_mnxTransporte);
        float clpTransporte = mnxTransporte / Float.parseFloat(CLP);
        campoTransporteCLP.setText(String.valueOf(new DecimalFormat("#.##").format(clpTransporte)));

        String s_mnxEntretenimiento = (String) campoEntretenimiento.getText();
        float mnxEntretenimiento = Float.parseFloat(s_mnxEntretenimiento);
        float clpEntretenimiento = mnxEntretenimiento / Float.parseFloat(CLP);
        campoEntretenimientoCLP.setText(String.valueOf(new DecimalFormat("#.##").format(clpEntretenimiento)));

        String s_mnxOtros = (String) campoOtros.getText();
        float mnxOtros = Float.parseFloat(s_mnxOtros);
        float clpOtros = mnxOtros / Float.parseFloat(CLP);
        campoOtrosCLP.setText(String.valueOf(new DecimalFormat("#.##").format(clpOtros)));
    }

    private void gastadoHoyMNX(){

        float lim = Float.parseFloat(LIMITE);

        String s_mnxComida = (String) campoComida.getText();
        float mnxComida = Float.parseFloat(s_mnxComida);

        String s_mnxTransporte = (String) campoTransporte.getText();
        float mnxTransporte = Float.parseFloat(s_mnxTransporte);

        String s_mnxEntretenimiento = (String) campoEntretenimiento.getText();
        float mnxEntretenimiento = Float.parseFloat(s_mnxEntretenimiento);

        String s_mnxOtros = (String) campoOtros.getText();
        float mnxOtros = Float.parseFloat(s_mnxOtros);

        float totalMNX = mnxComida+mnxTransporte+mnxEntretenimiento+mnxOtros;

        if(totalMNX > lim){
            warning.setVisibility(View.VISIBLE);
        }else{
            warning.setVisibility(View.INVISIBLE);
        }

        String msg = "$ "+String.valueOf(new DecimalFormat("#.##").format(totalMNX))+" MNX";
        gastoTotalMNX.setText(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
            this.finish();
        }
    }


}
