package Playlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.example.natour2021.R;

import Controller.Controller;

public class deletePathOverlay extends Fragment {
    private String nomeSentiero;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    public deletePathOverlay() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static deletePathOverlay newInstance() {
        deletePathOverlay fragment = new deletePathOverlay();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nomeSentiero=getArguments().getString("nomeSentiero");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view= inflater.inflate(R.layout.fragment_delete_path_overlay, container, false);

        Button deletePathNoButton= (Button) view.findViewById(R.id.deletePathNoButton);
        deletePathNoButton.setOnClickListener(view1 -> {
            view1.startAnimation(buttonClick);
            Controller c = Controller.getInstance();
            c.cleanFragment(getActivity().findViewById(R.id.deletePathContainer));
        });

        Button deletePathYesButton= (Button) view.findViewById(R.id.deletePathYesButton);
        deletePathYesButton.setOnClickListener(view1 -> {
            view1.startAnimation(buttonClick);
            Controller c = Controller.getInstance();
            c.cleanFragment(getActivity().findViewById(R.id.deletePathContainer));
            c.deletePath(nomeSentiero, (PersonalDetailView) getActivity());
        });

        return view;
    }
}