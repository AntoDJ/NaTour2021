package Login.ui.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSettings;
import com.amazonaws.services.cognitoidentityprovider.model.AdminSetUserPasswordRequest;
import com.amplifyframework.core.Amplify;
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
        buttonClick.setDuration(300);
        Button changePassword = root.findViewById(R.id.changePassButton);
        changePassword.setOnClickListener(view -> {
            view.startAnimation(buttonClick);
            ChangePasswordFragment changePasswordFragment = Controller.getInstance().changePasswordOverlay((HomeView)getActivity());
            ((HomeView)getActivity()).setChangePasswordFragment(changePasswordFragment);
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