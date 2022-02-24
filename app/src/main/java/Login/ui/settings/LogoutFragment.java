package Login.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.example.natour2021.R;

import Controller.Controller;
import Login.HomeView;

public class LogoutFragment extends Fragment {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    public LogoutFragment() {
        // Required empty public constructor
    }

    public static LogoutFragment newInstance() {
        LogoutFragment fragment = new LogoutFragment();

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
        view=  inflater.inflate(R.layout.fragment_logout, container, false);
        buttonClick.setDuration(300);

        Button nologout = (Button) view.findViewById(R.id.noLogoutButton);
        nologout.setOnClickListener(view1 -> {
            view1.startAnimation(buttonClick);
            Controller c = Controller.getInstance();
            c.cleanFragment(getActivity().findViewById(((ViewGroup)getView().getParent()).getId()));
        });

        Button yeslogout = (Button)  view.findViewById(R.id.YesLogoutButton);
        yeslogout.setOnClickListener(view1 -> {
            view1.startAnimation(buttonClick);
            Controller c = Controller.getInstance();
            c.logout(getActivity().findViewById(((ViewGroup)getView().getParent()).getId()), (HomeView) getActivity());
        });
        return view;
    }
}

