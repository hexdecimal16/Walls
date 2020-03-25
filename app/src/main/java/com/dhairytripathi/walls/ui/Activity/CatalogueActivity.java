package com.dhairytripathi.walls.ui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.dhairytripathi.walls.API.ServiceAPI;
import com.dhairytripathi.walls.Adapter.PixbayAdapter;
import com.dhairytripathi.walls.Model.Pixbay;
import com.dhairytripathi.walls.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import static com.dhairytripathi.walls.Utils.DisplayUtil.getWidth;

public class CatalogueActivity extends AppCompatActivity {
    private Context context;
    private int currentPage = 1;
    private int item_count = 20;
    private int view_threshold =12;
    private int  visibleItemCount, pastVisibleItems, totalItemCount, previous_total, setChangedPosition = 0;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        this.context = this;
        Intent intent = getIntent();
        final boolean ediChoice = intent.getBooleanExtra("ediChoice", false);
        String titled = intent.getStringExtra("title");
        final String order = intent.getStringExtra("order");
        final String category = intent.getStringExtra("category");
        RecyclerView catalogue = findViewById(R.id.rvCatalogue);
        MaterialTextView title = findViewById(R.id.catalogueTitle);
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshCatalogue);

        title.setText(titled);
        final GridLayoutManager layoutManager =  new GridLayoutManager(this, getWidth(150, this), GridLayoutManager.VERTICAL, false);
        catalogue.setLayoutManager(layoutManager);
        final ArrayList<Pixbay> pixbays = new ArrayList<>();
        final PixbayAdapter adapter = new PixbayAdapter(this, this, pixbays, 1, "", "");
        catalogue.setAdapter(adapter);
        ServiceAPI.getImages(this, "",1, ediChoice, order,"",category,"vertical","true", pixbays, adapter);
        currentPage++;

        catalogue.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ServiceAPI.getImages(context, "",currentPage, ediChoice, order,"",category,"vertical","true", pixbays, adapter);
                            }
                        }, 2000);
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
                ServiceAPI.getImages(context, "",1, ediChoice, order,"",category,"vertical","true", pixbays, adapter);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        currentPage = 2;
                    }
                }, 3000);
            }
        });
    }


}