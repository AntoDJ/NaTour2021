package Login.ui.notification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natour2021.R;

import org.w3c.dom.Text;

import Controller.Controller;
import Login.HomeView;

public class AnswerReportFragment extends Fragment {

    private String nomesentiero;
    private String descrizione;
    private int idnotifica;

    public AnswerReportFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AnswerReportFragment newInstance() {
        AnswerReportFragment fragment = new AnswerReportFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            descrizione = getArguments().getString("descrizione");
            nomesentiero = getArguments().getString("nomesentiero");
            idnotifica = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view= inflater.inflate(R.layout.answer_report_fragment, container, false);
        EditText rispostaEditText= (EditText) view.findViewById(R.id.rispostaText) ;

        TextView nomesentieroReport = (TextView) view.findViewById(R.id.nomeSentieroReport);
        nomesentieroReport.setText("Ecco la motivazione della segnalazione sul sentiero: "+nomesentiero);

        TextView motivazioneReport = (TextView) view.findViewById(R.id.motivationeText);
        motivazioneReport.setText(descrizione);


        Button reportAnswerButton = (Button) view.findViewById(R.id.reportAnswerButton);
        Button reportIgnoreButton = (Button) view.findViewById(R.id.reportIgnoreButton);
        Button reportBackButton = (Button) view.findViewById(R.id.reportBackButton);
        reportBackButton.setOnClickListener(view1 -> {
            Controller c = Controller.getInstance();
            c.cleanFragment(getActivity().findViewById(((ViewGroup)getView().getParent()).getId()));
        });

        reportAnswerButton.setOnClickListener(view1 -> {
            if(!rispostaEditText.getText().toString().trim().equals("")&&!rispostaEditText.getText().toString().trim().equals("vuota")) {
                Controller c = Controller.getInstance();
                c.cleanFragment(getActivity().findViewById(((ViewGroup)getView().getParent()).getId()));
                c.rispondiSegnalazione(idnotifica, rispostaEditText.getText().toString().trim());
            }
            else Toast.makeText(getActivity(),"Inserisci una risposta valida",Toast.LENGTH_LONG).show();
        });

        reportIgnoreButton.setOnClickListener(view1 -> {
            Controller c = Controller.getInstance();
            c.cleanFragment(getActivity().findViewById(((ViewGroup)getView().getParent()).getId()));
            c.rispondiSegnalazione(idnotifica, "vuota");
        });
        return view;
    }
}