package com.infamous_software.wallet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;


public class ajustes_fragment extends PreferenceFragmentCompat {

    public static String USD, CLP,LIMITE;

    public ajustes_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        PreferenceManager.setDefaultValues(getActivity().getApplicationContext(), R.xml.preferences, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        USD = sharedPreferences.getString(ajustes.USD,"20.14");
        CLP = sharedPreferences.getString(ajustes.CLP,"0.030");
        LIMITE = sharedPreferences.getString(ajustes.LIMITE,"1000");

        Snackbar.make(getActivity().findViewById(android.R.id.content),"USD: "+USD+"\t\t\tCLP: "+CLP+"\t\t\tLimite de gasto: "+LIMITE, Snackbar.LENGTH_INDEFINITE).show();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        android.support.v4.app.NavUtils.navigateUpFromSameTask(getActivity());
    }
}