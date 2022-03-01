package Create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.service.controls.Control;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natour2021.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.slider.Slider;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Controller.Controller;
import Exception.*;
import io.ticofab.androidgpxparser.parser.GPXParser;
import io.ticofab.androidgpxparser.parser.domain.Gpx;
import io.ticofab.androidgpxparser.parser.domain.Track;
import io.ticofab.androidgpxparser.parser.domain.TrackPoint;
import io.ticofab.androidgpxparser.parser.domain.TrackSegment;

public class CreateView extends AppCompatActivity {
    private ArrayList<Marker> coordinate;
    private MapViewFragment mapViewFragment;
    private long mLastClickTime = 0;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    private GoogleMap tempmap;
    private int requestcode=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_view);
        Controller c = Controller.getInstance();
        buttonClick.setDuration(300);
        coordinate=new ArrayList<>();
        Controller.getInstance().setCreateView(this);

        Slider durataSlider = (Slider) findViewById(R.id.durataPlaylistSlider);
        Slider difficoltaSlider = (Slider) findViewById(R.id.difficoltaPlaylistSlider);

        TextView durataTextView = (TextView) findViewById(R.id.durataTextView);
        TextView difficoltaTextView = (TextView) findViewById(R.id.difficoltaTextView);
        EditText nomeEditText = (EditText) findViewById(R.id.namePathTextView);
        EditText descrizioneEditText = (EditText) findViewById(R.id.descrizionePlainText);
        CheckBox accessibilitaCB = (CheckBox) findViewById((R.id.AccessibilitaCheckBox));


        durataSlider.addOnChangeListener((slider, value, fromUser) -> {
            if(durataSlider.getValue() - (int) durataSlider.getValue() == 0.5){
                durataTextView.setText("Durata: " +(int) durataSlider.getValue() + ":30 ore");
            }else{
                durataTextView.setText("Durata: " + (int) durataSlider.getValue() + " ore");
            }
        });

        difficoltaSlider.addOnChangeListener((slider, value, fromUser) -> {
            difficoltaTextView.setText("Difficoltà: " + (int)difficoltaSlider.getValue());
        });

        Button inserisciTracciatoButton = (Button) findViewById(R.id.inserisciTracciatoButton);
        inserisciTracciatoButton.setOnClickListener(view -> {
            view.startAnimation(buttonClick);
            mapViewFragment = c.openInsertPath(CreateView.this);
        });

        Button creaSentiero = (Button) findViewById(R.id.creaSentieroButton);
        creaSentiero.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 2000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            view.startAnimation(buttonClick);
            String puntoInziale="";
            String coor="";
            if(coordinate.size()!=0) {
                puntoInziale = coordinate.get(0).getPosition().latitude + " " + coordinate.get(0).getPosition().longitude;
                coordinate.remove(0);
                coor = "";
                for (Marker mar : coordinate)
                    coor += mar.getPosition().latitude + " " + mar.getPosition().longitude + " ";
            }
            try {
                c.checkPath(nomeEditText.getText().toString().trim(), descrizioneEditText.getText().toString().trim(), durataSlider.getValue(), (int) difficoltaSlider.getValue(), accessibilitaCB.isChecked(), puntoInziale, coor);
            }
            catch(NameWrongSizeException e1) {Toast.makeText(this, "Il nome non può essere nullo e deve essere massimo 100 caratteri",Toast.LENGTH_LONG).show();}
            catch(PathWrongSizeException e2) {Toast.makeText(this, "Il sentiero non deve essere vuoto o troppo lungo ",Toast.LENGTH_LONG).show();}
            catch(DescriptionWrongSizeException e3) {Toast.makeText(this, "La descrizione deve essere di massimo 200 caratteri",Toast.LENGTH_LONG).show();}
            catch(DifficultyOutOfBoundException e4) {Toast.makeText(this, "Difficoltà deve essere tra 0 e 10",Toast.LENGTH_LONG).show();}
            catch(DurationOutOfBoundException e5) {Toast.makeText(this, "Durata deve essere tra 0 e 10",Toast.LENGTH_LONG).show();}
        });
    }

    public void setCoordinate(ArrayList<Marker> coor){
        coordinate.clear();
        coordinate.addAll(coor);
    }
    public ArrayList<Marker> getCoordinate(){
        return coordinate;
    }

    @Override
    public void onBackPressed() {
        if(mapViewFragment!=null){
            Controller c = Controller.getInstance();
            c.cleanFragment(findViewById(R.id.mapViewContainer));
            mapViewFragment=null;
        }
        else super.onBackPressed();
    }

    public void GPX(){
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.setType("*/*");
        i.addCategory(Intent.CATEGORY_OPENABLE);
        try{
            startActivityForResult(
                    Intent.createChooser(i, "Select a File to Upload"), requestcode);
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestcode, int resultCode, Intent Data){
        if (resultCode == RESULT_OK) {
            Uri uri = Data.getData();
            parseGPX(uri);
        }
        super.onActivityResult(requestcode,resultCode,Data);
    }

    public void parseGPX(Uri filename){
        ArrayList<Marker> coordinateFromGPX= new ArrayList<>();
        GPXParser parser = new GPXParser();
        Gpx parsedGpx = null;
        try {
            InputStream in = getContentResolver().openInputStream(filename);
            parsedGpx = parser.parse(in); // consider doing this on a background thread
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

        if (parsedGpx != null) {
            List<Track> tracks = parsedGpx.getTracks();
            for (int i = 0; i < tracks.size(); i++) {
                Track track = tracks.get(i);
                List<TrackSegment> segments = track.getTrackSegments();
                for (int j = 0; j < segments.size(); j++) {
                    TrackSegment segment = segments.get(j);
                    int k=3;
                    for (TrackPoint trackPoint : segment.getTrackPoints()) {
                        if(k==3) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(new LatLng(trackPoint.getLatitude(), trackPoint.getLongitude()));
                            Marker marker = tempmap.addMarker(markerOptions);
                            coordinateFromGPX.add(marker);
                            k=0;
                        }
                        else k++;
                    }
                }
            }
        } else {
            Toast.makeText(this,"Il file che hai scelto non è un file valido",Toast.LENGTH_LONG).show();
        }

        if(coordinateFromGPX!=null){
            setCoordinate(coordinateFromGPX);
            Controller c = Controller.getInstance();
            c.cleanFragment(findViewById(R.id.mapViewContainer));
            mapViewFragment=null;
        }
    }

    public void setTempmap(GoogleMap map){
        tempmap=map;
    }
}