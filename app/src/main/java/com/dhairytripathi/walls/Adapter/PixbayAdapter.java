package com.dhairytripathi.walls.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.dhairytripathi.walls.Model.Pixbay;
import com.dhairytripathi.walls.R;
import com.dhairytripathi.walls.ui.Activity.CardActivity;
import com.dhairytripathi.walls.ui.Activity.CatalogueActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class PixbayAdapter extends RecyclerView.Adapter<PixbayAdapter.PixbayViewHolder>  {
    private Context context;
    private ArrayList<Pixbay> pixbays;
    private int type;
    private String location;
    private String catalogue;
    private Activity activity;

    public PixbayAdapter(Context context, Activity activity, ArrayList<Pixbay> pixbays, int type, String location, String catalogue) {
        this.context = context;
        this.type = type;
        this.location = location;
        this.catalogue = catalogue;
        this.pixbays = pixbays;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PixbayAdapter.PixbayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        switch (type) {
            case 1:
                v = LayoutInflater.from(context).inflate(R.layout.main_item1, parent, false);
                break;
            case 2:
                v = LayoutInflater.from(context).inflate(R.layout.main_item2, parent, false);
                break;
        }
        return new PixbayAdapter.PixbayViewHolder(v);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final PixbayAdapter.PixbayViewHolder holder, final int position) {
        if (!catalogue.equalsIgnoreCase("category")) {
            if(position <= pixbays.size() - 2) {
                final Pixbay pixbay = pixbays.get(position);
                int radius = context.getResources().getDimensionPixelSize(R.dimen.corner_radius);
                Glide.with(context)
                        .load(pixbay.getImageURL())
                        .centerCrop()
                        .transform(new RoundedCorners(radius))
                        .into(holder.imageViewWall);
                holder.imageViewWall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, CardActivity.class);
                        intent.putExtra("id", pixbay.getId());
                        intent.putExtra("tags", pixbay.getTags());
                        intent.putExtra("imageUrl", pixbay.getLargeImageURL());
                        intent.putExtra("views", pixbay.getViews());
                        intent.putExtra("size", pixbay.getImageSize());
                        intent.putExtra("likes", pixbay.getLikes());
                        intent.putExtra("userName", pixbay.getUser());
                        intent.putExtra("userUrl", pixbay.getUserImageURL());
                        intent.putExtra("userId", pixbay.getUser_id());
                        intent.putExtra("downloads", pixbay.getDownloads());
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, new Pair[]{Pair.create(holder.imageViewWall, "imageWallpaper")});

                        context.startActivity(intent, options.toBundle());
                    }
                });
            } else if( location.equalsIgnoreCase("main") && position > pixbays.size() - 2) {
                holder.imageViewWall.setImageDrawable(context.getResources().getDrawable(R.drawable.see_all, context.getTheme()));
                holder.imageViewWall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CatalogueActivity.class);
                        intent.putExtra("title", catalogue);
                        intent.putExtra("category", "");
                        if(catalogue.equalsIgnoreCase("featured")) {
                            intent.putExtra("ediChoice", true);
                        } else {
                            intent.putExtra("ediChoice", false);
                        }
                        if(catalogue.equalsIgnoreCase("popular")) {
                            intent.putExtra("order", "popular");
                        } else {
                            intent.putExtra("order", "latest");
                        }
                        context.startActivity(intent);
                    }
                });
            }
        } else {
            switch (position) {
                case 0:
                    holder.imageViewWall.setImageDrawable(context.getResources().getDrawable(R.drawable.animals));
                    holder.imageViewWall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, CatalogueActivity.class);
                            intent.putExtra("title","Animals");
                            intent.putExtra("category","animals");
                            intent.putExtra("ediChoice",false);
                            intent.putExtra("order", "latest");
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 1:
                    holder.imageViewWall.setImageDrawable(context.getResources().getDrawable(R.drawable.backgrounds));
                    holder.imageViewWall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(context, CatalogueActivity.class);
                            intent.putExtra("title","Backgrounds");
                            intent.putExtra("category","backgrounds");
                            intent.putExtra("ediChoice",false);
                            intent.putExtra("order", "latest");
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 2:
                    holder.imageViewWall.setImageDrawable(context.getResources().getDrawable(R.drawable.fashion));
                    holder.imageViewWall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(context, CatalogueActivity.class);
                            intent.putExtra("title","Fashion");
                            intent.putExtra("category","fashion");
                            intent.putExtra("ediChoice",false);
                            intent.putExtra("order", "latest");
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 3:
                    holder.imageViewWall.setImageDrawable(context.getResources().getDrawable(R.drawable.feelings));
                    holder.imageViewWall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(context, CatalogueActivity.class);
                            intent.putExtra("title","Feelings");
                            intent.putExtra("category","feelings");
                            intent.putExtra("ediChoice",false);
                            intent.putExtra("order", "latest");
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 4:
                    holder.imageViewWall.setImageDrawable(context.getResources().getDrawable(R.drawable.food));
                    holder.imageViewWall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(context, CatalogueActivity.class);
                            intent.putExtra("title","Food");
                            intent.putExtra("category","food");
                            intent.putExtra("ediChoice",false);
                            intent.putExtra("order", "latest");
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 5:
                    holder.imageViewWall.setImageDrawable(context.getResources().getDrawable(R.drawable.music));
                    holder.imageViewWall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(context, CatalogueActivity.class);
                            intent.putExtra("title","Music");
                            intent.putExtra("category","music");
                            intent.putExtra("ediChoice",false);
                            intent.putExtra("order", "latest");
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 6:
                    holder.imageViewWall.setImageDrawable(context.getResources().getDrawable(R.drawable.nature));
                    holder.imageViewWall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(context, CatalogueActivity.class);
                            intent.putExtra("title","Nature");
                            intent.putExtra("category","nature");
                            intent.putExtra("ediChoice",false);
                            intent.putExtra("order", "latest");
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 7:
                    holder.imageViewWall.setImageDrawable(context.getResources().getDrawable(R.drawable.people));
                    holder.imageViewWall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(context, CatalogueActivity.class);
                            intent.putExtra("title","People");
                            intent.putExtra("category","people");
                            intent.putExtra("ediChoice",false);
                            intent.putExtra("order", "latest");
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 8:
                    holder.imageViewWall.setImageDrawable(context.getResources().getDrawable(R.drawable.religious));
                    holder.imageViewWall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(context, CatalogueActivity.class);
                            intent.putExtra("title","Religion");
                            intent.putExtra("category","religion");
                            intent.putExtra("ediChoice",false);
                            intent.putExtra("order", "latest");
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 9:
                    holder.imageViewWall.setImageDrawable(context.getResources().getDrawable(R.drawable.sports));
                    holder.imageViewWall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(context, CatalogueActivity.class);
                            intent.putExtra("title","Sports");
                            intent.putExtra("category","sports");
                            intent.putExtra("ediChoice",false);
                            intent.putExtra("order", "latest");
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 10:
                    holder.imageViewWall.setImageDrawable(context.getResources().getDrawable(R.drawable.technology));
                    holder.imageViewWall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(context, CatalogueActivity.class);
                            intent.putExtra("title","Technology");
                            intent.putExtra("category","computer");
                            intent.putExtra("ediChoice",false);
                            intent.putExtra("order", "latest");
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 11:
                    holder.imageViewWall.setImageDrawable(context.getResources().getDrawable(R.drawable.travel));
                    holder.imageViewWall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(context, CatalogueActivity.class);
                            intent.putExtra("title","Travel");
                            intent.putExtra("category","travel");
                            intent.putExtra("ediChoice",false);
                            intent.putExtra("order", "latest");
                            context.startActivity(intent);
                        }
                    });
                    break;
            }
        }

    }


    @Override
    public int getItemCount() {
        return (pixbays.size());
    }

    public class PixbayViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imageViewWall;
        public PixbayViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewWall = itemView.findViewById(R.id.imageViewWall);
        }
    }
}
