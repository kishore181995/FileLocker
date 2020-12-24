package com.example.myapplication;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.renderscript.Type;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.sillicompressor.SiliCompressor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends Activity {

    AppCompatImageView uploadPicture;
    public static final String FILE_PROVIDER_AUTHORITY = ".sillicompressor.provider";
    private static final int REQUEST_TAKE_CAMERA_PHOTO = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE_VID = 2;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;
    private static final int REQUEST_TAKE_VIDEO = 200;
    public static ImagesAdapter.RefreshData adapterInterfacce;

    SharedPreference sharedPreference;
    RecyclerView imagesRv;
    LinearLayout emptyLl;
    AppCompatTextView filesTv;

    ArrayList<String> arrayList = new ArrayList<>();

    AppCompatImageView imageView,abaoutApp;

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    String mCurrentPhotoPath;
    Uri capturedUri = null;
    Uri compressUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_start);

        adapterInterfacce = new ImagesAdapter.RefreshData() {
            @Override
            public void refresh() {

                getList(1);
            }
        };

        imagesRv = findViewById(R.id.imagesRV);
        uploadPicture = findViewById(R.id.fileUploadedIv);
        imageView = findViewById(R.id.image);
        sharedPreference = new SharedPreference();
        emptyLl = findViewById(R.id.emptyLl);
        filesTv = findViewById(R.id.filesTv);
        abaoutApp = findViewById(R.id.aboutApp);

        abaoutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abtScren = new Intent(MainActivity.this,AboutApplicationActivity.class);
                startActivity(abtScren);
            }
        });

        filesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(TYPE_IMAGE);
            }
        });

        PrefManager prefManager = new PrefManager(MainActivity.this);
        if (prefManager.getUserMobile().length()>0)
        {

        }


        uploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(TYPE_IMAGE);
            }
        });

        getList(1);

    }

    private void requestPermissions(int mediaType) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (mediaType == TYPE_IMAGE) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE_VID);
            }

        } else {
            if (mediaType == TYPE_IMAGE) {
                // Want to compress an image
                dispatchTakePictureIntent();
            } else if (mediaType == TYPE_VIDEO) {
                // Want to compress a video
                dispatchTakeVideoIntent();
            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(this, "You need to enable the permission for External Storage Write" +
                            " to test out this library.", Toast.LENGTH_LONG).show();
                    return;
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE_VID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakeVideoIntent();
                } else {
                    Toast.makeText(this, "You need to enable the permission for External Storage Write" +
                            " to test out this library.", Toast.LENGTH_LONG).show();
                    return;
                }
                break;
            }
            default:
        }
    }


    private void dispatchTakePictureIntent() {
        /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");*/


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createMediaFile(TYPE_IMAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
                Log.d(LOG_TAG, "Error occurred while creating the file");

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                // Get the content URI for the image file
                capturedUri = FileProvider.getUriForFile(this,
                        getPackageName() + FILE_PROVIDER_AUTHORITY,
                        photoFile);

                Log.d(LOG_TAG, "Log1: " + capturedUri);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_CAMERA_PHOTO);

            }
        }
        else {
            Log.d("ERROR","INTENT");
        }
    }


    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        takeVideoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        takeVideoIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        takeVideoIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            try {

                takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 1000000);
                takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                capturedUri = FileProvider.getUriForFile(this,
                        getPackageName() + FILE_PROVIDER_AUTHORITY,
                        createMediaFile(TYPE_VIDEO));

                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedUri);
                Log.d(LOG_TAG, "VideoUri: " + capturedUri.toString());
                startActivityForResult(takeVideoIntent, REQUEST_TAKE_VIDEO);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    // Method which will process the captured image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //verify if the image was gotten successfully
        if (requestCode == REQUEST_TAKE_CAMERA_PHOTO && resultCode == Activity.RESULT_OK) {

            new ImageCompressionAsyncTask(this).execute(capturedUri.toString(),
                    getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Silicompressor/images");
            Log.d("CAPTURE",capturedUri.toString());
            Log.d("CAPTUREPATH", mCurrentPhotoPath);


        } else if (requestCode == REQUEST_TAKE_VIDEO && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                //create destination directory


            }
        }
    }

    class ImageCompressionAsyncTask extends AsyncTask<String, Void, String> {

        Context mContext;

        public ImageCompressionAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {

            return SiliCompressor.with(mContext).compress(params[0], new File(params[1]));

        }

        @Override
        protected void onPostExecute(String s) {

            float length = 0;
            String name;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                compressUri = Uri.parse(s);
                Cursor c = getContentResolver().query(compressUri, null, null, null, null);
                c.moveToFirst();
                name = c.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                length = c.getLong(c.getColumnIndex(OpenableColumns.SIZE)) / 1024;
                Log.d("Length", String.valueOf(length));
            } else {
                File imageFile = new File(s);
                compressUri = Uri.fromFile(imageFile);
                name = imageFile.getName();
                length = imageFile.length() / 1024f; // Size in KB
                Log.d("LengthKB", String.valueOf(length));
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), compressUri);
                Log.d("BITMAP",bitmap.toString());
                imageView.setImageBitmap(bitmap);
                PrefManager prefManager = new PrefManager(MainActivity.this);
                prefManager.setUserMobile(bitmap.toString());
                int compressWidth = bitmap.getWidth();
                int compressHieght = bitmap.getHeight();
                String text = String.format(Locale.US, "Name: %s\nSize: %fKB\nWidth: %d\nHeight: %d", name, length, compressWidth, compressHieght);
