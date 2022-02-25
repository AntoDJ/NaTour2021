package Search;

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

public class ReportOverlay extends Fragment {
    private long mLastClickTime = 0;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    public ReportOverlay() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static ReportOverlay newInstance(String param1, String param2) {
        ReportOverlay fragment = new ReportOverlay();
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
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_report_overlay, container, false);
        Button esciButton= (Button) view.findViewById(R.id.annullaReportButton);
        esciButton.setOnClickListener(view1 -> {
            view.startAnimation(buttonClick);
            Controller c = Controller.getInstance();
            c.cleanFragment(getActivity().findViewById(R.id.detailOverlayContainer));

        });

        EditText reportMotivation = (EditText) view.findViewById(R.id.reportMotivation);
        Button reportButton = (Button) view.findViewById(R.id.confermaReportButton);
        reportButton.setOnClickListener(view1 -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 2000){
                return;
            }
            view.startAnimation(buttonClick);
            mLastClickTime = SystemClock.elapsedRealtime();
            Controller c = Controller.getInstance();
            ((DetailView)getActivity()).ReportPath(reportMotivation.getText().toString().trim());
            c.cleanFragment(getActivity().findViewById(R.id.detailOverlayContainer));
        });
        return view;

    }

}