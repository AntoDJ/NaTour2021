package Playlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.example.natour2021.R;

import Controller.Controller;

public class removeFromPlaylistOverlay extends Fragment {
    private String nomesentiero;
    private String nomeplaylist;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    public removeFromPlaylistOverlay() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static removeFromPlaylistOverlay newInstance(String param1, String param2) {
        removeFromPlaylistOverlay fragment = new removeFromPlaylistOverlay();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.nomesentiero=getArguments().getString("nomesentiero");
            this.nomeplaylist=getArguments().getString("nomeplaylist");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view= inflater.inflate(R.layout.fragment_remove_from_playlist, container, false);

        Button removeFromPlaylistNoButton = (Button) view.findViewById(R.id.removePlaylistNoButton);
        removeFromPlaylistNoButton.setOnClickListener(view1 -> {
            view.startAnimation(buttonClick);
            Controller.getInstance().cleanFragment(getActivity().findViewById(R.id.removeFromPlaylistContainer));
        });

        Button removeFromPlaylistYesButton = (Button) view.findViewById(R.id.removePlaylistYesButton);
        removeFromPlaylistYesButton.setOnClickListener(view1 -> {
            view.startAnimation(buttonClick);
            Controller.getInstance().cleanFragment(getActivity().findViewById(R.id.removeFromPlaylistContainer));
            Controller.getInstance().removeFromPlaylist(nomesentiero, nomeplaylist, (PlaylistDetailsView) getActivity());
        });


        return view;
    }
}