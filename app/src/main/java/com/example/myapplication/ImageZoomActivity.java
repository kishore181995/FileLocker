package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class ImageZoomActivity extends AppCompatActivity {

    String url="";
    VideoView videoView;
    ProgressDialog progressDialog;
    AppCompatTextView mCancel;
    RelativeLayout imageRl;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_zoom_activity);

        Window window = ImageZoomActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.setStatusBarColor(ContextCompat.getColor(ImageZoomActivity.this, R.color.white));

        videoView = findViewById(R.id.videViewZoom);
        mCancel = findViewById(R.id.cancelTv);



        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent data = getIntent();

        url = data.getStringExtra("url");


            Log.d("Image",url);
            videoView.setVisibility(View.GONE);
            TouchImageView img = new TouchImageView(ImageZoomActivity.this);
            if (url != null || url.length() > 0) {
//                Glide.with(ImageZoomActivity.this).load(url).into(img);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(url));
                    Log.d("BITMAP",bitmap.toString());
//                    img.setImageBitmap(bitmap);
                    Glide.with(ImageZoomActivity.this).load(url).into(img);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Glide.with(ImageZoomActivity.this).load(R.drawable.lock).into(img);
            }
            img.setMaxZoom(4f);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 250, 0, 0);
            ImageZoomActivity.this.setContentView(img);



    }


}
