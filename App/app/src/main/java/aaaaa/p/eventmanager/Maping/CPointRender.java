package aaaaa.p.eventmanager.Maping;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import aaaaa.p.eventmanager.BackEnd.Clocation;

/**
 * Created by Alexis on 04/04/2018.
 */

public class CPointRender extends Drawable {
    private Canvas miCanvas;
    private Paint mGreenPaint;
    private Paint mRedPaint;
    private Paint mBluePaint;
    double Width;
    double Height;

    private ArrayList<CPos> ps=new ArrayList<CPos>();
    public CPointRender()
    {
        InicialiarBrushes();
    }
    private void InicialiarBrushes()
    {
        mGreenPaint= new Paint();
        mRedPaint= new Paint();
        mBluePaint= new Paint();
        mGreenPaint.setARGB(200,30,255,16);
        mRedPaint.setARGB(200,255,40,20);
        mBluePaint.setARGB(200,30,30,255);
        Width = 1688;
        Height = 1289;   }
    @Override
    public void draw(Canvas canvas)
    {
        this.miCanvas=canvas;
        // ps.add(new CPos(888,900));
        // ps.add(new CPos(200,1000));
        // Get the drawable's bounds
        for(int i=0;i<ps.size();i++)
        {
            CPos cur=ps.get(i);
            miCanvas.drawCircle(cur.getPosX(),cur.getPosY(),20,mGreenPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        // This method is required
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        // This method is required
    }

    @Override
    public int getOpacity() {
        // Must be PixelFormat.UNKNOWN, TRANSLUCENT, TRANSPARENT, or OPAQUE
        return PixelFormat.OPAQUE;
    }


    public void GraficarAmigos(ArrayList<Clocation> puntos )
    {
        for(int cont=0;cont<puntos.size();cont++)
        {
            Clocation cur= puntos.get(cont);
            if(cur!=null)
            {
                float[] pos=ConvertirLocationToPixels(cur);
                //valdiar pos
                // Draw a red circle in the center
                //si valido rojo
                ps.add(new CPos(pos[0],pos[1]));
                //si no rojo
            }
        }
    }

    public void GraficarMe(Clocation loc)
    {
        float[] pos=ConvertirLocationToPixels(loc);
        //valdiar pos
        // Draw a red circle in the center
        //si valido rojo
        //miCanvas.drawCircle(pos[0],pos[1],20,mGreenPaint);
    }
    //***************************************************************Datos para graficar dentro del área**************************************************************************
    //NOTA: Cualquier punto fuera de esta zona no será gráficado

    //limite izquierdo--------------------límite ziquierdo
    //x=0  lat=20.653951
    double limXIzquierdo=0;
    double limLatIzquierdo=20.653951;
    //limite derecho----------------------límite derecho
    //x=width lat=20.652313
    double limXDerecho=Width;
    double limLatDerecho=20.652313;
    //limite superior---------------------limite superior
    //y=0 lgn=-103.391367
    double limYSuperior=0;
    double limLgnSuperior=-103.391367;
    //limite inferior---------------------limite inferior
    //y=height lgn=-103.391679
    double limYInferior=Height;
    double limLgnInferior=-103.391679;
    //curlat=lat-minLat;------------------se le resta la latitud minimima a la actual
    //convertir la latitud en pixeles
    //factor pixels coordenadas en X
    //rangoX = maxX-minX;------------------se obtiene el rango de longitudes
    //curlat*Width/rangox=pseudolatX-------con regla de 3 se determinan los pixeles que representaen x que representa la latitud
    //latX=Width-pseudolatX;---------------como la longitud mas grande esta a la derecha y a la derecha esta el 0 se invierte ese valor

    //curlgn=lgn-minlgn--------------------se le resta la longitud minima a la longitud actual
    //convertir la longitud en pixeles
    //factor pixels coordenadas en y
    //rangoY=maxY-miny--------------------se obtiene el rango de las longitudes
    //latY=curlgn*Height/rangoY----------con regla de 3 se transforma la longitud a pixel en y

    private float[] ConvertirLocationToPixels(Clocation c)
    {
        if(c!=null)
        {
            float[] pos= new float[2];
            pos[0]=(float) ConvertirLatitud(c.ConvertLat());
            pos[1]=(float)ConvertirLongitud(c.ConvertLng());
            return pos;
        }
        else
        {
            float[] pos= new float[2];
            return pos;
        }

    }
    private double ConvertirLatitud(double latitud)
    {
        double curLat=latitud-limLatDerecho;
        double rangoX=limLatIzquierdo-limLatDerecho;
        double pseudolatX=curLat*Width/rangoX;
        return Width-pseudolatX;
    }
    private double ConvertirLongitud(double longitud)
    {
        double curLgn=longitud+(limLgnSuperior*(-1));
        double rangoY=(limLgnInferior*(-1))-(limLgnSuperior*(-1));
        double latY=curLgn*Height/rangoY;
        return latY*-1;
    }
    //***********************************************************Determinar si un punto esta fuera del edificio********************************************************************
    //***********************************************************Determinar si estoy dentro de algún  área***********************************************************************
}
