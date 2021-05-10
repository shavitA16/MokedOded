package com.schoolproject.MokedOded;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;

public class ReportActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    ImageView cameraImageView;
    Button sendButton;
    Button locationsButton;
    final int REQUEST_IMAGE_CAPTURE = 50;
    final int LOCATION_REQUEST_CODE = 69; // LMAO
//    String selectedIssue;
//    int issue;
//    String description;
//    Bitmap pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        cameraImageView = findViewById(R.id.cameraImageView);
        cameraImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(openCamera, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {

                }
            }
        });

        final Intent reportToLocations = new Intent(this, LocationsActivity.class);
        locationsButton = findViewById(R.id.locationsButton);
        locationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(reportToLocations, LOCATION_REQUEST_CODE);
                checkLocation();
            }
        });

        final Spinner issuesSpinner = findViewById(R.id.issuesSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.issues_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        issuesSpinner.setAdapter(adapter);
//        selectedIssue = issuesSpinner.getSelectedItem().toString();

        final EditText notesEditText = findViewById(R.id.notesEditText);

        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton s = Singleton.getInstance();
                s.description = notesEditText.getText().toString();
                s.issue = issuesSpinner.getSelectedItemPosition();
                s.date = Calendar.getInstance().getTime();

                database = FirebaseDatabase.getInstance("https://the-moked-81b25-default-rtdb.europe-west1.firebasedatabase.app/");
                myRef = database.getReference("issues");

                myRef.child(s.date.toString()).setValue(s);

                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cameraImageView.setImageBitmap(imageBitmap);
        } else if (requestCode == LOCATION_REQUEST_CODE) {
            checkLocation();
        }

    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_location, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
       /* popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        }); */

//        Button north1Button = findViewById(R.id.north1Button);
//        Button north2Button = findViewById(R.id.north2Button);
//        Button north3Button = findViewById(R.id.north3Button);
//        Button south1Button = findViewById(R.id.south1Button);
//        Button south2Button = findViewById(R.id.south2Button);
//        Button south3Button = findViewById(R.id.south3Button);
//           north1Button.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   Singleton s = Singleton.getInstance();
//                   s.location = 1;
//               }
//           });
//           north2Button.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   //result[0] = 2;
//                   popupWindow.dismiss();
//               }
//           });
//           north3Button.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   result[0] = 3;
//                   //   popupWindow.dismiss();
//               }
//           });
//           south1Button.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   result[0] = 11;
//                   //   popupWindow.dismiss();
//               }
//           });
//           south2Button.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   result[0] = 12;
//                   //  popupWindow.dismiss();
//               }
//           });
//           south3Button.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   result[0] = 13;
//                   //  popupWindow.dismiss();
//               }
//           });
    }

    private void checkLocation(){
        Singleton s = Singleton.getInstance();

        switch (s.location) {
            // North -
            case 1: // lobby 1
                locationsButton.setText("מבואה 1 - צפון");
                break;
            case 2: // lobby 2
                locationsButton.setText("מבואה 2 - צפון");
                break;
            case 3: // lobby 3
                locationsButton.setText("מבואה 3 - צפון");
                break;
            //South -
            case 11: // lobby 1
                locationsButton.setText("מבואה 1 - דרום");
                break;
            case 12: // lobby 2
                locationsButton.setText("מבואה 2 - דרום");
                break;
            case 13: // lobby 3
                locationsButton.setText("מבואה 3 - דרום");
                break;
            //Gardens -
            case 91: // north
                locationsButton.setText("חצר צפונית");
                break;
            case 92: // south
                locationsButton.setText("חצר דרומית");
                break;
            case 93: // main
                locationsButton.setText("חצר מרכזית");
                break;
        }
    }
}