package aaaaa.p.eventmanager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private static final String baseURL = "http://www.onlineprint.com.mx/EventManager/scripts/";
    private static Retrofit retrofit = null;

    public static Retrofit geApi(){
        if(retrofit==null){
            retrofit= new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