//                picDescription.setVisibility(View.VISIBLE);
//                picDescription.setText(text);
            } catch (IOException e) {
                e.printStackTrace();
            }

            AddtoList(compressUri);

        }
    }

    private void AddtoList(Uri compressUri)
    {
        arrayList.add(compressUri.toString());
       sharedPreference.addFavorite(MainActivity.this,compressUri.toString());
       getList(0);
    }

    private void getList(int i) {

        arrayList = new ArrayList<>();
        arrayList.clear();
        if (sharedPreference.getFavorites(MainActivity.this)!=null && sharedPreference.getFavorites(MainActivity.this).size()>0)
        {
            emptyLl.setVisibility(View.GONE);
            imagesRv.setVisibility(View.VISIBLE);
            Log.d("SIZE", String.valueOf(sharedPreference.getFavorites(MainActivity.this).size()));
            ImagesAdapter imagesAdapter = new ImagesAdapter(sharedPreference.getFavorites(MainActivity.this),MainActivity.this);
            imagesRv.setHasFixedSize(true);
            imagesRv.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
            imagesRv.setAdapter(imagesAdapter);
        }
        else
        {
            emptyLl.setVisibility(View.VISIBLE);
            imagesRv.setVisibility(View.GONE);
        }


    }

    private void setAdapter(int i)
    {
        if (i==0)
        {
            if (arrayList.size()==0)
            {

            }
        }
    }

    private File createMediaFile(int type) throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = (type == TYPE_IMAGE) ? "JPEG_" + timeStamp + "_" : "VID_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(
                type == TYPE_IMAGE ? Environment.DIRECTORY_PICTURES : Environment.DIRECTORY_MOVIES);
        File file = File.createTempFile(
                fileName,  /* prefix */
                type == TYPE_IMAGE ? ".jpg" : ".mp4",         /* suffix */
                storageDir      /* directory */
        );

        // Get the path of the file created
        mCurrentPhotoPath = file.getAbsolutePath();
        Log.d(LOG_TAG, "mCurrentPhotoPath: " + mCurrentPhotoPath);
        return file;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        //verify if the image was gotten successfully
//        if (requestCode == REQUEST_TAKE_CAMERA_PHOTO && resultCode == Activity.RESULT_OK) {
//
//
//
////            new ImageCompressionAsyncTask(this).execute(capturedUri.toString(),
////                    getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/filelock/images");
//           }
//        }

//    class ImageCompressionAsyncTask extends AsyncTask<String, Void, String> {
//
//        Context mContext;
//
//        public ImageCompressionAsyncTask(Context context) {
//            mContext = context;
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            return SiliCompressor.with(mContext).compress(params[0], new File(params[1]));
//
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//
//            float length = 0;
//            String name;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                compressUri = Uri.parse(s);
//                Cursor c = getContentResolver().query(compressUri, null, null, null, null);
//                c.moveToFirst();
//                name = c.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                length = c.getLong(c.getColumnIndex(OpenableColumns.SIZE)) / 1024;
//            } else {
//                File imageFile = new File(s);
//                compressUri = Uri.fromFile(imageFile);
//                name = imageFile.getName();
//                length = imageFile.length() / 1024f; // Size in KB
//            }
//
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), compressUri);
//                imageView.setImageBitmap(bitmap);
//                int compressWidth = bitmap.getWidth();
//                int compressHieght = bitmap.getHeight();
//                String text = String.format(Locale.US, "Name: %s\nSize: %fKB\nWidth: %d\nHeight: %d", name, length, compressWidth, compressHieght);
//                picDescription.setVisibility(View.VISIBLE);
//                picDescription.setText(text);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

    @Override
    public void onBackPressed() {
        popUpExit();
    }

    private void popUpExit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);
        LayoutInflater inflater = (LayoutInflater) MainActivity.this
                .getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_password, null);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(false);
        final AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
        AppCompatTextView okay,cancel,ques;
        AppCompatEditText anwser;
        ques = dialog.findViewById(R.id.secrettv);
        okay = dialog.findViewById(R.id.submitAnwser);
        anwser = dialog.findViewById(R.id.secretAnwser);
        cancel= dialog.findViewById(R.id.closeTv);
        okay.setText("EXIT");
        anwser.setVisibility(View.GONE);
        ques.setText("Are you sure to exit ?");

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}