package aaaaa.p.eventmanager.BackEnd;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Alexis on 04/04/2018.
 */

public interface InterfaceHttp {

    //http://merakiton.ddns.net/data/c01c66ef-73c1-46a6-9cce-4aa055ce9a57
    //url de los datos vergas
    @GET("c01c66ef-73c1-46a6-9cce-4aa055ce9a57")
    Call<CContainer> getUpdates();
}
