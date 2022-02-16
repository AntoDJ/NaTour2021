package Login.ui.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.natour2021.R;
import com.example.natour2021.databinding.FragmentNotificationBinding;

import Controller.Controller;


public class NotificationFragment extends Fragment {

    private NotificationViewModel notificationViewModel;
    private FragmentNotificationBinding binding;
    private ListView notifiche;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        notificationViewModel =
                new ViewModelProvider(this).get(NotificationViewModel.class);

    binding = FragmentNotificationBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        notifiche = (ListView) root.findViewById(R.id.personalPathListView);
        notifiche.setOnItemClickListener((adapterView, view, i, l) -> {
            Controller c = Controller.getInstance();

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
}