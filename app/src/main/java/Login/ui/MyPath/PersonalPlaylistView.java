package Login.ui.MyPath;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.natour2021.R;
import com.example.natour2021.databinding.ActivityPersonalPlaylistViewBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Controller.Controller;
import Entity.Path;
import Login.HomeView;


public class PersonalPlaylistView extends Fragment {

    private long mLastClickTime = 0;
    private MyPathViewModel myPathViewModel;
    private ActivityPersonalPlaylistViewBinding binding;
    private ListView sentieriPlaylist;
    private View root;
    private ArrayList<String> nomisentieri;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        myPathViewModel = new ViewModelProvider(this).get(MyPathViewModel.class);
        binding = ActivityPersonalPlaylistViewBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        sentieriPlaylist = (ListView) root.findViewById(R.id.personalPathListView);
        sentieriPlaylist.setOnItemClickListener((adapterView, view, i, l) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 2000){
                return;
            }
            Controller c = Controller.getInstance();
            c.getAllDetailsOfPersonalPath((HomeView) getActivity(),nomisentieri.get(i));
        });
        Controller.getInstance().getPersonalPaths(this);

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setPersonalList(ArrayList<String> nomisentieri){
        this.nomisentieri = nomisentieri;
        TextView testo = (TextView) root.findViewById(R.id.textPersonalPlaylist);
        if(nomisentieri.size()!=0) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, nomisentieri);
            testo.setText("Ecco i sentieri che hai creato");
            sentieriPlaylist.setAdapter(arrayAdapter);
            sentieriPlaylist.setVisibility(View.VISIBLE);
        }
        else{
            testo.setText("Non hai creato sentieri");
            sentieriPlaylist.setVisibility(View.GONE);
        }
    }
}