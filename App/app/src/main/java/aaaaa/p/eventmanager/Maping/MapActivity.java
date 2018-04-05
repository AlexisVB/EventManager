package aaaaa.p.eventmanager.Maping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

import aaaaa.p.eventmanager.BackEnd.CContainer;
import aaaaa.p.eventmanager.BackEnd.CDevice;
import aaaaa.p.eventmanager.BackEnd.CGeoUpdater;
import aaaaa.p.eventmanager.BackEnd.CSincronizador;
import aaaaa.p.eventmanager.R;

public class MapActivity extends AppCompatActivity implements Runnable{
    ImageView image;
    public boolean Activo;
    private CSincronizador sincronizer;
    private CGeoUpdater buscador;
    private Thread updater;
    private CContainer actual;
    private boolean seleccionados;
    private ArrayList<CDevice> devsFriends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        image= findViewById(R.id.imageView);
        sincronizer= new CSincronizador();
        seleccionados=false;
        buscador=new CGeoUpdater(sincronizer);//se inicia el hilo buscador
        updater=new Thread(this);
        Activo=true;
        updater.start();//se inicia el hilo graficador
        Log.w("Hi","Oafdd");
    }
    @Override
    public void run()
    {
        while(Activo)
        {
            synchronized (updater)
            {
                if(sincronizer.DatosDisponibles==false)
                {
                    try {
                        updater.wait(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Log.w("Hi","taking..");
                    actual=sincronizer.Quitar();
                    if(seleccionados==false)
                    {
                        Log.w("Hi","Selecting...");
                        TestGrafing();
                        seleccionados=true;
                    }

                    if(devsFriends!=null&&devsFriends.size()>0)
                        ChargePoints();//graficar los puntos con la lista de dispositivos seleccionados
                }

            }
        }
    }

    private void ChargePoints()
    {
        final CPointRender mydrawing = new CPointRender();//creación del renderizador
        CFilter filtro= new CFilter(actual);////creacion del filtro
        mydrawing.GraficarAmigos(filtro.SearchDevices(devsFriends));//busco las localizaciones de mis amigos y las grafico
        image.post(new Runnable() {
            public void run() {
                /* the desired UI update */
                image.setImageDrawable(mydrawing);
            }
        });
    }



    public ArrayList<CDevice> getDispositivos() {
        return devsFriends;
    }

    public void setDispositivos(ArrayList<CDevice> dispositivos) {
        this.devsFriends = dispositivos;
    }
    public void Desactivar()
    {
        Activo=false;
    }

    private void TestGrafing()
    {
        CFilter filtro= new CFilter(actual);
        this.setDispositivos(filtro.SearchByLocation(100,"20.6","-103.3"));
    }

    public void notificar()
    {
        synchronized(updater)
        {
            updater.notifyAll();
        }
    }
}
