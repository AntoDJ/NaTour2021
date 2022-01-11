package Login.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.natour2021.R;
import com.example.natour2021.databinding.FragmentHomeBinding;

import Controller.Controller;
import Login.HomeView;
import Login.InitialView;

import Controller.Controller;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button pathButton = (Button) root.findViewById(R.id.findPathButton);
        pathButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.searchView(this);
        });
        return root;
    }

        Button createPathButton = (Button) root.findViewById(R.id.createPathButton);
        createPathButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.openCreatePathView(HomeFragment.this);
        });


@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}