package com.anhtu.hongngoc.findfood.view;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.anhtu.hongngoc.findfood.R;
import com.anhtu.hongngoc.findfood.controller.DanDuongToiQuanAnController;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DanDuongToiQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private double latitude = 0;
    private double longitude = 0;
    SharedPreferences sharedPreferences;
    Location vitrihientai;

    private DanDuongToiQuanAnController danDuongToiQuanAnController;
    String duongdan = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_danduong);

        danDuongToiQuanAnController = new DanDuongToiQuanAnController();

        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);
        Log.d("kiki", latitude + " - " + longitude);

        sharedPreferences = getSharedPreferences("toado", Context.MODE_PRIVATE);
        vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude", "0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude", "0")));

        duongdan = "https://maps.googleapis.com/maps/api/directions/json?origin=20.984529,105.838845&destination=" +latitude + "," + longitude + "&language=vi&key=AIzaSyBt1r1RSw3srdsUF_l5rTskoolsNqBXg1I";

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.clear();
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMinZoomPreference(10);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        LatLng latLng = new LatLng( 20.984529,105.838845);
        Log.d("lati",String.valueOf(vitrihientai.getLatitude()) );

//        vitrihientai.getLatitude(),vitrihientai.getLongitude()
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        googleMap.addMarker(markerOptions);

        LatLng vitriquanan = new LatLng(latitude, longitude);
        MarkerOptions markervitriquanan = new MarkerOptions();
        markervitriquanan.position(vitriquanan);
        googleMap.addMarker(markervitriquanan);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,14);
        googleMap.moveCamera(cameraUpdate);

        danDuongToiQuanAnController.HienThiDanDuongToiQuanAn(googleMap,duongdan);
    }
}
