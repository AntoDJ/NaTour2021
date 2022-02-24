package Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.natour2021.R;

import Controller.Controller;
import Playlist.PersonalDetailView;

public class AdminDeleteFragment extends Fragment {
    private String nomeSentiero;

    public AdminDeleteFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AdminDeleteFragment newInstance(String param1, String param2) {
        AdminDeleteFragment fragment = new AdminDeleteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nomeSentiero = getArguments().getString("nomeSentiero");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view= inflater.inflate(R.layout.fragment_admin_delete, container, false);

        /*Button deletePathNoButton= (Button) view.findViewById();
        deletePathNoButton.setOnClickListener(view1 -> {
            Controller c = Controller.getInstance();
            c.cleanFragment(getActivity().findViewById());
        });

        Button deletePathYesButton= (Button) view.findViewById();
        deletePathYesButton.setOnClickListener(view1 -> {
            Controller c = Controller.getInstance();
            c.cleanFragment(getActivity().findViewById());
            c.deletePathAdmin(nomeSentiero, (AdminDetailView) getActivity());
        });*/

        return view;
    }
}