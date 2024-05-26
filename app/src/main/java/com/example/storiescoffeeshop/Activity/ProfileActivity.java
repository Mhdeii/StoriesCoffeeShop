package com.example.storiescoffeeshop.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import com.example.storiescoffeeshop.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final String TAG = "ProfileActivity";

    private ImageView profileImageView;
    private ImageButton captureButton;
    private String currentPhotoPath;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = findViewById(R.id.profileImageView);
        captureButton = findViewById(R.id.captureButton);

        captureButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                dispatchTakePictureIntent();
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> deleteProfilePicture());

        // Initialize database
        ProfileDbHelper dbHelper = new ProfileDbHelper(this);
        database = dbHelper.getWritableDatabase();

        // Load the profile picture from database
        loadProfilePicture();
    }

    private void deleteProfilePicture() {
        database.delete(ProfileContract.ProfileEntry.TABLE_NAME, null, null);
        profileImageView.setImageResource(R.drawable.profile); // or any placeholder image
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e(TAG, "Error occurred while creating the File", ex);
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.storiescoffeeshop.provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new File(currentPhotoPath);
            if (imgFile.exists()) {
                profileImageView.setImageURI(Uri.fromFile(imgFile));
                saveProfilePicture(currentPhotoPath);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir("Pictures");
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void saveProfilePicture(String imagePath) {
        ContentValues values = new ContentValues();
        values.put(ProfileContract.ProfileEntry.COLUMN_NAME_IMAGE_PATH, imagePath);
        database.insert(ProfileContract.ProfileEntry.TABLE_NAME, null, values);

        // Save image path to shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("ProfilePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profile_image_path", imagePath);
        editor.apply();
    }


    private void loadProfilePicture() {
        String[] projection = {
                ProfileContract.ProfileEntry.COLUMN_NAME_IMAGE_PATH
        };

        Cursor cursor = database.query(
                ProfileContract.ProfileEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(ProfileContract.ProfileEntry.COLUMN_NAME_IMAGE_PATH));
            profileImageView.setImageURI(Uri.fromFile(new File(imagePath)));
        }
        cursor.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                // Permission denied, show a message to the user
                Log.i(TAG, "Camera permission was denied");
            }
        }
    }

    private static class ProfileDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Profile.db";

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + ProfileContract.ProfileEntry.TABLE_NAME + " (" +
                        ProfileContract.ProfileEntry._ID + " INTEGER PRIMARY KEY," +
                        ProfileContract.ProfileEntry.COLUMN_NAME_IMAGE_PATH + " TEXT)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ProfileContract.ProfileEntry.TABLE_NAME;

        public ProfileDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }

    public static class ProfileContract {
        private ProfileContract() {}

        public static class ProfileEntry implements BaseColumns {
            public static final String TABLE_NAME = "profile";
            public static final String COLUMN_NAME_IMAGE_PATH = "image_path";
        }
    }
}
