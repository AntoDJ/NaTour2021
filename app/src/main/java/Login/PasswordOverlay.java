package Login;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.natour2021.R;

import Controller.Controller;


public class PasswordOverlay extends Fragment {

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
        ImageButton xImageButton = (ImageButton) view.findViewById(R.id.exitPasswordyOverlaButton);
        xImageButton.setOnClickListener(view1 -> {
            Controller c = Controller.getInstance();
            c.cleanFragment(getActivity().findViewById(R.id.passwordOverlayContainer));
        });


        return view;
    }
    public boolean onKey(View v, int keyCode, KeyEvent event){
        if(keyCode== KeyEvent.KEYCODE_BACK){
            return true;
        }
        return false;
    }

}