package Login.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.natour2021.R;
import com.example.natour2021.databinding.FragmentSettingsBinding;

import Controller.Controller;
import Login.HomeView;


public class SettingsFragment extends Fragment {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);


    private SettingsViewModel settingsViewModel;
private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

    binding = FragmentSettingsBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        SharedPreferences sharedPref = getContext().getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);
        String tipo = sharedPref.getString(String.valueOf(R.string.tipo_login),"");
        buttonClick.setDuration(300);

        Button changePassword = root.findViewById(R.id.changePassButton);
        changePassword.setOnClickListener(view -> {
            if(tipo.equals("normale")) {
                view.startAnimation(buttonClick);
                ChangePasswordFragment changePasswordFragment = Controller.getInstance().changePasswordOverlay((HomeView) getActivity());
                ((HomeView) getActivity()).setChangePasswordFragment(changePasswordFragment);
            }
            else Toast.makeText(getContext(),"Non puoi cambiare Password con l'accesso con google o facebook",Toast.LENGTH_LONG).show();
        });

        Button logoutButton= (Button) root.findViewById(R.id.LogoutButton);
        logoutButton.setOnClickListener(view ->{
            view.startAnimation(buttonClick);
            Controller c = Controller.getInstance();
            LogoutFragment logoutFragment = c.logoutOverlay((HomeView)getActivity());
            ((HomeView)getActivity()).setLogoutFragment(logoutFragment);
        });
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}