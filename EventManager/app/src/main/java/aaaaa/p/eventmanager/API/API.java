package aaaaa.p.eventmanager.API;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alexis on 04/04/2018.
 */

public class API {
    public static final String URL_UPDATES="http://merakiton.ddns.net/data/";
    public static Retrofit retrofit=null;
    //public static usuario miusuario;


    public static Context context;
    public static int sizeList1=0;
    public static int sizeList2=0;
    public static boolean sonido;
    public static boolean vibracion;
    public static Retrofit getApiUpdates()
    {
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder()
                    .baseUrl(URL_UPDATES)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
