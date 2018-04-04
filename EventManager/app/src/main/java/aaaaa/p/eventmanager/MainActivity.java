package aaaaa.p.eventmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import aaaaa.p.eventmanager.BackEnd.CGeoUpdater;
import aaaaa.p.eventmanager.TrackingMap.MapsActivity;

public class MainActivity extends AppCompatActivity {
    CGeoUpdater updater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //updater= new CGeoUpdater(this);
        //updater.Start();
        Intent nuevo= new Intent(this,MapsActivity.class);
        startActivity(nuevo);
    }
}
