package aaaaa.p.eventmanager.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import aaaaa.p.eventmanager.Activities.MainActivity;
import aaaaa.p.eventmanager.R;
import aaaaa.p.eventmanager.API.Servidor;
import aaaaa.p.eventmanager.Modelos.Usuarios;
import aaaaa.p.eventmanager.Utilidades;
import aaaaa.p.eventmanager.API.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment1 extends Fragment {
    private EditText editTextUser;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Usuarios usuarios;
    private SharedPreferences preferencias;
    public String user;
    public String pass;
    private Context context;

    public LoginFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_fragment1, container, false);

        editTextUser = (EditText) view.findViewById(R.id.editTextUsuario);
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        buttonLogin = (Button) view.findViewById(R.id.buttonLogin);
        context = view.getContext();

        preferencias = this.getActivity().getSharedPreferences("Preferencias", Context.MODE_PRIVATE);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leerCampos();
                if (login(user, pass)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.INTERNET}, 100);
                    } else {
                        if (checkedPermission(Manifest.permission.INTERNET)) {
                            select();
                        }
                    }
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void leerCampos() {
        user = editTextUser.getText().toString();
        pass = editTextPassword.getText().toString();
    }

    private void select() {
        Servidor servicio = API.geApi().create(Servidor.class);
        Call<Usuarios> usuarioCall = servicio.consultarUsuario(user, pass);

        usuarioCall.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                usuarios = response.body();
                if (usuarios != null && usuarios.getUser() != null) {
                    Toast.makeText(context, "Sesión iniciada.", Toast.LENGTH_LONG).show();
                    Utilidades.guardarPreferencias(preferencias, usuarios.getIdUser(), user, pass, usuarios.getName(), usuarios.getLast2(),
                            usuarios.getLast1(), usuarios.getMail());
                    Utilidades.guardarFecha(preferencias, usuarios.getDay(), usuarios.getMonth(), usuarios.getYear());
                    pasarAlMain();
                } else {
                    Toast.makeText(context, "Error: usuario o contraseña incorrectos.\nInténtelo de nuevo.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                Toast.makeText(context, "Error en la conexión.\nInténtelo de nuevo.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean login(String usuario, String password) {
        if (TextUtils.isEmpty(usuario)) {
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(context, "Ingrese usuario y contraseña...", Toast.LENGTH_LONG).show();
                return false;
            } else {
                Toast.makeText(context, "Ingrese usuario.", Toast.LENGTH_LONG).show();
                return false;
            }
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Ingrese contraseña.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private void pasarAlMain() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private Boolean checkedPermission(String permission) {
        int result = context.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 100) {
            String permission = permissions[0];
            int result = grantResults[0];

            if (permission.equals(Manifest.permission.INTERNET)) {
                if (result == PackageManager.PERMISSION_GRANTED) {
                    select();
                } else {
                    Toast.makeText(context, "Rechazaste el acceso", Toast.LENGTH_LONG).show();
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
