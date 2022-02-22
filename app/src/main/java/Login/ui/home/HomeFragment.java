package Login.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.natour2021.R;
import com.example.natour2021.databinding.FragmentHomeBinding;

import Controller.Controller;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //LAMBDA CHE CONTROLLA L'ADMIN
        Button findpathButton = (Button) root.findViewById(R.id.findPathButton);
        findpathButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.searchView(this);
        });

        Button createpathButton = (Button) root.findViewById(R.id.createPathButton);
        createpathButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.openCreatePathView(HomeFragment.this);
        });
        return root;
    }


@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}