package Login.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.natour2021.R;

import Controller.Controller;
import Login.HomeView;

public class ChangePasswordFragment extends Fragment {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    private long mLastClickTime = 0;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        view=  inflater.inflate(R.layout.fragment_change_password, container, false);
        buttonClick.setDuration(300);

        EditText vecchiaPass = (EditText) view.findViewById(R.id.vecchiaPassET);
        EditText nuovaPass = (EditText) view.findViewById(R.id.nuovaPassET);
        EditText confermaPass = (EditText) view.findViewById(R.id.confermaPassET);

        Button cancelButton = (Button) view.findViewById(R.id.changePassCancelButton);
        cancelButton.setOnClickListener(view1 -> {
            view1.startAnimation(buttonClick);
            Controller.getInstance().cleanFragment(getActivity().findViewById(((ViewGroup)getView().getParent()).getId()));
        });

        Button confirmButton = (Button) view.findViewById(R.id.changePassConfirmButton);
        confirmButton.setOnClickListener(view1 -> {
            view1.startAnimation(buttonClick);
            if (SystemClock.elapsedRealtime() - mLastClickTime < 2000){
                return;
            }
            if(nuovaPass.getText().toString().trim().equals(confermaPass.getText().toString().trim())){
                Controller.getInstance().changePassword(getActivity().findViewById(((ViewGroup)getView().getParent()).getId()),
                        (HomeView)getActivity() ,
                        vecchiaPass.getText().toString().trim(),nuovaPass.getText().toString().trim());
            }
            else Toast.makeText(getActivity(),"Le due password nuove devono corrispondere",Toast.LENGTH_LONG).show();
        });





        return view;
    }
}