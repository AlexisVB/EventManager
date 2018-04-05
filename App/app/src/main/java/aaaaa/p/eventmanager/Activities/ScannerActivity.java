package aaaaa.p.eventmanager.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import aaaaa.p.eventmanager.API.API;
import aaaaa.p.eventmanager.API.Servidor;
import aaaaa.p.eventmanager.Modelos.Grupo;
import aaaaa.p.eventmanager.Modelos.Relacion;
import aaaaa.p.eventmanager.Modelos.Tracking;
import aaaaa.p.eventmanager.Utilidades;
import aaaaa.p.eventmanager.VariablesGlobales;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static aaaaa.p.eventmanager.VariablesGlobales.grupo;
import static aaaaa.p.eventmanager.VariablesGlobales.relacion;
import static aaaaa.p.eventmanager.VariablesGlobales.tracking;

public class ScannerActivity extends Activity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;

    private int idUser;
    private SharedPreferences preferencias;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view

        preferencias = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        final String code = rawResult.getContents(); // Prints scan results
        final String format = rawResult.getBarcodeFormat().getName(); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);

        Toast.makeText(ScannerActivity.this, "Contents = " + code + ", Format = " + format, Toast.LENGTH_LONG).show();

        int cont = 0;
        String id = "";
        do{
            id+=code.substring(cont, cont+1);
            cont++;

        }while (code.charAt(cont)>47 && code.charAt(cont)<58);

        idUser = Integer.parseInt(id);

        insert();

    }

    private void insert() {
        Thread peticion= new Thread(new Runnable() {
            @Override
            public void run() {
                Servidor servicio = API.geApi().create(Servidor.class);
                final Call<Relacion> relacionCall = servicio.insertarDispositivo("00:00:00:00", idUser, grupo.getIdGroup());

                relacionCall.enqueue(new Callback<Relacion>() {
                    @Override
                    public void onResponse(Call<Relacion> call, Response<Relacion> response) {
                        relacion = response.body();
                        if (relacion != null) {
                            select();
                            /*Toast.makeText(GruposActivity.this, "Usuario creado con éxito.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(GruposActivity.this, CrearGrupoActivity.class);
                            startActivity(intent);*/
                        } else {
                            Toast.makeText(ScannerActivity.this, "Error: usuario no creado.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Relacion> call, Throwable t) {
                        Toast.makeText(ScannerActivity.this, "Error en la conexión.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        peticion.start();

    }

    private void select() {
        Thread peticion= new Thread(new Runnable() {
            @Override
            public void run() {
                Servidor servicio = API.geApi().create(Servidor.class);
                final Call<List<Tracking>> trackingCall = servicio.dispositivoPorGrupo(grupo.getIdGroup());

                trackingCall.enqueue(new Callback<List<Tracking>>() {
                    @Override
                    public void onResponse(Call<List<Tracking>> call, Response<List<Tracking>> response) {
                        tracking = response.body();
                        if (tracking!= null) {
                            Toast.makeText(ScannerActivity.this, "Usuario agregado con éxito.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ScannerActivity.this, CrearGrupoActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ScannerActivity.this, "Error: usuario no creado.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Tracking>> call, Throwable t) {
                        Toast.makeText(ScannerActivity.this, "Error en la conexión.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        peticion.start();

    }

}