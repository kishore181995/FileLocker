package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder> {
    ArrayList<String> favoritesAdd;
    Context mContext;
    SharedPreference sharedPreference;

    ImagesAdapter.RefreshData callback;
    public interface RefreshData{
        public void refresh();
    }

    public ImagesAdapter(ArrayList<String> favorites, MainActivity mainActivity) {
        this.favoritesAdd = favorites;
        this.mContext = mainActivity;
        this.callback = MainActivity.adapterInterfacce;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_images, parent, false);
        return new ImagesAdapter.MyViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Bitmap bitmap = null;
        sharedPreference = new SharedPreference();
        try {
            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.parse(favoritesAdd.get(position)));
            Log.d("BITMAP",bitmap.toString());
            holder.imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.photo_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showBottomlayout(sharedPreference,position);

                return true;
            }
        });

        holder.photo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent zommScreen = new Intent(mContext,ImageZoomActivity.class);
                zommScreen.putExtra("url",favoritesAdd.get(position));
                mContext.startActivity(zommScreen);
            }
        });

    }

    private void showBottomlayout(SharedPreference sharedPreference, int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alert_popup, null);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(false);
        final AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
        AppCompatTextView heading , cancel, okay;
        heading = dialog.findViewById(R.id.headingPopup);
        cancel = dialog.findViewById(R.id.cancelPopup);
        okay = dialog.findViewById(R.id.deletePopup);
        okay.setText("Delete");

        heading.setText("Are you sure you want to delete the file ?");

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sharedPreference.removeFavorite(mContext,favoritesAdd.get(position));
                notifyDataSetChanged();
                if (callback!=null)
                {
                    callback.refresh();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritesAdd.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imageView;
        LinearLayout photo_layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            photo_layout = (LinearLayout)itemView.findViewById(R.id.photo_layout);
            imageView = (AppCompatImageView)itemView.findViewById(R.id.photoIv);
        }
    }
}
