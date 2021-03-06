package Login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.natour2021.R;

import Controller.Controller;

public class RegistrationConfirmFragment extends Fragment {
    private String email;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    public RegistrationConfirmFragment() {
        // Required empty public constructor
    }

    public static RegistrationConfirmFragment newInstance() {
        RegistrationConfirmFragment fragment = new RegistrationConfirmFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString("email");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_registration_confirm, container, false);
        buttonClick.setDuration(300);
        Button confirmRegistrationButton = (Button) view.findViewById(R.id.codeConfirmationButton);
        EditText confirmRegistrationEditText = (EditText) view.findViewById(R.id.codeConfirmationEditText);

        confirmRegistrationButton.setOnClickListener(view1 -> {
            view1.startAnimation(buttonClick);
            if(confirmRegistrationEditText.getText().toString().trim().length()==6) {
                Controller.getInstance().confermaRegistrazione(confirmRegistrationEditText.getText().toString().trim(),
                        getActivity().findViewById(R.id.registrationFrameLayout),
                        (RegistrationView) getActivity(), email);
            }
            else Toast.makeText ((RegistrationView)getActivity(), "Inserisci correttamente il codice",Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}