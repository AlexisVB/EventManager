package aaaaa.p.eventmanager;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Servidor {

    @GET("insertarUsuario.php")
    Call<Usuarios> insertarUsuario(@Query("usuario") String usuario, @Query("password") String pass, @Query("nombre") String nombre, @Query("apellido_pat")
            String apellido_pat, @Query("apellido_mat") String apellido_mat, @Query("correo") String correo, @Query("dia") int dia, @Query("mes") int mes, @Query("anio") int anio, @Query("phone") String phone);

    @GET("consultarUsuario.php")
    Call<Usuarios> consultarUsuario(@Query("usuario") String usuario, @Query("password") String pass);

    @GET("updatePass.php")
    Call<Usuarios> updatePass(@Query("id") int id, @Query("password") String pass);

    @GET("updateUsuario.php")
    Call<Usuarios> updateUsuario(@Query("id") int id, @Query("usuario") String usuario, @Query("nombre") String nombre, @Query("apellido_pat")
            String apellido_pat, @Query("apellido_mat") String apellido_mat, @Query("correo") String correo, @Query("dia") int dia, @Query("mes") int mes, @Query("anio") int anio, @Query("phone") String phone);

}
