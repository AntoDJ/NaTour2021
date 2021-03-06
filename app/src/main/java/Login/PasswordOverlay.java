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


public class PasswordOverlay extends Fragment {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);


    public PasswordOverlay() {
        // Required empty public constructor
    }

    public static PasswordOverlay newInstance() {
        PasswordOverlay fragment = new PasswordOverlay();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_password_overlay, container, false);
        buttonClick.setDuration(300);
        EditText MailEditText = (EditText) view.findViewById(R.id.forgotPassEmailET);
        EditText CodiceEditText = (EditText) view.findViewById(R.id.forgotPassCodiceET);
        EditText PassEditText = (EditText) view.findViewById(R.id.forgotPassPasswordET);


        Button cancelButton = (Button) view.findViewById(R.id.forgotPassCancelButton);
        cancelButton.setOnClickListener(view1 -> {
            view1.startAnimation(buttonClick);
            Controller.getInstance().cleanFragment(getActivity().findViewById(R.id.passwordOverlayContainer));
        });
        Button sendCodeButton = (Button) view.findViewById(R.id.mandaCodiceButton);
        sendCodeButton.setOnClickListener(view1 -> {
            view1.startAnimation(buttonClick);
            if(!MailEditText.getText().toString().trim().equals("")){
                Controller.getInstance().resetPassword(MailEditText.getText().toString().trim());
            }
            else Toast.makeText(getActivity(),"Inserisci la mail",Toast.LENGTH_LONG).show();
        });
        Button changePassButton = (Button) view.findViewById(R.id.forgotPassChangePassButton);
        changePassButton.setOnClickListener(view1 -> {
            view1.startAnimation(buttonClick);
            if(!CodiceEditText.getText().toString().trim().equals("")&&!PassEditText.getText().toString().trim().equals("")){
                Controller.getInstance().confirmResetPassword(PassEditText.getText().toString().trim(),
                        CodiceEditText.getText().toString().trim());
            }
            else Toast.makeText(getActivity(),"Inserisci Password e Codice",Toast.LENGTH_LONG).show();
        });




        return view;
    }
}