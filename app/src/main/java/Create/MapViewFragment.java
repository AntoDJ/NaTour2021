package Create;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.natour2021.R;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import Controller.Controller;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapViewFragment extends Fragment {
    private static ArrayList<Marker> coordinate = new ArrayList<Marker>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MapViewFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static MapViewFragment newInstance(String param1, String param2) {
        MapViewFragment fragment = new MapViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        Controller c = Controller.getInstance();
        view = inflater.inflate(R.layout.fragment_map_view, container, false);

        Button annullaPuntoButton = (Button) view.findViewById(R.id.annullaUltimoPuntoButton);
        annullaPuntoButton.setOnClickListener(view1 -> {
            CreateMapsFragment.removeCoordinata(coordinate.get(coordinate.size()-1));
            coordinate.remove(coordinate.size()-1);
        });

        Button insertPathButton = (Button) view.findViewById(R.id.confermaTracciatoButton);
        insertPathButton.setOnClickListener(view1 -> {
            ArrayList<String> temp= new ArrayList<>();
            for(Marker m:coordinate){
                temp.add(m.getPosition().latitude+" "+m.getPosition().longitude);
            }
            ((CreateView)getActivity()).setCoordinate(temp);
            c.cleanFragment(getActivity().findViewById(((ViewGroup)getView().getParent()).getId()));
        });
        return view;
    }

    public static void addMarker(Marker marker){
        coordinate.add(marker);
    }

}