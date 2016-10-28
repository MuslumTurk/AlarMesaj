package com.example.muslum.tabs_example;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Muslum on 23.12.2015.
 */
public class TwoFragment extends Fragment {
    public EditText nmra;
    public TwoFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_two, container, false);
        final ListView rehber = (ListView)view.findViewById(R.id.listView);// ListView e rehber değişken adını verdik
        final EditText nmra =(EditText)view.findViewById(R.id.editText);
        final Button msj=(Button)view.findViewById(R.id.button9);

        final ArrayList<String> name = new ArrayList<String>();
        final ArrayList<String> number = new ArrayList<>();
        final ArrayList<String> isimler = new ArrayList<String>();

        msj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setAction(Intent.ACTION_INSERT);
                intent.setData(ContactsContract.Contacts.CONTENT_URI);
                startActivity(intent);
            }
        });


        String strOrder = android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        final Cursor numaralar =getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, strOrder);
        final ArrayAdapter<String> veriler = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1,isimler ); // veriler adında bir ArrayAdapter oluşturup isimler değişkenindeki verileri adapter a ekledik

        final rehber_custom adapter=new rehber_custom(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1,name,number);

        rehber.setAdapter(adapter);
        //rehber.setAdapter(veriler);

        while (numaralar.moveToNext()){

            String contactName = numaralar.getString(numaralar.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phNumber = numaralar.getString(numaralar.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


            isimler.add(contactName + "\n" + phNumber);
            name.add(contactName);
            number.add(phNumber);
            Collections.sort(name);

        }

        nmra.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                System.out.println(cs.toString());
                adapter.getFilter().filter(cs.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });


        rehber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).getText().toString();

                String[] str;
                str = ((TextView) view).getText().toString().split("\n");
                nmra.setText(str[1]);
                Toast.makeText(getActivity().getApplicationContext(), "Tıklanan Kişi Bilgileri\n İsmi: "+ str[0]+"\n Numarası: "+str[1], Toast.LENGTH_LONG).show();
            }
        });

        numaralar.close();
        return view;
    }
}

