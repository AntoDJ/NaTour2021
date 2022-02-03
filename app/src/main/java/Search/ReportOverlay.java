package Search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.natour2021.R;

import Controller.Controller;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportOverlay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportOverlay extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportOverlay() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static ReportOverlay newInstance(String param1, String param2) {
        ReportOverlay fragment = new ReportOverlay();
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
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_report_overlay, container, false);
        Button esciButton= (Button) view.findViewById(R.id.annullaReportButton);
        esciButton.setOnClickListener(view1 -> {
            Controller c = Controller.getInstance();
            c.cleanFragment(getActivity().findViewById(R.id.detailOverlayContainer));

        });

        EditText reportMotivation = (EditText) view.findViewById(R.id.reportMotivation);
        Button reportButton = (Button) view.findViewById(R.id.confermaReportButton);
        reportButton.setOnClickListener(view1 -> {
            Controller c = Controller.getInstance();
            ((DetailView)getActivity()).ReportPath(reportMotivation.getText().toString().trim());
            c.cleanFragment(getActivity().findViewById(R.id.detailOverlayContainer));
        });
        return view;

    }

}