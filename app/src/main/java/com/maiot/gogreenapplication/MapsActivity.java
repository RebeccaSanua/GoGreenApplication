package com.maiot.gogreenapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    Location currentLocation;
    FusedLocationProviderClient fusedClient;
    private static final int REQUEST_CODE = 101;

    FrameLayout map;
    ImageView ShowHome_maps;
    ImageView ShowScanner_maps;
    ImageView ShowMap_maps;

    private GoogleMap googleMap;
    private CircleOptions circleOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        map=findViewById(R.id.map);

        fusedClient= LocationServices.getFusedLocationProviderClient(this);
        getLocation();
        ShowHome_maps = findViewById(R.id.ShowHome_maps);
        ShowScanner_maps = findViewById(R.id.ShowScanner_maps);
        ShowMap_maps = findViewById(R.id.ShowMap_maps);

        final AnimatorSet clickAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.click_animation);

        ShowHome_maps.setOnClickListener(view -> {
            ShowHome_maps.setPivotX(ShowHome_maps.getWidth() / 2);
            ShowHome_maps.setPivotY(ShowHome_maps.getHeight() / 2);
            clickAnimation.setTarget(ShowHome_maps);
            clickAnimation.start();
            Intent intent = new Intent(MapsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ShowScanner_maps.setOnClickListener(view -> {
            ShowScanner_maps.setPivotX(ShowScanner_maps.getWidth() / 2);
            ShowScanner_maps.setPivotY(ShowScanner_maps.getHeight() / 2);
            clickAnimation.setTarget(ShowScanner_maps);
            clickAnimation.start();
            Intent intent = new Intent(MapsActivity.this, ScannerActivity.class);
            startActivity(intent);
        });

        ShowMap_maps.setOnClickListener(view -> {
            Intent intent = new Intent(MapsActivity.this, MapsActivity.class);
            startActivity(intent);
        });
    }

    private void getLocation(){
        if(ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        circleOptions = new CircleOptions()
                .center(latLng)
                .radius(10) // Imposta il raggio del cerchio in metri
                .strokeWidth(15) // Imposta la larghezza del bordo del cerchio
                .strokeColor(Color.WHITE) // Imposta il colore del bordo del cerchio
                .fillColor(Color.rgb(126, 217, 87)); // Imposta il colore di riempimento del cerchio

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        googleMap.addCircle(circleOptions);

        googleMap.setOnCameraIdleListener(this);
    }

    @Override
    public void onCameraIdle() {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        float zoom = googleMap.getCameraPosition().zoom;
        double radius = calculateRadius(zoom);
        circleOptions.radius(radius);
        googleMap.clear();
        googleMap.addCircle(circleOptions);
        addMarkers();
    }
    private void addMarkers() {
        LatLng position1 = new LatLng(44.4048, 8.9348);
        LatLng position2 = new LatLng(44.4020, 8.9673);
        LatLng position3 = new LatLng(44.4028, 8.9429);
        LatLng position4 = new LatLng(44.4046, 8.9599);
        LatLng position5 = new LatLng(44.4174, 8.9336);
        LatLng position6 = new LatLng(44.4071, 8.9566);
        LatLng position7 = new LatLng(44.3977, 8.9336);
        LatLng position8 = new LatLng(44.4111, 8.8959);

        int width = 130;
        int height = 280;

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.immagine_marker);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, false);

        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromBitmap(scaledBitmap);

        MarkerOptions markerOptions1 = new MarkerOptions()
                .position(position1)
                .icon(markerIcon);

        MarkerOptions markerOptions2 = new MarkerOptions()
                .position(position2)
                .icon(markerIcon);

        MarkerOptions markerOptions3 = new MarkerOptions()
                .position(position3)
                .icon(markerIcon);

        MarkerOptions markerOptions4 = new MarkerOptions()
                .position(position4)
                .icon(markerIcon);

        MarkerOptions markerOptions5 = new MarkerOptions()
                .position(position5)
                .icon(markerIcon);

        MarkerOptions markerOptions6 = new MarkerOptions()
                .position(position6)
                .icon(markerIcon);

        MarkerOptions markerOptions7 = new MarkerOptions()
                .position(position7)
                .icon(markerIcon);

        MarkerOptions markerOptions8 = new MarkerOptions()
                .position(position8)
                .icon(markerIcon);


        Marker marker1 = googleMap.addMarker(markerOptions1);
        googleMap.addMarker(markerOptions2);
        googleMap.addMarker(markerOptions3);
        googleMap.addMarker(markerOptions4);
        googleMap.addMarker(markerOptions5);
        googleMap.addMarker(markerOptions6);
        googleMap.addMarker(markerOptions7);
        googleMap.addMarker(markerOptions8);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            LinearLayout tendinaCar = findViewById(R.id.tendinaCar);
            Button buttonShowCar = findViewById(R.id.buttonShowCar);
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.equals(marker1)) {
                    if (buttonShowCar.getVisibility() == View.VISIBLE && tendinaCar.getVisibility() == View.GONE) {
                        buttonShowCar.setVisibility(View.GONE);
                        tendinaCar.setVisibility(View.VISIBLE);
                        return false;
                    }
                    else{
                        buttonShowCar.setVisibility(View.VISIBLE);
                        tendinaCar.setVisibility(View.GONE);
                        return false;
                    }
                }
                return false;
            }
        });

        ImageView closeTendina = findViewById(R.id.closeTendina);

        closeTendina.setOnClickListener(new View.OnClickListener() {
            LinearLayout tendinaCar = findViewById(R.id.tendinaCar);
            Button buttonShowCar = findViewById(R.id.buttonShowCar);
            @Override
            public void onClick(View v) {
                buttonShowCar.setVisibility(View.VISIBLE);
                tendinaCar.setVisibility(View.GONE);
            }
        });

    }
    private double calculateRadius(float zoom) {
        // Calcola il raggio in base allo zoom
        // Esempio di implementazione: il raggio diminuisce gradualmente con l'aumentare dello zoom
        return 10 * Math.pow(2, (18 - zoom)/1.3);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation();
            }
        }
    }
}