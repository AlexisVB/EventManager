package aaaaa.p.eventmanager.Maping;

import android.support.annotation.ArrayRes;

import java.util.ArrayList;

import aaaaa.p.eventmanager.BackEnd.CAp;
import aaaaa.p.eventmanager.BackEnd.CContainer;
import aaaaa.p.eventmanager.BackEnd.CDevice;
import aaaaa.p.eventmanager.BackEnd.Clocation;

/**
 * Created by Alexis on 05/04/2018.
 */

public class CFilter {

    private CContainer actual;
    //Lista para buscar tus dispositivos
    public CFilter(CContainer actual)
    {
        this.actual=actual;
    }
    public ArrayList<Clocation> SearchDevices(ArrayList<CDevice> dispositivos)
    {
        ArrayList<Clocation> puntos= new ArrayList<Clocation>();
        for(int i=0;i<dispositivos.size();i++)
        {
            puntos.add(SearchByMac(dispositivos.get(i).getClientMac()));
        }
        return  puntos;
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
                    lng+=l.ConvertLng();
                    lat+=l.ConvertLat();
                    r++;
                }
            }
        }
        if(lng>0||lat>0)
        {
            //se encontró
            lng=lng/r;
            lat=lat/r;
            return new Clocation(Double.toString(lng),Double.toString(lat),Double.toString(0));
        }
        else
        {
            return null;
        }
    }
    public ArrayList<CDevice> SearchByLocation(int cant,String lat,String lng)
    {
        ArrayList<CDevice> dispositivos= new ArrayList<CDevice>();
        for(int i=0;i<actual.getUpdate_set().size();i++)//recorrer Aps
        {
            CAp current= actual.getUpdate_set().get(i);
            for(int cont=0;cont<current.getObservations().size();cont++)//recorrer dispositivos en Aps
            {

                CDevice curDevice=current.getObservations().get(cont);
                Clocation pos= curDevice.getLocation();
                if(pos.getLat().contains(lat)&&pos.getLng().contains(lng))
                {
                    dispositivos.add(curDevice);
                    cant--;
                    if(cant==0)
                        return dispositivos;
                }
            }
        }
        return dispositivos;
    }
}
