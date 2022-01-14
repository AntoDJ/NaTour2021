package Login.ui.MyPath;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.natour2021.R;
import com.example.natour2021.databinding.ActivityPersonalPlaylistViewBinding;

import java.util.ArrayList;

import Controller.Controller;
import Entity.Path;


public class PersonalPlaylistView extends Fragment {
    Controller c;
    ArrayList<Path> path;

    private MyPathViewModel myPathViewModel;
private ActivityPersonalPlaylistViewBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        myPathViewModel = new ViewModelProvider(this).get(MyPathViewModel.class);
        binding = ActivityPersonalPlaylistViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView sentieriPlaylist = (ListView) root.findViewById(R.id.personalPathListView);
        c = Controller.getInstance();

        path = c.getPersonalPathOfPlaylist();
        ArrayList<String> dettagliSentiero = new ArrayList<>();
        for(Path p : path){
            dettagliSentiero.add(p.getNomeSentiero() + " \nDurata:" + p.getDurata() + "    Difficoltà:" + p.getDifficolta());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, dettagliSentiero);
        sentieriPlaylist.setAdapter(arrayAdapter);

        sentieriPlaylist.setOnItemClickListener((adapterView, view, i, l) -> {
            Controller c = Controller.getInstance();
            String text = sentieriPlaylist.getItemAtPosition(i).toString();
            int index = text.indexOf(' ');
            String sentiero = text.substring(0, i);
            c.openPersonalDetailsView(this);
            //Finireeeeeeeee
        });



        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}