package com.dhairytripathi.walls.ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.dhairytripathi.walls.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;


public class CardActivity extends AppCompatActivity {
    private String filename = "waller.jpg";
    private long downloadID;
    private DownloadManager downloadManager;
    private boolean dFlag = false;
    private String type;
    private String imageUrl;
    private ImageView ivWallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String tags = intent.getStringExtra("tags");
        imageUrl = intent.getStringExtra("imageUrl");
        Log.d("url", imageUrl);
        String views = intent.getStringExtra("views");
        String size = intent.getStringExtra("size");
        String likes = intent.getStringExtra("likes");
        String userName = intent.getStringExtra("userName");
        String userUrl = intent.getStringExtra("userUrl");
        String userId = intent.getStringExtra("userId");
        String downloads = intent.getStringExtra("downloads");
        int radius = this.getResources().getDimensionPixelSize(R.dimen.corner_radius);
        StringTokenizer st = new StringTokenizer(imageUrl, "/");
        filename = st.nextToken();
        while (st.hasMoreTokens()) {
            filename = st.nextToken();
        }
        Log.d("FILE", imageUrl);
        MaterialTextView tvUserName = findViewById(R.id.userName);
        ivWallpaper = findViewById(R.id.imageViewWall);
        final LinearLayout setHome = findViewById(R.id.setWallpaperHome);
        LinearLayout setLock = findViewById(R.id.setWallpaperLock);
        LinearLayout customise = findViewById(R.id.wallpaperCustomise);
//        LinearLayout favorite = findViewById(R.id.wallpaperFavorite);

        Glide.with(this)
                .load(imageUrl)
                .fitCenter()
                .transform(new RoundedCorners(radius))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivWallpaper);
        tvUserName.setText(userName);

        setHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "home";
                setWallpaper("home", imageUrl);
            }
        });

        setLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "lock";
                setWallpaper("lock", imageUrl);
            }
        });

        customise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "customise";
                setWallpaper("customise", imageUrl);
            }
        });

//        favorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @SuppressLint("NewApi")
    private void setWallpaper(String type, String url) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        Drawable bms = ((Drawable)ivWallpaper.getDrawable().getCurrent());
        Bitmap bitmap = drawableToBitmap(bms);
        if (type.equalsIgnoreCase("home")) {
            try {
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                Toast.makeText(getApplicationContext(), "Home screen wallpaper set!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Sorry cannot set wallpaper!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        } else if (type.equalsIgnoreCase("lock")) {
            try {
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                Toast.makeText(getApplicationContext(), "Lock screen wallpaper set!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Sorry cannot set wallpaper!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        } else {
            try {
                Intent intent = wallpaperManager.getCropAndSetWallpaperIntent(getImageUri(getApplicationContext(), bitmap));
                try {
                  startActivity(intent);
                } catch (ActivityNotFoundException e) {
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Sorry cannot set wallpaper!", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}