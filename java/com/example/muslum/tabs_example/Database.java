package com.example.muslum.tabs_example;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Muslum on 26.12.2015.
 */
public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="MTrkDatabase.db";
    public static final String TABLE_NAME="MTKonusmalar";
    public static final String COL_1="ID";
    public static final String COL_2="ALICI";
    public static final String COL_3="MESAJ";
    public static final String COL_4="TARIH";
    public static final String COL_5="SAAT";
    private SQLiteDatabase db;

    private String[] sutunlar={COL_2,COL_3,COL_4,COL_5};

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ALICI INTEGER NOT NULL ,MESAJ TEXT NOT NULL, TARIH INTEGER NOT NULL, SAAT INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean upgrade(String alici, String mesaj, String tarih, String saat, int id)
    {
        db=this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COL_2, alici);
        args.put(COL_3, mesaj);
        args.put(COL_4, tarih);
        args.put(COL_5, saat);
        db.update(TABLE_NAME, args, COL_1 + "=" + id, null);
        return true;
    }
    public boolean delete(int id)
    {
        db=this.getWritableDatabase();
        ContentValues args = new ContentValues();
        db.delete(TABLE_NAME, COL_1 + "=" + id, null);
        return true;
    }

    public boolean veriekle(String alici,String mesaj,String tarih,String saat)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content =new ContentValues();
        content.put(COL_2,alici);
        content.put(COL_3,mesaj);
        content.put(COL_4,tarih);
        content.put(COL_5, saat);
        long eklenen =  db.insert(TABLE_NAME,null,content);
        if(eklenen==-1)
            return false;
        else
            return true;
    }

   public ArrayList<String> listele() {
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor okunanlar = db.query(TABLE_NAME, sutunlar, null, null, null, null, null);
       ArrayList<String> isimler = new ArrayList<String>();
       while (okunanlar.moveToNext())
       {
           String alici =okunanlar.getString(okunanlar.getColumnIndex("ALICI"));
           String mesaj =okunanlar.getString(okunanlar.getColumnIndex("MESAJ"));
           String tarih =okunanlar.getString(okunanlar.getColumnIndex("TARIH"));
           String saat =okunanlar.getString(okunanlar.getColumnIndex("SAAT"));
           isimler.add(alici+"\n"+mesaj+"\n"+tarih+"\n"+saat+"\n");
       }
       return isimler;
   }
    public boolean kayitgor()  {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor okunanlar = db.query(TABLE_NAME, sutunlar, null, null, null, null, null);
        boolean al = false;
        while (okunanlar.moveToNext())
        {
            String tarih = okunanlar.getString(okunanlar.getColumnIndex("TARIH"));
            String saat =okunanlar.getString(okunanlar.getColumnIndex("SAAT"));
            String alici =okunanlar.getString(okunanlar.getColumnIndex("ALICI"));
            String mesaj =okunanlar.getString(okunanlar.getColumnIndex("MESAJ"));

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd - HH:mm");
                Date date1 = sdf.parse(tarih+" - "+saat);
                Date date2 = sdf.parse(getDateTime().toString());

                if (date1.equals(date2)) {
                    try {
                        android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
                        sms.sendTextMessage(alici, null, mesaj, null, null);
                        al = true;
                    } catch (Exception e) {
                        al = false;
                    }
                } else {
                    al = false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return al;
    }

    public String getDateTime() {
        // get date time in custom format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd - HH:mm");
        return sdf.format(new Date());
    }

}
