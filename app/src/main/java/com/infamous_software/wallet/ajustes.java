package com.infamous_software.wallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ajustes extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new ajustes_fragment()).commit();
    }

    public static final String USD ="usd";
    public static final String CLP ="clp";
    public static final String LIMITE ="limite";


}
