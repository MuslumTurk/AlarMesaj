package com.example.muslum.tabs_example;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Muslum on 9.1.2016.
 */
public class update extends Activity {
    String g_alici,g_mesaj,g_tarih,g_saat,yeni;
    Integer id;
    Database mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guncelle);
        final Database mydb = new Database(this);
        final EditText alici = (EditText) findViewById(R.id.editText6);
        final EditText mesaj = (EditText) findViewById(R.id.editText8);
        final EditText tarih = (EditText) findViewById(R.id.editText11);
        final EditText saat = (EditText) findViewById(R.id.editText12);
        final Button guncel = (Button) findViewById(R.id.button3);

        Bundle paketim  = new  Bundle();
        paketim = getIntent().getExtras();
        g_alici = paketim.getString("alici");
        g_mesaj = paketim.getString("mesaj");
        g_tarih = paketim.getString("tarih");
        g_saat = paketim.getString("saat");
        id = paketim.getInt("ID");
        alici.setText(g_alici);
        mesaj.setText(g_mesaj);
        tarih.setText(g_tarih);
        saat.setText(g_saat);

        Bundle paketim2  = new  Bundle();
        paketim2 = getIntent().getExtras();
        yeni = paketim.getString("alici");

        guncel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean gelen = mydb.upgrade(alici.getText().toString(), mesaj.getText().toString(), tarih.getText().toString(), saat.getText().toString(), id);
                if (gelen = true) {
                    Toast.makeText(getApplicationContext(), "Kayıt Güncellendi ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Kayıt Güncellenemedi.!! ", Toast.LENGTH_LONG).show();
                }
            }
        });
     }

}