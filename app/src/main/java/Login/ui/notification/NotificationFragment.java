package Login.ui.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.natour2021.R;
import com.example.natour2021.databinding.FragmentNotificationBinding;

import java.util.ArrayList;

import Controller.Controller;
import Login.HomeView;


public class NotificationFragment extends Fragment {

    private NotificationViewModel notificationViewModel;
    private FragmentNotificationBinding binding;
    private ListView notificheList;
    private View root;
    private ArrayList<String> nomiSentieri;
    private ArrayList<String> descrizioni;
    private ArrayList<Integer> ID;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        notificationViewModel =
                new ViewModelProvider(this).get(NotificationViewModel.class);

        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        notificheList = (ListView) root.findViewById(R.id.notificationListView);
        notificheList.setOnItemClickListener((adapterView, view, i, l) -> {
            Controller c = Controller.getInstance();
            c.openAnswerReportOverlay(nomiSentieri.get(i),descrizioni.get(i),ID.get(i),(HomeView) getActivity(),this);
        });
        Controller c = Controller.getInstance();
        c.getNotification(this);

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setNotification(ArrayList<String> nomiSentieri, ArrayList<String> descrizioni, ArrayList<Integer> notificheID){
        TextView testo = (TextView) root.findViewById(R.id.textNotification);
        this.nomiSentieri=nomiSentieri;
        this.descrizioni=descrizioni;
        this.ID=notificheID;
        if(nomiSentieri.size()!=0) {
            ArrayList<String> notificaToList = new ArrayList<>();
            for(String s:nomiSentieri)  notificaToList.add("Segnalazione su sentiero "+s);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, notificaToList);
            testo.setText("Ecco le tue notifiche");
            notificheList.setAdapter(arrayAdapter);
            notificheList.setEnabled(true);
        }
        else{
            testo.setText("Non hai nuove notifiche");
        }
    }

    public void removeNotification(Integer id){
        TextView testo = (TextView) root.findViewById(R.id.textNotification);
        int posizione=-1;
        for(Integer idnotifica:ID){
            if(idnotifica==id) posizione=ID.indexOf(idnotifica);
        }
        if(posizione!=-1) {
            nomiSentieri.remove(posizione);
            descrizioni.remove(posizione);
            ID.remove(posizione);
            if(nomiSentieri.size()==0){
                notificheList.setVisibility(View.GONE);
                testo.setText("Non hai nuove notifiche");
            }
            else{
                ArrayList<String> notificaToList = new ArrayList<>();
                for(String s:nomiSentieri)  notificaToList.add("Segnalazione su sentiero "+s);
                ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, notificaToList);
                notificheList.setAdapter(arrayAdapter);
            }
        }
    }
}

