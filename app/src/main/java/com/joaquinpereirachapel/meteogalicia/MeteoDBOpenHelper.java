package com.joaquinpereirachapel.meteogalicia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MeteoDBOpenHelper extends SQLiteOpenHelper {
    public MeteoDBOpenHelper(@Nullable Context context) {
        super(context, "meteo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE estacion (_id integer not null primary key, nome text not null, " +
                "concello text not null, idprovincia integer not null);");
        db.execSQL("CREATE TABLE provincia (_id integer not null primary key, nome text not null);");

        db.execSQL("CREATE TABLE concello (_id integer not null primary key, nome text not null, idprovincia integer not null);");

        db.execSQL("INSERT INTO provincia VALUES (1, 'A Coruña');");
        db.execSQL("INSERT INTO provincia VALUES (2, 'Lugo');");
        db.execSQL("INSERT INTO provincia VALUES (3, 'Ourense');");
        db.execSQL("INSERT INTO provincia VALUES (4, 'Pontevedra');");

//        // INSERCIÓN DAS ESTACIÓNS
//        db.execSQL("INSERT INTO estacion VALUES(10045,'Mabegondo','Abegondo',1);");
//        db.execSQL("INSERT INTO estacion VALUES(10053,'Campus Lugo','Lugo',2);");
//        db.execSQL("INSERT INTO estacion VALUES(10057,'Alto do Rodicio','Maceda',3);");
//        db.execSQL("INSERT INTO estacion VALUES(10061,'Mouriscade','Lalín',4);");
//        db.execSQL("INSERT INTO estacion VALUES(10062,'Ancares','Cervantes',2);");
//        db.execSQL("INSERT INTO estacion VALUES(10064,'Lourizán','Pontevedra',4);");
//        db.execSQL("INSERT INTO estacion VALUES(10086,'Fornelos de montes','Fornelos de Montes',4);");
//        db.execSQL("INSERT INTO estacion VALUES(10088,'Fragavella','Abadín',2);");
//        db.execSQL("INSERT INTO estacion VALUES(10095,'Sergude','Boqueixón',1);");
//        db.execSQL("INSERT INTO estacion VALUES(10099,'Bóveda','Bóveda',2);");
//        db.execSQL("INSERT INTO estacion VALUES(10106,'A Pontenova','A Pontenova',2);");
//        db.execSQL("INSERT INTO estacion VALUES(10109,'Amiudal','Avión',3);");
//        db.execSQL("INSERT INTO estacion VALUES(10110,'Baltar','Baltar',3);");
//        db.execSQL("INSERT INTO estacion VALUES(10111,'Entrimo','Entrimo',3);");
//        db.execSQL("INSERT INTO estacion VALUES(10112,'Gandarela','Celanova',3);");
//        db.execSQL("INSERT INTO estacion VALUES(10113,'Monte Medo','Baños de Molgas',3);");
//        db.execSQL("INSERT INTO estacion VALUES(10118,'Burela','Burela',2);");
//        db.execSQL("INSERT INTO estacion VALUES(10120,'Pereira','Forcarei',4);");
//        db.execSQL("INSERT INTO estacion VALUES(10121,'Rebordelo','Cotobade',4);");
//        db.execSQL("INSERT INTO estacion VALUES(10123,'Ourense-Ciencias','Ourense',3);");
//        db.execSQL("INSERT INTO estacion VALUES(10126,'Ons','Bueu',4);");
//        db.execSQL("INSERT INTO estacion VALUES(10127,'Caldas de Reis','Caldas de Reis',4);");
//        db.execSQL("INSERT INTO estacion VALUES(10130,'Lardeira','Carballeda de Valdeorras',3);");
//        db.execSQL("INSERT INTO estacion VALUES(10133,'Xesteiras','Cuntis',4);");
//        db.execSQL("INSERT INTO estacion VALUES(10136,'O Xipro','A Fonsagrada',2);");
//        db.execSQL("INSERT INTO estacion VALUES(10138,'Xares','A Veiga',3);");
//        db.execSQL("INSERT INTO estacion VALUES(10140,'Sabón','Arteixo',1);");
//        db.execSQL("INSERT INTO estacion VALUES(10143,'Cariño','Cariño',1);");
//        db.execSQL("INSERT INTO estacion VALUES(10144,'Raído-Arzúa','Arzúa',1);");
//        db.execSQL("INSERT INTO estacion VALUES(10146,'Castro R. de Lea','Castro de Rei',2);");
//        db.execSQL("INSERT INTO estacion VALUES(10152,'Coruña','A Coruña',1);");
//        db.execSQL("INSERT INTO estacion VALUES(10161,'Vigo-Campus','Vigo',4);");
//        db.execSQL("INSERT INTO estacion VALUES(10202,'Alto do Faro','Chantada',2);");
//        db.execSQL("INSERT INTO estacion VALUES(10203,'Alto de Cerredo','A Fonsagrada',2);");
//        db.execSQL("INSERT INTO estacion VALUES(10204,'Casaio','Carballeda de Valdeorras',3);");
//        db.execSQL("INSERT INTO estacion VALUES(10800,'Camariñas','Camariñas',1);");
//        db.execSQL("INSERT INTO estacion VALUES(14000,'Coruña Dique','A Coruña',1);");
//        db.execSQL("INSERT INTO estacion VALUES(14003,'Punta Langosteira','Arteixo',1);");
//        db.execSQL("INSERT INTO estacion VALUES(14004,'Coruña San Diego','A Coruña',1);");
//        db.execSQL("INSERT INTO estacion VALUES(19067,'Castrelo','Cambados',4);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 1 && newVersion == 2){
            db.execSQL("CREATE TABLE concello (_id integer not null primary key, nome text not null, idprovincia integer not null);");
        }
    }
}
