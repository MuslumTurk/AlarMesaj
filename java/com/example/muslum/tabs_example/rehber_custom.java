package com.example.muslum.tabs_example;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by muslum on 5/7/16.
 */
public class rehber_custom extends ArrayAdapter<String> {

private final Activity context;
private final ArrayList<String> name;
private final ArrayList<String> number;

public rehber_custom(Activity context, int simple_list_item_1, int text1, ArrayList<String> name, ArrayList<String> number) {
    super(context, R.layout.rehber_custom, name);
    this.context = context;
    this.name = name;
    this.number = number;
}

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.rehber_custom, null, true);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1,name);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
        Button ara = (Button) rowView.findViewById(R.id.button);

        txtTitle.setText(name.get(position));
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

                        context.getContentResolver().delete(ContactsContract.Contacts.CONTENT_URI, ContactsContract.Contacts.DISPLAY_NAME + "= ? ", new String[]{name.get(position)});
                        Toast.makeText(getContext(), "Silindi : " + number.get(position), Toast.LENGTH_LONG).show();
                    }
                });
                b.show();
                return false;
            }
        });

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
