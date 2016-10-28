package com.example.muslum.tabs_example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.muslum.tabs_example.R;

import static com.example.muslum.tabs_example.R.drawable.ara_btn;


public class OneFragment extends Fragment {
    private EditText num;
    public OneFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_one, container, false);
        final Button ara=(Button)view.findViewById(R.id.btnara);
        Button bir=(Button)view.findViewById(R.id.btn1);
        Button iki=(Button)view.findViewById(R.id.btn2);
        Button üc=(Button)view.findViewById(R.id.btn3);
        Button dört=(Button)view.findViewById(R.id.btn4);
        Button bes=(Button)view.findViewById(R.id.btn5);
        Button alti=(Button)view.findViewById(R.id.btn6);
        Button yedi=(Button)view.findViewById(R.id.btn7);
        Button sekiz=(Button)view.findViewById(R.id.btn8);
        Button dokuz=(Button)view.findViewById(R.id.btn9);
        Button sifir=(Button)view.findViewById(R.id.btn0);
        Button yildiz=(Button)view.findViewById(R.id.btnyildiz);
        Button kare=(Button)view.findViewById(R.id.btnkare);
        final Button sil=(Button)view.findViewById(R.id.btnsil);
        final EditText num=(EditText)view.findViewById(R.id.editText4);
        //num.setSelection(num.getText().length());
        sil.setVisibility(sil.GONE);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        bir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(num.getText() + "1");
                num.setSelection(num.getText().length());
            }
        });
        iki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(num.getText() + "2");
                num.setSelection(num.getText().length());
            }
        });
        üc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(num.getText()+"3");
                num.setSelection(num.getText().length());
            }
        });
        dört.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(num.getText()+"4");
                num.setSelection(num.getText().length());
            }
        });
        bes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(num.getText()+"5");
                num.setSelection(num.getText().length());
            }
        });
        alti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(num.getText() + "6");
                num.setSelection(num.getText().length());
            }
        });
        yedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(num.getText()+"7");
                num.setSelection(num.getText().length());
            }
        });
        sekiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(num.getText() + "8");
                num.setSelection(num.getText().length());
            }
        });
        dokuz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(num.getText()+"9");
                num.setSelection(num.getText().length());
            }
        });
        sifir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(num.getText()+"0");
                num.setSelection(num.getText().length());
            }
        });
        yildiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(num.getText() + "*");
                num.setSelection(num.getText().length());
            }
        });
        kare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(num.getText()+"#");
                num.setSelection(num.getText().length());
            }
        });

        sil.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                num.setText("");
                return false;
            }
        });
        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (num.getText().toString().length()==0)
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Daha Fazla Silinemez..!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    num.setSelection(num.getText().length());
                    String a=num.getText().toString();
                    a = a.substring(0, num.length()-1);
                    num.setText(a);
                    num.setSelection(num.getText().length());
                }
            }
        });

        num.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

                //Show cross button after start typing.
                if (num.getText().toString().length() > 0) {
                    sil.setVisibility(View.VISIBLE);
                } else {
                    sil.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                sil.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                if(num.getText().toString().length()==0)
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Aramak İstediğiniz Numarayı Giriniz..!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel: " + num.getText().toString()));
                    startActivity(i);
                    num.setText("");
                }
            }
        });
        return view;
    }
}
