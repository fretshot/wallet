package com.infamous_software.wallet;


public class Utilidades {

    //Constantes campos tabla usuario
    public static final String TABLA_USUARIO="usuario";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_DIA="dia";
    public static final String CAMPO_MES="mes";
    public static final String CAMPO_AÑO="año";
    public static final String CAMPO_COMIDA="comida";
    public static final String CAMPO_TRANSPORTE="transporte";
    public static final String CAMPO_ENTRETENIMIENTO="entretenimiento";
    public static final String CAMPO_OTROS="otros";

    public static final String CREAR_TABLA_USUARIO="CREATE TABLE " + ""+TABLA_USUARIO+" ("+CAMPO_ID+" " + "TEXT, "+CAMPO_DIA+" TEXT, "+CAMPO_MES+" TEXT, "+CAMPO_AÑO+" TEXT, "+CAMPO_COMIDA+" TEXT, "+CAMPO_TRANSPORTE+ " TEXT, "+CAMPO_ENTRETENIMIENTO+ " TEXT, "+CAMPO_OTROS+ " TEXT)";

}
