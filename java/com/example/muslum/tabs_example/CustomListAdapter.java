package com.example.muslum.tabs_example;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import static android.support.v4.app.ActivityCompat.startActivity;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;
    private final ArrayList<Date> date;
    private final ArrayList<String> time;
    private final ArrayList<String> number;
    private final ArrayList<String> lokup;

    public CustomListAdapter(Activity context, ArrayList<String> itemname, ArrayList<Date> date,  ArrayList<String> times, ArrayList<String> number,ArrayList<String> lokup) {
        super(context, R.layout.satir_layout, itemname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.itemname = itemname;
        this.date = date;
        this.time = times;
        this.number = number;
        this.lokup = lokup;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.satir_layout, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
        Button ara = (Button) rowView.findViewById(R.id.button);

        txtTitle.setText(itemname.get(position));
        imageView.setImageResource(R.drawable.contact);

        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                final AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                b.setIcon(android.R.drawable.ic_dialog_alert);
                b.setMessage("Silmek İstermisiniz?");

                b.setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, CallLog.Calls._ID + "= ? ", new String[]{lokup.get(position)});
                        Toast.makeText(getContext(), "Silindi : " + number.get(position), Toast.LENGTH_LONG).show();
                    }
                });
                b.show();
                return false;
            }
        });

        if((itemname).get(position)==" " || (itemname.get(position)==null))
        {
            txtTitle.setText(number.get(position));
            extratxt.setText("Kaydedilmeyen Numara");
        }
        else
        {
            extratxt.setText("Numara :"+number.get(position) +"\nSüre :"+time.get(position));
        }

        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent git = new Intent(Intent.ACTION_CALL);
                git.setData(Uri.parse("tel: " + number.get(position).toString()));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                v.getContext().startActivity(git);
            }

        });
        return rowView;
    }
}