package com.example.projetoicompra.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projetoicompra.BD.ICompraRepositorio;
import com.example.projetoicompra.BD.ICompraViewModel;
import com.example.projetoicompra.R;
import com.example.projetoicompra.model.Local_Compra;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class ViewMapasActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;


    private ICompraViewModel iCompraViewModel;
    private ICompraRepositorio repositorio;
    private List<Local_Compra> local_compras = new ArrayList<>();
    int tamanho = 555;
    public Activity essaactivity;

    public void setLocaisCompra(List<Local_Compra> local_compras) {
        this.local_compras = local_compras;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mapas);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Mapa de Compras");
        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        essaactivity = this;

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*// Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        //Objeto responsavel por gerenciar a localização do usuario
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Double latitudeusuario = location.getLatitude();
                Double longitudeusuario = location.getLongitude();

                mMap.clear();
                LatLng localusuario = new LatLng(latitudeusuario, longitudeusuario);
                mMap.addMarker(new MarkerOptions().position(localusuario).title("Você está aqui"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localusuario, 15));
                definirPontosCompra();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                //Toast.makeText(ViewMapasActivity.this, "Por Favor ative a localização", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder ativarloc = new AlertDialog.Builder(essaactivity)
                        .setTitle("Serviço de Localização")
                        .setMessage("Por favor ative o serviço de localização")
                        .setPositiveButton("Ativar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);

                            }
                        });

                AlertDialog dialog = ativarloc.create();
                dialog.show();

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    10,
                    locationListener
            );


        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_mapa, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fechartelamapa:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void definirPontosCompra(){
        iCompraViewModel = ViewModelProviders.of(this).get(ICompraViewModel.class);
        iCompraViewModel.getVm_TodoLocalCompra().observe(this, new Observer<List<Local_Compra>>() {
            @Override
            public void onChanged(List<Local_Compra> local_compra) {

                if (local_compra.size() >= 0) {

                    for (int i = 0; i < local_compra.size(); i++) {

                        Double latitudecompra = Double.parseDouble(local_compra.get(i).getLatitude());
                        Double longitudecompra = Double.parseDouble(local_compra.get(i).getLongitude());
                        //Toast.makeText(getApplicationContext(), "lat "+local_compra.get(0).getLatitude()+" long: "+local_compra.get(0).getLongitude(), Toast.LENGTH_SHORT).show();

                        LatLng marcarLocal = new LatLng(latitudecompra, longitudecompra);
                        mMap.addMarker(new MarkerOptions().position(marcarLocal).title(local_compra.get(i).getRazao_social()));

                    }
                }
            }
        });
    }
}

