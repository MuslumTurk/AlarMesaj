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
public class mesajlar extends Activity {
    String numaraaa;
    TwoFragment deneme;
    Database mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mesaj_kayitlari);
        final Database mydb = new Database(this);

        final ListView mesaj = (ListView) findViewById(R.id.listView3);
        final ArrayList<String> gelen = mydb.listele();

        final ArrayAdapter<String> veriler = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1,gelen ); // veriler adında bir ArrayAdapter oluşturup isimler değişkenindeki verileri adapter a ekledik
        mesaj.setAdapter(veriler);
        System.out.println(gelen);

        mesaj.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> av, View v, final int pos, final long id) {

                final AlertDialog.Builder b = new AlertDialog.Builder(mesajlar.this);
                b.setIcon(android.R.drawable.ic_dialog_alert);
                b.setMessage("Değişiklik Yapmak İstermisiniz?");
                b.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String[] str;
                        str = gelen.toString().split("\n");
                        System.out.println(str[0] + str[1] + str[2] + str[3]);

                        Intent git = new Intent(mesajlar.this, update.class);
                        git.putExtra("alici", str[0]);
                        git.putExtra("mesaj", str[1]);
                        git.putExtra("tarih", str[2]);
                        git.putExtra("saat", str[3]);
                        git.putExtra("ID", (pos + 1));
                        startActivity(git);
                    }
                });

                b.setNegativeButton("Sil", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        final AlertDialog.Builder a = new AlertDialog.Builder(mesajlar.this);
                        a.setIcon(android.R.drawable.ic_dialog_alert);
                        a.setMessage("Emin Misiniz?");
                        a.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                boolean gelen = mydb.delete((pos + 1));
                                if (gelen = true) {
                                    Toast.makeText(getApplicationContext(), "Kayıt Silindi ", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Kayıt Silinemedi.!! ", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        a.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        });
                        a.show();
                    }
                });

                        b.show();
                        return true;
                    }
                });
    }

}
