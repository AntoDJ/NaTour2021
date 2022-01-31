package Login.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.natour2021.R;
import com.example.natour2021.databinding.FragmentSettingsBinding;

import Controller.Controller;
import Login.HomeView;


public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

    binding = FragmentSettingsBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        Button b1= (Button) root.findViewById(R.id.LogoutButton);
        b1.setOnClickListener(view ->{
             Controller c = Controller.getInstance();
             c.logoutOverlay(((HomeView)getActivity()));
        });
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}