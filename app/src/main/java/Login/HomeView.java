package Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natour2021.R;
import com.example.natour2021.databinding.ActivityNavigationBinding;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import Controller.Controller;
import Login.ui.notification.AnswerReportFragment;
import Login.ui.settings.LogoutFragment;


public class HomeView extends AppCompatActivity {
    private int backButtonCount=0;
    private LogoutFragment logoutFragment;
    private AnswerReportFragment answerReportFragment;

    private AppBarConfiguration mAppBarConfiguration;
private ActivityNavigationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigation.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_my_path, R.id.nav_playlist, R.id.nav_notification, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        SharedPreferences sharedPref= this.getSharedPreferences(String.valueOf(R.string.preference_file_key),Context.MODE_PRIVATE);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.navigationEmailTextView);
        navUsername.setText(sharedPref.getString(String.valueOf(R.string.logged_email),""));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void setLogoutFragment(LogoutFragment logoutFragment) {
        this.logoutFragment=logoutFragment;
    }

    @Override
    public void onBackPressed() {
        if(answerReportFragment!=null){
            Controller c = Controller.getInstance();
            c.cleanFragment(findViewById(R.id.reportAnswerLayout));
            answerReportFragment = null;
        }
        else {
            if (logoutFragment != null) {
                Controller c = Controller.getInstance();
                c.cleanFragment(findViewById(R.id.HomeContainer));
                logoutFragment = null;
            } else {
                if (backButtonCount >= 1) {
                    backButtonCount = 0;
                    System.exit(0);
                } else {
                    Toast.makeText(this, "Premi di nuovo indietro per chiudere l'app", Toast.LENGTH_SHORT).show();
                    backButtonCount++;
                }
            }
        }
    }

    public void setAnswerReportFragment(AnswerReportFragment answerReportFragment) {
        this.answerReportFragment=answerReportFragment;
    }
}