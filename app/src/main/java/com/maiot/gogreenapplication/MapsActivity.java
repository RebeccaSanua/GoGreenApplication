package com.maiot.gogreenapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.Manifest;
import android.os.Bundle;
import android.service.autofill.VisibilitySetterAction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.maiot.gogreenapplication.SessionManager;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    Location currentLocation;
    FusedLocationProviderClient fusedClient;
    private static final int REQUEST_CODE = 101;

    FrameLayout map;
    ImageView ShowHome_maps;
    ImageView ShowScanner_maps;
    ImageView ShowMap_maps;
    Button openScanner;

    TextView textScanner;

    // Dichiarazione delle variabili di istanza per i marker
    private Marker marker1;
    private Marker marker2;
    private Marker marker3;
    private Marker marker4;
    private Marker marker5;
    private Marker marker6;
    private Marker marker7;
    private Marker marker8;

    //codice univoco QRcode auto
    private String univocoTendina;
    private GoogleMap googleMap;
    private CircleOptions circleOptions;
    private LinearLayout tendinaCar;
    private Button buttonShowCar;

    private Button prenota;
    private Button scanner;

    private Button terminaNoleggio;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        tendinaCar = findViewById(R.id.tendinaCar);
        buttonShowCar = findViewById(R.id.buttonShowCar);
        prenota = findViewById(R.id.prenota);
        scanner = findViewById(R.id.openScanner);
        terminaNoleggio = findViewById(R.id.TerminaNoleggio);
        sharedPreferences = getSharedPreferences("MarkerData", Context.MODE_PRIVATE);

        map=findViewById(R.id.map);

        fusedClient= LocationServices.getFusedLocationProviderClient(this);
        getLocation();
        openScanner = findViewById(R.id.openScanner);
        ShowHome_maps = findViewById(R.id.ShowHome_maps);
        ShowScanner_maps = findViewById(R.id.ShowScanner_maps);
        ShowMap_maps = findViewById(R.id.ShowMap_maps);

        final AnimatorSet clickAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.click_animation);
        terminaNoleggio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.endSession();
            }
        });

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

        openScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MapsActivity.this);
                intentIntegrator.setPrompt("Scannerizza il QR code");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
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


        marker1 = googleMap.addMarker(markerOptions1);
        marker2 = googleMap.addMarker(markerOptions2);
        marker3 = googleMap.addMarker(markerOptions3);
        marker4 = googleMap.addMarker(markerOptions4);
        marker5 = googleMap.addMarker(markerOptions5);
        marker6 = googleMap.addMarker(markerOptions6);
        marker7 = googleMap.addMarker(markerOptions7);
        marker8 = googleMap.addMarker(markerOptions8);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.equals(marker1)) {
                    tendinaCar = findViewById(R.id.tendinaCar);
                    buttonShowCar = findViewById(R.id.buttonShowCar);
                    prenota = findViewById(R.id.prenota);
                    scanner = findViewById(R.id.openScanner);
                    terminaNoleggio = findViewById(R.id.TerminaNoleggio);
                    if (buttonShowCar.getVisibility() == View.VISIBLE && tendinaCar.getVisibility() == View.GONE) {
                        buttonShowCar.setVisibility(View.GONE);
                        tendinaCar.setVisibility(View.VISIBLE);
                        univocoTendina = getUnivocoTendinaByMarker(marker1);
                        SharedPreferences sharedPreferences = getSharedPreferences("MarkerData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("univocoTendina", univocoTendina);
                        editor.apply();
                        return false;
                    } else{
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

            @Override
            public void onClick(View v) {
                tendinaCar = findViewById(R.id.tendinaCar);
                buttonShowCar = findViewById(R.id.buttonShowCar);
                buttonShowCar.setVisibility(View.VISIBLE);
                tendinaCar.setVisibility(View.GONE);
            }
        });

    }
    private String getUnivocoTendinaByMarker(Marker marker) {
        if (marker.equals(marker1)) {
            return "car0001";
        } else if (marker.equals(marker2)) {
            return "valore univoco del marker2";
        } else if (marker.equals(marker3)) {
            return "valore univoco del marker3";
        } else if (marker.equals(marker4)) {
            return "valore univoco del marker4";
        } else if (marker.equals(marker5)) {
            return "valore univoco del marker5";
        } else if (marker.equals(marker6)) {
            return "valore univoco del marker6";
        } else if (marker.equals(marker7)) {
            return "valore univoco del marker7";
        } else if (marker.equals(marker8)) {
            return "valore univoco del marker8";
        }

        return ""; // Valore predefinito nel caso in cui il marker non corrisponda a nessuno dei casi sopra
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null){
            SharedPreferences sharedPreferences = getSharedPreferences("MarkerData", Context.MODE_PRIVATE);
            String univocoTendina = sharedPreferences.getString("univocoTendina", "");
            String contents = intentResult.getContents();
            TextView textScanner = findViewById(R.id.textScanner);
            if (contents.equals(univocoTendina)) {
                // Sovrascrivi il TextView con l'ID "textScanner" con la frase "La prenotazione è avvenuta con successo!"
                textScanner.setText("La prenotazione è avvenuta con successo!");
                // Passaggio delle variabili a SessionManager
                SessionManager.setTendinaCar(tendinaCar);
                SessionManager.setButtonShowCar(buttonShowCar);
                SessionManager.setScannerCar(openScanner);
                SessionManager.setPrenotaCar(prenota);
                SessionManager.setTerminaNoleggio(terminaNoleggio);
                SessionManager.startSession();

            } else {
                 /* TODO: spostare il testo in alto e inserire kilometraggio e timer*/
                textScanner.setText("Sembrano non corrispondere i codici, riprova!");
                // Il codice scannerizzato non corrisponde al valore univoco della tendina
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
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