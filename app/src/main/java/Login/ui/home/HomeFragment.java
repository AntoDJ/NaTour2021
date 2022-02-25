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
    private static Boolean admin=null;
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

        if(admin!=null&&this.admin){
            adminFunctionButton.setVisibility(View.VISIBLE);
            adminFunctionButton.setOnClickListener(view -> {
                Controller.getInstance().openAdminView((HomeView)getActivity());
            });
        }

        if(admin==null) {
            SharedPreferences sharedPref = getActivity().getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);
            String email = sharedPref.getString(String.valueOf(R.string.logged_email), "");
            Controller.getInstance().checkAdmin(this, email);
        }
        return root;
    }


@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setAdmin(Boolean isadmin){
        this.admin=isadmin;
        if(this.admin){
            Button adminFunctionButton = (Button) getView().findViewById(R.id.AdminFunctionButton);
            adminFunctionButton.setVisibility(View.VISIBLE);
            adminFunctionButton.setOnClickListener(view -> {
                Controller.getInstance().openAdminView((HomeView)getActivity());
            });
        }
    }
}