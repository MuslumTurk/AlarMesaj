package com.example.muslum.tabs_example;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

import static com.example.muslum.tabs_example.R.drawable.asddd;


public class ThreeFragment extends Fragment {
    Database mydb;
    private Calendar calendar=Calendar.getInstance();
    public DateFormat formate =DateFormat.getDateInstance();
    public static EditText tarih,alici,mesaj,saat;
    private Button gönders;
    private Timer mTimer = null;
    private Servisim Time;
    private String Tarih;
    private String alicis;
    public static final String EXTRA_PRODUCT_ID = "product_id";
    public static final String EXTRA_PRODUCT_TITLE = "product_title";
    private static final String SAVED_PAGER_POSITION = "pager_position";
    private int notificationIdOne = 111;

    TwoFragment veriler;


    public ThreeFragment() {
        /*if (servis_calisiyormu()) {
            getActivity().stopService(new Intent(getActivity(), Servisim.class));
        } else {
            getActivity().startService(new Intent(getActivity(), Servisim.class));
        }*/
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_three, container, false);
        final Database mydb = new Database(getActivity());
        final Button gönder = (Button) view.findViewById(R.id.button2);
        final Button asd = (Button) view.findViewById(R.id.button6);
        final Button asd2 = (Button) view.findViewById(R.id.button4);
        final EditText alici = (EditText) view.findViewById(R.id.editText2);
        final EditText mesaj = (EditText) view.findViewById(R.id.editText7);
        final EditText tarih = (EditText) view.findViewById(R.id.editText3);
        final EditText saat = (EditText) view.findViewById(R.id.editText5);
        final CheckBox göster=(CheckBox)view.findViewById(R.id.checkBox);
        String numaraaa;
/*
        Bundle paketim  = new  Bundle();
        paketim = getActivity().getIntent().getExtras();
        numaraaa = paketim.getString("numara");
        alici.setText(numaraaa);
*/
        tarih.setVisibility(tarih.GONE);
        saat.setVisibility(saat.GONE);
        asd2.setVisibility(asd.GONE);
        asd.setVisibility(asd.GONE);
        gönder.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (alici.getText().toString().length() > 0 && mesaj.getText().toString().length() > 0) {
                    try {
                        android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
                        sms.sendTextMessage(alici.getText().toString(), null, mesaj.getText().toString(), null, null);
                        Toast.makeText(getActivity().getApplicationContext(), "Mesaj Gönderildi.. Teşekkür ederiz..", Toast.LENGTH_LONG).show();
                        doNotify();
                        alici.setText("");
                        mesaj.setText("");

                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "HATA.! Bir Sorunla Karşılaşıldı..", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "HATA..! Tüm Alanları Doldurunuz.", Toast.LENGTH_LONG).show();
                }
            }
        });
        göster.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(göster.isChecked())
                {
                    tarih.setVisibility(tarih.VISIBLE);
                    saat.setVisibility(saat.VISIBLE);
                    asd.setVisibility(asd.VISIBLE);
                    asd2.setVisibility(asd.VISIBLE);
                    gönder.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            boolean eklendi = mydb.veriekle("9" + alici.getText().toString(), mesaj.getText().toString(), tarih.getText().toString(), saat.getText().toString());

                            if (alici.getText().toString().length() > 0 && mesaj.getText().toString().length() > 0 && tarih.getText().toString().length() > 0 && saat.getText().toString().length() > 0) {

                                if (eklendi == true) {
                                    //Toast.makeText(getActivity().getApplicationContext(), "Kayıt Eklendi.\nMesajınız   " + tarih.getText().toString() + "  tarihinde\n saat  " + saat.getText().toString() + "  de Gönderilecektir.", Toast.LENGTH_LONG).show();
                                    doNotify();
                                    alici.setText("");
                                    mesaj.setText("");
                                    tarih.setText("");
                                    saat.setText("");
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "HATA..! Kayıt Eklenemedi.", Toast.LENGTH_LONG).show();

                                }
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "HATA..! Tüm Alanları Doldurunuz.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    tarih.setVisibility(tarih.GONE);
                    saat.setVisibility(saat.GONE);
                    asd.setVisibility(asd.GONE);
                    asd2.setVisibility(asd.GONE);
                }
            }
        });

        final Cursor numaralar =getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        final ArrayList<String> isimler = new ArrayList<String>(); // listemizin adını isimler olarak belirledik
        final ArrayAdapter<String> veriler = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1,isimler ); // veriler adında bir ArrayAdapter oluşturup isimler değişkenindeki verileri adapter a ekledik
        return view;
    }

    public boolean servis_calisiyormu() {
        ActivityManager servis_manager = (ActivityManager)getActivity().getSystemService(getActivity().ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo bilgi : servis_manager.getRunningServices(Integer.MAX_VALUE)) {
            if (getActivity().getApplication().getPackageName().equals(bilgi.service.getPackageName())) {
                return true;
            }
        }
        return false;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    public void doNotify(){
        NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(getActivity());
        mBuilder.setContentTitle("AlarMesaj Bildirim");
        mBuilder.setContentText("Mesajınız Kayıt Altına Alındı");
        mBuilder.setTicker("New Message !");
        mBuilder.setSmallIcon(R.drawable.notify);

        Intent notificationIntent = new Intent(getActivity().getApplicationContext(), mesajlar.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity().getApplicationContext(), 0, notificationIntent, 0);

        mBuilder.setContentIntent(contentIntent);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.notify);
        mBuilder.setLargeIcon(bm);
        mBuilder.setAutoCancel(true);
        NotificationManager myNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.notify(notificationIdOne, mBuilder.build());

    }
    public void onStart() {
        super.onStart();
        tarih =(EditText)getView().findViewById(R.id.editText3);
        saat =(EditText)getView().findViewById(R.id.editText5);
        Button asd = (Button) getActivity().findViewById(R.id.button6);
        Button asd2 = (Button) getActivity().findViewById(R.id.button4);
        asd.setOnClickListener(onClickListener);
        asd2.setOnClickListener(onClickListener);
    }

    public void showdate(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("dateAsText",tarih.getText().toString());
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void returnDate(String date) {
        tarih.setText(calendar.get(Calendar.YEAR));
    }

    public void showtime(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("timeAsText",saat.getText().toString());
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void returnTime(String time) {

        saat.setText(calendar.get(Calendar.HOUR_OF_DAY));
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button6:
                    showdate(v);
                    break;
                case R.id.button4:
                    showtime(v);
                    break;
                default:
                    break;
            }
        }
    };
}
