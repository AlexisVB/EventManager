package aaaaa.p.eventmanager.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import aaaaa.p.eventmanager.Activities.AccountsActivity;
import aaaaa.p.eventmanager.PageAdapter;
import aaaaa.p.eventmanager.PageAdapter1;
import aaaaa.p.eventmanager.R;
import aaaaa.p.eventmanager.Utilidades;

public class LoginActivity extends FragmentActivity {

    private TextView textViewForgot;
    private SharedPreferences preferencias;
    private TextView textViewSingin;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enlazarUI();
        preferencias = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);

        credencialesExistentes();
        Utilidades.session(preferencias, false);

        textViewSingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarAlRegistro();
            }
        });

    }

    private void enlazarUI() {
        textViewSingin = (TextView) findViewById(R.id.textViewSignin);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private void pasarAlRegistro() {
        Intent intent = new Intent(this, AccountsActivity.class);
        startActivity(intent);
    }

    private void credencialesExistentes() {
        String usuario = Utilidades.leerPreferenciaUsuario(preferencias);
        String password = Utilidades.leerPreferenciaPassword(preferencias);
        if (!TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(password)) {
            PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(0);
        } else {
            PageAdapter1 adapter1 = new PageAdapter1(getSupportFragmentManager());
            viewPager.setAdapter(adapter1);
        }
    }
}
