package Search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.natour2021.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class DetailMapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng latLng= new LatLng(41.8,12.7);
            CameraPosition cameraPosition= new CameraPosition.Builder().target(latLng).zoom(6.0f).build();
            CameraUpdate cameraUpdate= CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.moveCamera(cameraUpdate);
            MarkerOptions markerOptions = new MarkerOptions();
            String puntoiniziale = ((DetailView)getActivity()).getPuntoIniziale();
            String part[]= puntoiniziale.split(" ", 2);
            LatLng latlng= new LatLng(Double.parseDouble(part[0]), Double.parseDouble(part[1]));
            markerOptions.position(latlng);
            markerOptions.title("puntoiniziale");
            Marker marker= googleMap.addMarker(markerOptions);
            ArrayList<String> coordinate = ((DetailView)getActivity()).getCoordinate();
            if(!coordinate.isEmpty()){
                for(String s:coordinate){
                    part= s.split(" ", 2);
                    latlng=new LatLng(Double.parseDouble(part[0]), Double.parseDouble(part[1]));
                    markerOptions.position(latlng);
                    markerOptions.title("");
                    marker= googleMap.addMarker(markerOptions);

                }
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}