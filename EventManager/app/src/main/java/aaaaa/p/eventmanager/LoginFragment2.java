package aaaaa.p.eventmanager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment2 extends Fragment {
    private Button buttonSess;
    private Button buttonDelete;

    private Usuarios usuarios;
    private Context context;
    private SharedPreferences preferencias;

    public LoginFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_fragment2, container, false);

        buttonSess = (Button) view.findViewById(R.id.buttonSess);
        buttonDelete = (Button) view.findViewById(R.id.buttonDelete);
        context = view.getContext();

        preferencias = this.getActivity().getSharedPreferences("Preferencias", Context.MODE_PRIVATE);

        String sess = "Iniciar sesión como " + Utilidades.leerPreferenciaUsuario(preferencias);
        buttonSess.setText(sess);

        buttonSess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.INTERNET}, 100);
                } else {
                    if (checkedPermission(Manifest.permission.INTERNET)) {
                        select();
                    }
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDelete();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void select() {
        Servidor servicio = API.geApi().create(Servidor.class);
        Call<Usuarios> usuarioCall = servicio.consultarUsuario(Utilidades.leerPreferenciaUsuario(preferencias), Utilidades.leerPreferenciaPassword(preferencias));

        usuarioCall.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                usuarios = response.body();
                if (usuarios != null && usuarios.getUser() != null) {
                    Toast.makeText(context, "Sesión iniciada.", Toast.LENGTH_LONG).show();
                    pasarAlMain();
                } else {
                    Toast.makeText(context, "Error con las credenciales o con la conexión.\nInténtelo de nuevo más tarde.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                Toast.makeText(context, "Error en la conexión.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void pasarAlMain() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void alertDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Deberás ingresar tu usuario y contraseña la próxima vez que inicies sesión.");
        builder.setTitle("Atención");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Utilidades.clear(preferencias);
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
