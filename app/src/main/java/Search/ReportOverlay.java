package Search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.natour2021.R;

import Controller.Controller;
import Exception.*;

public class ReportOverlay extends Fragment {
    private long mLastClickTime = 0;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    private String nomesentiero;
    private String creatoresentiero;

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
            nomesentiero=getArguments().getString("nomesentiero");
            creatoresentiero=getArguments().getString("creatore");
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
            SharedPreferences sharedPref = getActivity().getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);
            String segnalante = sharedPref.getString(String.valueOf(R.string.logged_email),"");

            try {
                c.checkReport((DetailView) getActivity(), nomesentiero,"", reportMotivation.getText().toString().trim() ,segnalante ,creatoresentiero, Controller.getInstance());
                c.cleanFragment(getActivity().findViewById(R.id.detailOverlayContainer));
            }
            catch(AnswerNotEmptyException e1){Toast.makeText(getContext(),"Non deve esserci una risposta",Toast.LENGTH_LONG).show();}
            catch(MotivationWrongSizeException e2){
                Toast.makeText(getContext(),"La motivazione non deve essere vuota e deve essere massimo di 200 caratteri",Toast.LENGTH_LONG).show();
                Log.e("Error","La motivazione non deve essere vuota e deve essere massimo di 200 caratteri");
            }
            catch(CreatorWrongSizeException e3){Toast.makeText(getContext(),"Il creatore non deve essere vuoto e deve essere massimo di 50 caratteri",Toast.LENGTH_LONG).show();}
            catch(ReporterWrongSizeException e4){Toast.makeText(getContext(),"Il segnalante non deve essere vuoto e deve essere massimo di 50 caratteri",Toast.LENGTH_LONG).show();}
            catch(CreatorEqualsReporterException e5){
                Toast.makeText(getContext(),"Non puoi segnalare un tuo sentiero",Toast.LENGTH_LONG).show();
                Log.e("Error","Segnalazione proprio sentiero");
                c.cleanFragment(getActivity().findViewById(R.id.detailOverlayContainer));
            }
            catch(NamePathWrongSizeException e6){Toast.makeText(getContext(),"Il nome del sentiero non deve essere vuoto e deve essere massimo di 100 caratteri",Toast.LENGTH_LONG).show();}


        });
        return view;

    }

}