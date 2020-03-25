package com.dhairytripathi.walls.ui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.dhairytripathi.walls.API.ServiceAPI;
import com.dhairytripathi.walls.Adapter.PixbayAdapter;
import com.dhairytripathi.walls.Model.Pixbay;
import com.dhairytripathi.walls.R;

import java.util.ArrayList;

import static com.dhairytripathi.walls.Utils.DisplayUtil.getWidth;

public class SearchableActivity extends AppCompatActivity {
    private Context context;
    private int currentPage = 1;
    private int item_count = 20;
    private int view_threshold =12;
    private int  visibleItemCount, pastVisibleItems, totalItemCount, previous_total, setChangedPosition = 0;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        Intent intent = getIntent();
        final String query = intent.getStringExtra("query");
        context = this;

        final RecyclerView search = findViewById(R.id.rvSearch);
        final LottieAnimationView loading = findViewById(R.id.searchLoading);
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshSearch);

        search.setLayoutManager(new GridLayoutManager(this, getWidth(150, this), GridLayoutManager.VERTICAL, false));
        final ArrayList<Pixbay> pixbays = new ArrayList<>();
        final PixbayAdapter adapter = new PixbayAdapter(this, this, pixbays, 1, "", "");
        search.setAdapter(adapter);
        search.setVisibility(View.INVISIBLE);

        ServiceAPI.getImages(this, query ,1, false, "latest", "", "", "vertical","true", pixbays, adapter);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                search.setVisibility(View.VISIBLE);
                loading.pauseAnimation();
                loading.setVisibility(View.GONE);
            }
        }, 5000);
        currentPage++;

        search.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager llm = recyclerView.getLayoutManager();
                visibleItemCount = llm.getChildCount();
                totalItemCount = llm.getItemCount();
                pastVisibleItems = ((GridLayoutManager)llm).findFirstVisibleItemPosition();
                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemCount > previous_total) {
                            isLoading = false;
                            previous_total = totalItemCount;
                        }
                    }

                    if (!isLoading && (pastVisibleItems + view_threshold) >= (currentPage - 1) * item_count) {
                        loading.playAnimation();
                        loading.setVisibility(View.VISIBLE);
                        ServiceAPI.getImages(context, query , currentPage, false, "latest", "", "", "vertical","true", pixbays, adapter);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loading.pauseAnimation();
                                loading.setVisibility(View.GONE);
                            }
                        }, 5000);
                        currentPage++;
                        recyclerView.scrollToPosition(setChangedPosition);
                    }
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setColorSchemeColors(Color.parseColor("#fc8803"));
                loading.playAnimation();
                loading.setVisibility(View.VISIBLE);
                ServiceAPI.getImages(context, "",1, false, "latest","", "","vertical","true", pixbays, adapter);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        loading.pauseAnimation();
                        loading.setVisibility(View.GONE);
                        currentPage = 2;
                    }
                }, 5000);
            }
        });
    }
}