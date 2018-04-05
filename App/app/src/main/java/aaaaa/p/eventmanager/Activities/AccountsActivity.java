package aaaaa.p.eventmanager.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import aaaaa.p.eventmanager.Fragments.DatePickerFragment;
import aaaaa.p.eventmanager.R;
import aaaaa.p.eventmanager.API.Servidor;
import aaaaa.p.eventmanager.Modelos.Usuarios;
import aaaaa.p.eventmanager.Utilidades;
import aaaaa.p.eventmanager.API.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText editTextUser;
    private EditText editTextName;
    private EditText editTextLast1;
    private EditText editTextLast2;
    private EditText editTextDate;
    private EditText editTextEmail;
    private EditText editTextPass;
    private EditText editTextPass2;
    private EditText editTextPhone;
    private Button buttonAction;

    public String user;
    public String name;
    public String last1;
    public String last2;
    public int dia;
    public int mes;
    public int anio;
    public String email;
    public String pass;
    public String phone;

    private SharedPreferences preferencias;

    private Usuarios usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        preferencias = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
        enlazarUI();

        setToolbar();

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        buttonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validaciones())
                    insert();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void enlazarUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextLast1 = (EditText) findViewById(R.id.editTextLast1);
        editTextLast2 = (EditText) findViewById(R.id.editTextLast2);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        editTextPass2 = (EditText) findViewById(R.id.editTextPass2);
        buttonAction = (Button) findViewById(R.id.buttonAction);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
    }

    private void leerCampos() {
        user = editTextUser.getText().toString();
        name = editTextName.getText().toString();
        last1 = editTextLast1.getText().toString();
        last2 = editTextLast2.getText().toString();
        email = editTextEmail.getText().toString();
        pass = editTextPass.getText().toString();
        phone = editTextPhone.getText().toString();
    }

    private void setToolbar() {

        Boolean session = Utilidades.leerPreferenciaSession(preferencias);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registro de usuarios");

    }

    private void showDatePickerDialog() {
        DatePickerFragment fragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                editTextDate.setText(selectedDate);
                dia = day;
                mes = month + 1;
                anio = year;
                Utilidades.guardarFecha(preferencias, dia, mes, anio);
            }

        });
        fragment.show(this.getFragmentManager(), "datePicker");
    }

    private boolean validaciones() {
        if (validacion(editTextUser) && validacion(editTextName) && validacion(editTextLast1) && validacion(editTextLast2)
                && validacion(editTextDate) && validacion(editTextEmail) && validacion(editTextPass) && validacion(editTextPass2))
            if (validEmail())
                if (validPass())
                    if (Utilidades.passwords(editTextPass.getText().toString(), editTextPass2.getText().toString()) == 1) {
                        Toast.makeText(this, "Registrando usuario...", Toast.LENGTH_LONG).show();
                        return true;
                    } else {
                        Toast.makeText(this, "Las contraseñas no coinciden.\nInténtelo de nuevo.", Toast.LENGTH_LONG).show();
                        return false;
                    }
                else {
                    Toast.makeText(this, "Contraseña demasiado corta.\nInténtelo de nuevo.", Toast.LENGTH_LONG).show();
                    return false;
                }
            else {
                Toast.makeText(this, "Correo inválido.\nInténtelo de nuevo.", Toast.LENGTH_LONG).show();
                return false;
            }
        else {
            Toast.makeText(this, "Todos los campos deben ser llenados.\nIntentalo de nuevo.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean validacion(EditText editText) {
        String et = editText.getText().toString();
        if (!TextUtils.isEmpty(et)) {
            return true;
        } else return false;
    }

    private boolean validEmail() {
        String email = editTextEmail.getText().toString();
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validPass() {
        String pass = editTextPass.getText().toString();
        return pass.length() >= 5;
    }

    private void insert() {
        leerCampos();
        Servidor servicio = API.geApi().create(Servidor.class);
        Call<Usuarios> usuarioCall = servicio.insertarUsuario(user, pass, name, last1, last2, email, dia, mes, anio, phone);

        usuarioCall.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                usuarios = response.body();
                if (usuarios != null) {
                    Toast.makeText(AccountsActivity.this, "Usuario creado con éxito.", Toast.LENGTH_LONG).show();
                    login();
                } else {
                    Toast.makeText(AccountsActivity.this, "Error: usuario no creado.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                Toast.makeText(AccountsActivity.this, "Error en la conexión.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void login() {
        Utilidades.guardarPreferencias(preferencias, usuarios.getIdUser(), user, pass, name, last2, last1, email);
        Intent intent = new Intent(AccountsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
