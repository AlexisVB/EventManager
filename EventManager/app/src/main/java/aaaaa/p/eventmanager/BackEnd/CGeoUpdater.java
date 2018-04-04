package aaaaa.p.eventmanager.BackEnd;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import aaaaa.p.eventmanager.API.API;
import aaaaa.p.eventmanager.MapsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexis on 04/04/2018.
 */

public class CGeoUpdater {
    //lista de dispositivos
    //Busca en los dispositivos en nube
    //obtiene la posición de cada uno
    //avisa que tiene nuevos datos
    private boolean disponible;
    private  CContainer actual;
    private MapsActivity padre;

    public CGeoUpdater(MapsActivity p)
    {
        padre= p;
        disponible=false;

    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void Start()
    {
        //recibir listas
        peticionHttp();

    }
    //Lista para buscar tus dispositivos
    public ArrayList<Clocation> SearchDevices(ArrayList<CDevice> dispositivos)
    {
        ArrayList<Clocation> puntos= new ArrayList<Clocation>();
        for(int i=0;i<dispositivos.size();i++)
        {
            puntos.add(SearchByMac(dispositivos.get(i).getClientMac()));
        }
        return  puntos;
    }
    //favor de implementar esto en un hilo :)
    private void peticionHttp()
    {
    try
    {
        InterfaceHttp service= API.getApiUpdates().create(InterfaceHttp.class);
        Call<CContainer> updatesCall=service.getUpdates();
        updatesCall.enqueue(new Callback<CContainer>() {
            @Override
            public void onResponse(Call<CContainer> call, Response<CContainer> response) {

                actual=response.body();
                disponible=true;
                padre.RenderPoints();
                //meter a la cola de actualización
                Log.w("Hi","OK");
            }

            @Override
            public void onFailure(Call<CContainer> call, Throwable t) {
                Log.w("Err","Fuk");
            }
        });
    }catch (Exception e) {
        Log.w("Err", "Fuk2");
    }

    }
    //yo solo estoy para ayudar
    private Clocation SearchByMac(String Mac)
    {
        double lng=0;
        double lat=0;
        int r=0;
        for(int i=0;i<actual.getUpdate_set().size();i++)//recorrer Aps
        {
            CAp current= actual.getUpdate_set().get(i);
            for(int cont=0;cont<current.getObservations().size();cont++)//recorrer dispositivos en Aps
            {
                CDevice curDevice=current.getObservations().get(cont);
                if(curDevice.getClientMac().compareTo(Mac)==0)
                {
                    Clocation l= curDevice.getLocation();
                    lng+=l.getLng();
                    lat+=l.getLat();
                    r++;
                }
            }
        }
        if(lng>0||lat>0)
        {
            //se encontró
            lng=lng/r;
            lat=lat/r;
            return new Clocation(lng,lat,0);
        }
        else
        {
            return null;
        }
    }

 }
