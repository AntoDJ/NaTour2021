package Login.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.natour2021.R;
import com.example.natour2021.databinding.FragmentHomeBinding;

import Controller.Controller;
import Login.HomeView;


public class HomeFragment extends Fragment {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    private HomeViewModel homeViewModel;
private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        buttonClick.setDuration(300);

        Button findpathButton = (Button) root.findViewById(R.id.findPathButton);
        findpathButton.setOnClickListener(view -> {
            view.startAnimation(buttonClick);
            Controller c = Controller.getInstance();
            c.searchView(this);
        });

        Button createpathButton = (Button) root.findViewById(R.id.createPathButton);
        createpathButton.setOnClickListener(view -> {
            view.startAnimation(buttonClick);
            Controller c = Controller.getInstance();
            c.openCreatePathView(HomeFragment.this);
        });

        Button adminFunctionButton = (Button) root.findViewById(R.id.AdminFunctionButton);

        if(((HomeView)getActivity()).isAdmin()){
            adminFunctionButton.setVisibility(View.VISIBLE);
            adminFunctionButton.setOnClickListener(view -> {
                Controller.getInstance().openAdminView((HomeView)getActivity());
            });
        }


        ((HomeView)getActivity()).isAdmin();


        return root;
    }


@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}