/**
 * Created by Pauly Jenkins on 26/06/2017.
 */
package fr.cesi.bar.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.raizlabs.android.dbflow.co
import java.util.HashMap;
import java.util.List;

import fr.cesi.base.database.Bar;
import fr.cesi.base.database.BarController;
import fr.cesi.basecode.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private View mBottomSheet;
    private BottomSheetLayout mBottomSheetLayout;

    private TextView mBarName;

    List<Bar> mBars;
    HashMap<Marker, Bar> mMarkerBars = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mBottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);

        mBottomSheet = LayoutInflater.from(this)
        .inflate(R.layout.view_bottom_sheet, mBottomSheetLayout, false);

        mBarName = (TextView) mBottomSheet.findViewById(R.id.bar_name);


        //
        // Button button1 = (Button) findViewById( R.id.button_1 );
        //button1.setOnClickListener(this);
        //mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        View bottomSheet = findViewById( R.id.bottom_sheet );
//        Button button1 = (Button) findViewById( R.id.button_1 );
//
//        button1.setOnClickListener(this);
//
//        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
//    }


    @Override
    protected void onResume() {
        super.onResume();


        int access_fine_location = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int access_coarce_location = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (access_coarce_location != PackageManager.PERMISSION_GRANTED ||
                access_fine_location != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    1337); //here, request callback int to 1337 - used when manage response from call
        } else {
            //here we can manage maps start
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);

            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                onMarkerClickListener(marker);
                // return false;
                return true;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(44.838578, -0.581482))
                .title("Connemara Irish Pub")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.appmark))
        );

    }

    private void onMarkerClickListener(Marker marker) {

        Bar bar = mMarkerBars.get(marker);
        mBottomSheetLayout.showWithSheetView(mBottomSheet);

        mBarName.setText("...");
    }

}