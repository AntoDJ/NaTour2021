package Login.ui.playlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ListAdapter;

import com.example.natour2021.R;
import com.example.natour2021.databinding.FragmentPlaylistBinding;

import java.util.ArrayList;
import java.util.List;

import Controller.Controller;
import Login.HomeView;


public class PlaylistFragment extends Fragment {

    private FragmentPlaylistBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        PlaylistViewModel playlistViewModel = new ViewModelProvider(this).get(PlaylistViewModel.class);
        binding = FragmentPlaylistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView playlistListView = (ListView) root.findViewById(R.id.playlistListView);
        ArrayList<String> playlist = new ArrayList<String>();
        playlist.add("Sentieri preferiti");
        playlist.add("Sentieri da visitare");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, playlist);
        playlistListView.setAdapter(arrayAdapter);

        playlistListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Controller c = Controller.getInstance();
            String nomePlaylist = playlistListView.getItemAtPosition(i).toString();
            c.getPathOfPlaylist(this, nomePlaylist);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void playlistvuota(String nomePlaylist) {
        Toast.makeText(getActivity(),"Non ci sono sentieri nella playlist "+nomePlaylist,Toast.LENGTH_LONG).show();
    }
}