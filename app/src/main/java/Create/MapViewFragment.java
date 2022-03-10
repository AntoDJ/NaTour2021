package Create;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.example.natour2021.R;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import Controller.Controller;

public class MapViewFragment extends Fragment {
    private static ArrayList<Marker> coordinate = new ArrayList<Marker>();
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    public MapViewFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static MapViewFragment newInstance(String param1, String param2) {
        MapViewFragment fragment = new MapViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        coordinate.clear();
        Controller c = Controller.getInstance();
        view = inflater.inflate(R.layout.fragment_map_view, container, false);
        buttonClick.setDuration(300);

        Button annullaPuntoButton = (Button) view.findViewById(R.id.annullaUltimoPuntoButton);
        annullaPuntoButton.setOnClickListener(view1 -> {
            view1.startAnimation(buttonClick);
            if(coordinate.size()!=0) {
                CreateMapsFragment.removeCoordinata(coordinate.get(coordinate.size() - 1));
                coordinate.remove(coordinate.size() - 1);
            }
        });

        Button insertPathButton = (Button) view.findViewById(R.id.confermaTracciatoButton);
        insertPathButton.setOnClickListener(view1 -> {
            view1.startAnimation(buttonClick);
            for(Marker m:coordinate)  Log.i("prova",m.getPosition().toString());
            ((CreateView) getActivity()).setCoordinate(coordinate);
            c.cleanFragment(getActivity().findViewById(((ViewGroup)getView().getParent()).getId()));
        });

        Button selectGPXButton = (Button) view.findViewById(R.id.insertFileGPXButton);
        selectGPXButton.setOnClickListener(view1 -> {
            view1.startAnimation(buttonClick);
            ((CreateView)getActivity()).GPX();
        });

        return view;
    }

    public static void addMarker(Marker marker){
        coordinate.add(marker);
    }
}