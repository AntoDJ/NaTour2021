package Search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.natour2021.R;
import com.example.natour2021.databinding.FragmentPlaylistBinding;

import java.util.ArrayList;

import Controller.Controller;
import Login.ui.playlist.PlaylistViewModel;

public class addToPlaylistFragment extends Fragment {
    private long mLastClickTime = 0;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    private String namePath;

    private PlaylistViewModel playlistViewModel;
    private FragmentPlaylistBinding binding;

    public addToPlaylistFragment() {
        // Required empty public constructor
    }

    public static addToPlaylistFragment newInstance() {
        addToPlaylistFragment fragment = new addToPlaylistFragment();
        Bundle args = new Bundle();;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            namePath=getArguments().getString("nomeSentiero");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.fragment_add_to_playlist, container, false);

        ListView PlaylistList = (ListView) view.findViewById(R.id.overlayPlaylistView);
        ArrayList<String> playlist = new ArrayList<String>();
        playlist.add("Sentieri preferiti");
        playlist.add("Sentieri da visitare");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, playlist);
        PlaylistList.setAdapter(arrayAdapter);
        PlaylistList.setOnItemClickListener((adapterView, view1, i, l) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 2000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            Controller c = Controller.getInstance();
            c.addPathToPlaylist((DetailView)getActivity(), namePath,PlaylistList.getItemAtPosition(i).toString());
        });
        Button backButton= (Button) view.findViewById(R.id.backButton);
        backButton.setOnClickListener(view1 -> {
            view.startAnimation(buttonClick);
            Controller c = Controller.getInstance();
            c.cleanFragment(getActivity().findViewById(R.id.detailOverlayContainer));

        });
        return view;
    }
}