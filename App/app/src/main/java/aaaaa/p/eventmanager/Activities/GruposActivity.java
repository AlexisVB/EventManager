package aaaaa.p.eventmanager.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.List;

import aaaaa.p.eventmanager.API.API;
import aaaaa.p.eventmanager.API.Servidor;
import aaaaa.p.eventmanager.Adapters.GruposAdapter;
import aaaaa.p.eventmanager.Fragments.QRFragment;
import aaaaa.p.eventmanager.Modelos.Grupo;
import aaaaa.p.eventmanager.Modelos.ListaGrupos;
import aaaaa.p.eventmanager.R;
import aaaaa.p.eventmanager.Utilidades;
import aaaaa.p.eventmanager.VariablesGlobales;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GruposActivity extends AppCompatActivity {

    private ListView listViewGrupos;
    private EditText editTextNameGroup;
    private Button buttonCrearGrupo;
    private Button buttonUnirseAGrupo;
    private SharedPreferences preferencias;
    private AlertDialog dialogQR;

    private ListaGrupos lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);

        bindUI();

        preferencias = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);

        buttonCrearGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });

        buttonUnirseAGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog().show();
            }
        });

        getGroups();



    }

    public AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GruposActivity.this);

        LayoutInflater inflater = GruposActivity.this.getLayoutInflater();

        View v = inflater.inflate(R.layout.fragment_qr, null);

        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(generarCodigo(), BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) v.findViewById(R.id.imageViewQR)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        builder.setView(v)

        .setPositiveButton("Terminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    private String generarCodigo(){
        String code;
        code = Integer.toString(Utilidades.leerPreferenciaId(preferencias)) + Utilidades.leerPreferenciaNombre(preferencias);
        return code;
    }

    private void bindUI(){
        buttonCrearGrupo = (Button) findViewById(R.id.buttonCrearGrupo);
        buttonUnirseAGrupo = (Button) findViewById(R.id.buttonUnirseAGrupo);
        editTextNameGroup = (EditText) findViewById(R.id.editTextNameGroup);
        listViewGrupos = (ListView) findViewById(R.id.listViewGroups);
    }

    private boolean validar(){
        if(!editTextNameGroup.getText().toString().equals("")){
            insert();
            return true;
        }
        else {
            Toast.makeText(GruposActivity.this, "Ingresa nombre o asunto del grupo",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void insert() {
        Thread peticion= new Thread(new Runnable() {
            @Override
            public void run() {
                Servidor servicio = API.geApi().create(Servidor.class);
                Call<Grupo> grupoCall = servicio.insertarGrupo(editTextNameGroup.getText().toString(), Utilidades.leerPreferenciaId(preferencias));

                grupoCall.enqueue(new Callback<Grupo>() {
                    @Override
                    public void onResponse(Call<Grupo> call, Response<Grupo> response) {
                        VariablesGlobales.grupo = response.body();
                        if (VariablesGlobales.grupo != null) {
                            Toast.makeText(GruposActivity.this, "Usuario creado con éxito.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(GruposActivity.this, CrearGrupoActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(GruposActivity.this, "Error: usuario no creado.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Grupo> call, Throwable t) {
                        Toast.makeText(GruposActivity.this, "Error en la conexión.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        peticion.start();

    }

    private void getGroups() {
        Thread peticion= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Servidor servicio = API.geApi().create(Servidor.class);
                    Call<ListaGrupos> grupoCall = servicio.gruposPropietario(Utilidades.leerPreferenciaId(preferencias));
                    Response<ListaGrupos> response = grupoCall.execute();
                    lista = response.body();
                    if (lista != null) {
                        listViewGrupos.post(new Runnable() {
                            @Override
                            public void run() {
                                GruposAdapter adapter = new GruposAdapter(GruposActivity.this, R.layout.items_grupos, lista);
                                listViewGrupos.setAdapter(adapter);
                            }
                        });
                    } else {
                        Toast.makeText(GruposActivity.this, "Error: usuario no creado.", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){
                    Log.v("Err","Fuk2");

                }
            }
        });
        peticion.start();

    }



}
