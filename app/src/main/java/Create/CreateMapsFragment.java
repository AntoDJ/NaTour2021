package Create;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
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

public class CreateMapsFragment extends Fragment {
    private static int i=1;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng latLng= new LatLng(41.8,12.7);
            CameraPosition cameraPosition= new CameraPosition.Builder().target(latLng).zoom(6.0f).build();
            CameraUpdate cameraUpdate= CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.moveCamera(cameraUpdate);
            ArrayList<Marker> coordinateinserite =((CreateView)getActivity()).getCoordinate();
            for(Marker m:coordinateinserite){
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(m.getPosition());
                markerOptions.title(m.getTitle());
                Marker marker =googleMap.addMarker(markerOptions);
                MapViewFragment.addMarker(marker);
            }


            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("Punto "+i++);
                    Marker marker= googleMap.addMarker(markerOptions);
                    MapViewFragment.addMarker(marker);
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
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

    public static void removeCoordinata(Marker coordinata){
        coordinata.remove();
        i--;
    }
}