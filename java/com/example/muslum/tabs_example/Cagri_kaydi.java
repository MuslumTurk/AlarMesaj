package com.example.muslum.tabs_example;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Muslum on 9.1.2016.
 */
public class Cagri_kaydi extends Fragment {
    private static Cagri_kaydi inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    EditText numara;
    mesajlar gönderilen;

    public static Cagri_kaydi instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.cagri_kaydi, container, false);

        final ListView rehber = (ListView) view.findViewById(R.id.listView4);
        final ArrayList<String> isimler = new ArrayList<String>();
        final ArrayList<String> user = new ArrayList<String>(); // listemizin adını isimler olarak belirledik
        final ArrayList<Date> dates = new ArrayList<>();
        final ArrayList<String> times = new ArrayList<String>();
        final ArrayList<String> lokup = new ArrayList<String>();
        final ArrayList<String> number = new ArrayList<>();
        final ArrayAdapter<String> veriler = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, isimler); // veriler adında bir ArrayAdapter oluşturup isimler değişkenindeki verileri adapter a ekledik

        CustomListAdapter adapter=new CustomListAdapter(getActivity(), user,dates,times,number,lokup);
        rehber.setAdapter(adapter);

        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
        Cursor managedCursor = getActivity().managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, strOrder);

        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int lokupuri = managedCursor.getColumnIndex(CallLog.Calls._ID);
        int numbers = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);


        while (managedCursor.moveToNext()) {
            String phNum = managedCursor.getString(numbers);
            String callTypeCode = managedCursor.getString(type);
            String strcallDate = managedCursor.getString(date);
            String namept = managedCursor.getString(name);
            String lokuppt = managedCursor.getString(lokupuri);
            Date callDate = new Date(Long.valueOf(strcallDate));
            String callDuration = managedCursor.getString(duration);
            String callType = null;

            int callcode = Integer.parseInt(callTypeCode);
            switch (callcode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    callType = "Giden Arama";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    callType = "Gelen Arama";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    callType = "Cevapsız Arama";
                    break;
            }
            isimler.add("Numara : "+namept + "\n" + callType + "\n" + callDuration +" Saniye"+ "    " + callDate + "\n");
            user.add(namept);
            dates.add(callDate);
            times.add(callDuration);
            number.add(phNum);
            lokup.add(lokuppt);
        }
        return view;
    }
}



