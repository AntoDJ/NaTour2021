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

public class addToPlaylistOverlay extends Fragment {

    private PlaylistViewModel playlistViewModel;
    private FragmentPlaylistBinding binding;

    public addToPlaylistOverlay() {
        // Required empty public constructor
    }

    public static addToPlaylistOverlay newInstance() {
        addToPlaylistOverlay fragment = new addToPlaylistOverlay();
        Bundle args = new Bundle();;
        fragment.setArguments(args);
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