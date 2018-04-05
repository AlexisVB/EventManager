package aaaaa.p.eventmanager.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import aaaaa.p.eventmanager.Adapters.GruposAdapter;
import aaaaa.p.eventmanager.Adapters.TrackingAdapter;
import aaaaa.p.eventmanager.Modelos.Tracking;
import aaaaa.p.eventmanager.R;

import static aaaaa.p.eventmanager.VariablesGlobales.tracking;

public class CrearGrupoActivity extends AppCompatActivity {

    private Button buttonAgregarAmigo;
    private ListView listViewIntegrantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);

        bindUI();

        buttonAgregarAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAlScanner();
            }
        });

        if(tracking != null){
            TrackingAdapter adapter = new TrackingAdapter(CrearGrupoActivity.this, R.layout.items_grupos, tracking);
            listViewIntegrantes.setAdapter(adapter);
        }
    }

    private void bindUI(){
        buttonAgregarAmigo = (Button) findViewById(R.id.buttonAgregarAmigo);
        listViewIntegrantes = (ListView) findViewById(R.id.listViewIntegrantes);
    }

    private void irAlScanner() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        }
        if (checkedPermission(Manifest.permission.CAMERA)) {
            Intent intent = new Intent(this, ScannerActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(CrearGrupoActivity.this, "Rechazaste el acceso", Toast.LENGTH_LONG).show();
        }

    }

    private Boolean checkedPermission(String permission) {
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

}
