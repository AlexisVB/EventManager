package aaaaa.p.eventmanager.BackEnd;

import java.util.ArrayList;

/**
 * Created by Alexis on 05/04/2018.
 */

public class CSincronizador {
    public boolean DatosDisponibles;
    public CContainer actual= new CContainer();
    public CSincronizador()
    {
        DatosDisponibles=false;
    }

    public void Poner(CContainer c)
    {
        synchronized (actual)
        {
            DatosDisponibles=true;

            actual=c;
        }

    }
    public CContainer Quitar()
    {
        synchronized (actual)
        {
            DatosDisponibles=false;

            return actual;
        }
    }


}
