package Search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.natour2021.R;
import com.example.natour2021.databinding.FragmentPlaylistBinding;

import java.util.ArrayList;

import Controller.Controller;
import Login.ui.playlist.PlaylistViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addToPlaylistOverlay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addToPlaylistOverlay extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private PlaylistViewModel playlistViewModel;
    private FragmentPlaylistBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addToPlaylistOverlay() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addToPlaylistOverlay.
     */
    // TODO: Rename and change types and number of parameters
    public static addToPlaylistOverlay newInstance(String param1, String param2) {
        addToPlaylistOverlay fragment = new addToPlaylistOverlay();
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
        view=inflater.inflate(R.layout.fragment_add_to_playlist_overlay, container, false);

        ListView PlaylistList = (ListView) view.findViewById(R.id.overlayPlaylistView);
        ArrayList<String> playlist = new ArrayList<String>();
        playlist.add("Sentieri preferiti");
        playlist.add("Sentieri da visitare");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, playlist);
        PlaylistList.setAdapter(arrayAdapter);
        PlaylistList.setOnItemClickListener((adapterView, view1, i, l) -> {
            Controller c = Controller.getInstance();
            ((DetailView)getActivity()).addToPlaylist(PlaylistList.getItemAtPosition(i).toString());
            c.cleanFragment(getActivity().findViewById(R.id.detailOverlayContainer));
        });
        Button backButton= (Button) view.findViewById(R.id.backButton);
        backButton.setOnClickListener(view1 -> {
            Controller c = Controller.getInstance();
            c.cleanFragment(getActivity().findViewById(R.id.detailOverlayContainer));

        });
        return view;

    }
}