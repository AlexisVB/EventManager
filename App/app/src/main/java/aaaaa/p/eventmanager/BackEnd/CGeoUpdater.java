package aaaaa.p.eventmanager.BackEnd;

import android.util.Log;


import aaaaa.p.eventmanager.API.API;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexis on 04/04/2018.
 */

public class CGeoUpdater  implements Runnable{
    //lista de dispositivos
    //Busca en los dispositivos en nube

    //avisa que tiene nuevos datos
    public  CContainer actual;
    private CSincronizador sincronizer;
    public boolean Activo;

    private Thread finderThread;
    public CGeoUpdater(CSincronizador s)
    {
        sincronizer=s;
        finderThread= new Thread(this);
        Activo=true;
        finderThread.start();//comenzar la actualizacion
    }
    @Override
    public void run()
    {
        while (Activo)
        {
            synchronized (finderThread)
            {
                if(sincronizer.DatosDisponibles==true)
                {
                    try {
                        finderThread.wait(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(sincronizer.DatosDisponibles==true)
                        return;
                }
                else
                {
                    Log.w("Hi","updating..");
                    Update();
                    try {
                        finderThread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }

    private void Update()
    {
        try
        {
            InterfaceHttp service= API.getApiUpdates().create(InterfaceHttp.class);
            Call<CContainer> updatesCall=service.getUpdates();
            Response<CContainer> response=updatesCall.execute();
            actual=response.body();
            sincronizer.Poner(actual);
            //meter a la cola de actualización
            Log.w("Hi","OK");
            Log.w("Hi","Puting");
            notificar();
            /*
            updatesCall.enqueue((new Callback<CContainer>() {
                @Override
                public void onResponse(Call<CContainer> call, Response<CContainer> response) {
                    actual=response.body();
                    sincronizer.Poner(actual);
                    //meter a la cola de actualización
                    Log.w("Hi","OK");
                    sincronizer.DatosDisponibles=true;
                }
                @Override
                public void onFailure(Call<CContainer> call, Throwable t) {
                    Log.w("Err","Fuk");
                    sincronizer.DatosDisponibles=false;
                }
            }));*/

        }catch (Exception e) {
            Log.w("Err", "Fuk2");
            sincronizer.DatosDisponibles=false;
        }

    }
    public void Desactivar()
    {
        Activo=false;
    }
    public void notificar()
    {
        synchronized(finderThread)
        {
            finderThread.notifyAll();
        }
    }
}
