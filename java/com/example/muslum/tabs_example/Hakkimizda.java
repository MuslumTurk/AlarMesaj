package com.example.muslum.tabs_example;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Thread.sleep;

/**
 * Created by Muslum on 29.12.2015.
 */
public class Hakkimizda extends Fragment {
    public Hakkimizda() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.hakkimizda, container, false);
        ImageView images=(ImageView)view.findViewById(R.id.imageView);
        images.setImageResource(R.drawable.logo);

        return view;
    }
}
