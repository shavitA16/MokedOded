package com.schoolproject.MokedOded;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReportActivity extends AppCompatActivity {

    private static final String TAG ="" ;
    private static final int CAMERA_REQUEST_CODE = 16;
    FirebaseDatabase database;
    FirebaseStorage storage;
    DatabaseReference myRef;
    StorageReference storageRef;
    StorageReference itemRef;
    private FirebaseAuth mAuth;
    ImageView cameraImageView;
    Button sendButton;
    Button locationsButton;
    final int REQUEST_IMAGE_CAPTURE = 50;
    final int LOCATION_REQUEST_CODE = 69; // LMAO
    private Uri imgURI;
    private String currentPhotoPath;

    private Uri newImageUri;
    private File newImageFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        mAuth = FirebaseAuth.getInstance();

        cameraImageView = findViewById(R.id.cameraImageView);
        cameraImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dispatchTakePictureIntent();
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

        final EditText notesEditText = findViewById(R.id.notesEditText);
        Toast onSuccessToast = Toast.makeText(this, "הבעיה דווחה בהצלחה", Toast.LENGTH_SHORT);

        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton s = Singleton.getInstance();
                s.description = notesEditText.getText().toString();
                s.issue = issuesSpinner.getSelectedItemPosition();
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
                s.date = dateFormat.format(date);
                s.userEmail = mAuth.getCurrentUser().getEmail();

                String uid = String.valueOf(System.currentTimeMillis());
                String imageNameWithExtension = uid + ".jpg";
                s.imgURL = imageNameWithExtension;
                storage = FirebaseStorage.getInstance("gs://the-moked-81b25.appspot.com/");
                storageRef = storage.getReference("images");
                itemRef = storageRef.child(imageNameWithExtension);
//                dispatchTakePictureIntent();
//                if(imgURI!=null){
//                    itemRef.putFile(imgURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            database = FirebaseDatabase.getInstance("https://the-moked-81b25-default-rtdb.europe-west1.firebasedatabase.app/");
//                            myRef = database.getReference("issues");
//                            myRef.child(uid).setValue(s);
//                            onSuccessToast.show();
//                            finish();
//                        }
//                    });
//                }
                if (s.issue!=0 && s.location!=0) {
                    database = FirebaseDatabase.getInstance("https://the-moked-81b25-default-rtdb.europe-west1.firebasedatabase.app/");
                    myRef = database.getReference("issues");
                    myRef.child(uid).setValue(s);
                    onSuccessToast.show();
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cameraImageView.setImageBitmap(imageBitmap);
        } else if (requestCode == LOCATION_REQUEST_CODE) {
            checkLocation();
        }

    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            newImageFile = null;
            try {
                newImageFile = createImageFile();
            } catch (IOException ex) {
                Log.e(TAG, "dispatchTakePictureIntent: Error occurred while creating the File", ex);

            }
            // Continue only if the File was successfully created
            if (newImageFile != null) {
                newImageUri = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        newImageFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, newImageUri);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
//    private void dispatchTakePictureIntent() {     nissim
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//               Log.d("error", ex.toString());
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.schoolproject.MokedOded",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            }
//        }
//    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        imgURI = Uri.fromFile(f);
        cameraImageView.setImageURI(imgURI);
        mediaScanIntent.setData(imgURI);
        this.sendBroadcast(mediaScanIntent);
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