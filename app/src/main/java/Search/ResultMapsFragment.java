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

import Controller.Controller;

public class ResultMapsFragment extends Fragment {
    double averageLongitude=0;
    double averageLatitude=0;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            Marker marker = null;
            MarkerOptions markerOptions = new MarkerOptions();
            //prendo tutte le variabili
            ArrayList<String> nomi = ((ResultView)getActivity()).getNomi();
            ArrayList<String> posizioni = ((ResultView)getActivity()).getPosizioni();
            ArrayList<Integer> difficolta = ((ResultView)getActivity()).getDifficolta();
            float[] durate = ((ResultView)getActivity()).getDurate();
            //creo un marker per ogni sentiero
            for(int i=0; i<nomi.size(); i++){
                String parts[]= posizioni.get(i).split(" ", 2);
                LatLng latlng= new LatLng(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
                averageLatitude+=Double.parseDouble(parts[0]);
                averageLongitude+=Double.parseDouble(parts[1]);
                markerOptions.position(latlng);
                markerOptions.title(nomi.get(i));
                if(durate[i]-(int)durate[i]==0.5)   markerOptions.snippet("Durata: " + (int)durate[i] + ":30 ore Difficoltà: " + difficolta.get(i));
                else markerOptions.snippet("Durata: " + (int)durate[i] + " ore Difficoltà: " + difficolta.get(i));
                googleMap.addMarker(markerOptions);

            }
            LatLng latlng = new LatLng(averageLatitude/nomi.size(),averageLongitude/nomi.size());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latlng).zoom(7.0f).build();
            CameraUpdate cameraUpdate= CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.moveCamera(cameraUpdate);
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(@NonNull Marker marker) {
                    Controller c = Controller.getInstance();
                    ((ResultView)getActivity()).markerclicked(marker.getTitle());
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result_maps, container, false);
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