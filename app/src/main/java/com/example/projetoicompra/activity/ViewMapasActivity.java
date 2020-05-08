package com.example.projetoicompra.activity;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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

    Application application;

    private ICompraViewModel iCompraViewModel;
    private ICompraRepositorio repositorio;
    private List<Local_Compra> local_compras = new ArrayList<>();
    int tamanho=555;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mapas);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        setTitle("Mapa de Compras");

        //repositorio = new ICompraRepositorio(getApplication());
        //local_compras =  repositorio.getRe_TodoLocalCompra().getValue();
        //Local_Compra loc_compra = local_compras.get(0);


        //iCompraViewModel = new ViewModelProvider(this).get(ICompraViewModel.class);
        iCompraViewModel = ViewModelProviders.of(this).get(ICompraViewModel.class);



        iCompraViewModel.getVm_TodoLocalCompra().observe(this, new Observer<List<Local_Compra>>() {
            @Override
            public void onChanged(List<Local_Compra> local_compra) {
                local_compras = local_compra;
                //Toast.makeText(getApplicationContext(), local_compra.get(0).getRazao_social(), Toast.LENGTH_SHORT).show();
            }
        });

        local_compras = iCompraViewModel.getVm_TodoLocalCompra().getValue();

        Local_Compra loc_compra = local_compras.get(0);

        Toast.makeText(this, "Passando por aqui "+loc_compra.getRazao_social(), Toast.LENGTH_SHORT).show();

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

        /*if(local_compras.size() <=0) {
            for (int i = 0; i < local_compras.size(); i++) {

                Double latitudecompra = Double.parseDouble(local_compras.get(i).getLatitude());
                Double longitudecompra = Double.parseDouble(local_compras.get(i).getLongitude());

                LatLng marcarLocal = new LatLng(latitudecompra, longitudecompra);
                mMap.addMarker(new MarkerOptions().position(marcarLocal).title(local_compras.get(i).getRazao_social()));

            }
        }*/




        //Objeto responsavel por gerenciar a localização do usuario
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Double latitudeusuario = location.getLatitude();
                Double longitudeusuario = location.getLongitude();


                // Add a marker in Sydney and move the camera
                //mMap.clear();
                LatLng localusuario = new LatLng(latitudeusuario, longitudeusuario);
                mMap.addMarker(new MarkerOptions().position(localusuario).title("Você está aqui"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localusuario, 15));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    10,
                    locationListener
            );


        }





    }
}

