package Login.ui.playlist;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.natour2021.R;
import com.example.natour2021.databinding.FragmentPlaylistBinding;

import java.util.ArrayList;

import Controller.Controller;


public class PlaylistFragment extends Fragment {

    private FragmentPlaylistBinding binding;
    private long mLastClickTime = 0;

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
            if (SystemClock.elapsedRealtime() - mLastClickTime < 5000){
                Toast.makeText(getContext(),"Sto caricando i sentieri della playlist",Toast.LENGTH_LONG).show();
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            Controller.getInstance().getPathOfPlaylist(this, playlistListView.getItemAtPosition(i).toString());
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}