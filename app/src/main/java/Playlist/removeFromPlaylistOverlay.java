package Playlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.natour2021.R;

import Controller.Controller;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link removeFromPlaylistOverlay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class removeFromPlaylistOverlay extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public removeFromPlaylistOverlay() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment removeFromPlaylistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static removeFromPlaylistOverlay newInstance(String param1, String param2) {
        removeFromPlaylistOverlay fragment = new removeFromPlaylistOverlay();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view= inflater.inflate(R.layout.fragment_remove_from_playlist, container, false);

        Button b1 = (Button) view.findViewById(R.id.removePlaylistNoButton);
        b1.setOnClickListener(view1 -> {
            Controller c = Controller.getInstance();
            c.cleanFragment(getActivity().findViewById(R.id.removeFromPlaylistContainer));
        });

        Button b2 = (Button) view.findViewById(R.id.removePlaylistYesButton);

        return view;
    }
}